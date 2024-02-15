package com.soundstock.services;

import com.soundstock.exceptions.InsufficientBalanceException;
import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.OrderMapper;
import com.soundstock.model.*;
import com.soundstock.model.dto.OrderDTO;
import com.soundstock.model.dto.PortfolioItemDTO;
import com.soundstock.model.entity.OrderEntity;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.OrderRepository;
import com.soundstock.repository.PortfolioRepository;
import com.soundstock.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static com.soundstock.exceptions.ErrorMessages.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final StockService stockService;
    private final PortfolioRepository portfolioRepository;
    private final UserService userService;
    @Transactional
    public void registerBuyOrder(BaseOrder baseOrder, Principal principal) {
        UserEntity user = userService.getUserByUsername(principal.getName());
        OrderCostStrategy costStrategy = determineCostStrategy(baseOrder);
        BigDecimal orderCost = costStrategy.calculateCost(baseOrder);
        verifySufficientBalance(user, orderCost);
        processBuyOrder(user, orderCost);
        finalizeOrder(baseOrder, user, orderCost);
    }
    @Transactional
    public void registerSellOrder(BaseOrder baseOrder, Principal principal) {
        UserEntity user = userService.getUserByUsername(principal.getName());
        OrderCostStrategy costStrategy = determineCostStrategy(baseOrder);
        BigDecimal orderCost = costStrategy.calculateCost(baseOrder);
        if (baseOrder instanceof CryptoOrderDTO cryptoOrderDTO) {
            processSellOrder(user, cryptoOrderDTO, orderCost);
        } else if (baseOrder instanceof TrackOrderDTO trackOrderDTO) {
            processSellOrder(user, trackOrderDTO, orderCost);
        } else {
            throw new IllegalArgumentException("Unsupported order type");
        }
        finalizeOrder(baseOrder, user, orderCost);
    }

    public OrderDTO getOrder(Principal principal, Long orderid) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new ObjectNotFound(USER_NOT_FOUND));

        OrderEntity orderEntity = orderRepository.findById(orderid)
                .orElseThrow(() -> new ObjectNotFound(ORDER_NOT_FOUND));

        if (!orderEntity.getUser().getId().equals(userEntity.getId())) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        return orderMapper.mapOrderEntityToDto(orderEntity);
    }

    public List<OrderDTO> getOrdersFromUser(Principal principal) {
        String username = principal.getName();
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFound(USER_NOT_FOUND));

        return orderMapper.mapOrderEntitesToDTOList(orderRepository.findByUserId(userEntity.getId()));
    }

    public List<OrderDTO> getAllOrders() {
        return orderMapper.mapOrderEntitesToDTOList(orderRepository.findAll());
    }
    private Integer getTotalQuantityFromUser(CryptoOrderDTO cryptoOrderDTO, List<PortfolioItemDTO> summarizeAllTransactions) {
        return summarizeAllTransactions.stream()
                .filter(c -> c.getAssetName().equals(cryptoOrderDTO.getStock().getName()))
                .mapToInt(x -> Math.toIntExact(x.getQuantity()))
                .findFirst()
                .orElse(0);
    }
    private BigDecimal getTotalValueFromUser(TrackOrderDTO trackOrderDTO, List<PortfolioItemDTO> portfolioItems) {
        return portfolioItems.stream()
                .filter(c -> c.getAssetName().equals(trackOrderDTO.getTrack().getTitle()))
                .map(PortfolioItemDTO::getTotalValue)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }
    private void processBuyOrder(UserEntity user, BigDecimal orderCost) {
        user.setBalance(user.getBalance().subtract(orderCost));
    }

    private void processSellOrder(UserEntity user, CryptoOrderDTO orderDTO, BigDecimal orderCost) {
        List<PortfolioItemDTO> portfolioItems = portfolioRepository.getPortfolioSummary(orderRepository.findByUserId(user.getId()));
        int totalQuantity = getTotalQuantityFromUser(orderDTO, portfolioItems);

        if (orderDTO.getQuantity() > totalQuantity) {
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE);
        }
        user.setBalance(user.getBalance().add(orderCost));    }

    private void processSellOrder(UserEntity user, TrackOrderDTO trackOrderDTO, BigDecimal orderCost) {
        List<PortfolioItemDTO> portfolioItems = portfolioRepository.getPortfolioSummary(orderRepository.findByUserId(user.getId()));
        BigDecimal totalValue = getTotalValueFromUser(trackOrderDTO, portfolioItems);
        if (trackOrderDTO.getTransactionValue().compareTo(totalValue) > 0) {
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE);
        }
        user.setBalance(user.getBalance().add(orderCost));
    }
    private void saveOrder(BaseOrder baseOrder, UserEntity user, BigDecimal orderCost) {
        OrderEntity orderEntity = orderMapper.mapDTOToOrderEntity(baseOrder,orderCost);
        orderEntity.setTransactionValue(orderCost);
        orderEntity.setUser(user);
        orderRepository.save(orderEntity);
        log.info("Order saved");
    }
    private void updateUser(UserEntity user) {
        userRepository.save(user);
        log.info("User updated");
    }

    private void verifySufficientBalance(UserEntity user, BigDecimal orderCost) {
        if (user.getBalance().compareTo(orderCost) < 0) {
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE);
        }
    }
    private OrderCostStrategy determineCostStrategy(BaseOrder baseOrder) {
        if (baseOrder instanceof CryptoOrderDTO) {
            return new CryptoOrderCostStrategy();
        } else if (baseOrder instanceof TrackOrderDTO) {
            return new TrackOrderCostStrategy();
        } else {
            throw new IllegalArgumentException("Invalid order type");
        }
    }
    private void finalizeOrder(BaseOrder baseOrder, UserEntity user, BigDecimal orderCost) {
        saveOrder(baseOrder, user, orderCost);
        updateUser(user);
    }


}
