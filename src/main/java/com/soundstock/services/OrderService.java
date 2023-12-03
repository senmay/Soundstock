package com.soundstock.services;

import com.soundstock.exceptions.InsufficientBalanceException;
import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.OrderMapper;
import com.soundstock.model.dto.OrderDTO;
import com.soundstock.model.entity.OrderEntity;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.OrderRepository;
import com.soundstock.repository.StockRepository;
import com.soundstock.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import static com.soundstock.enums.TransactionType.BUY;
import static com.soundstock.exceptions.ErrorMessages.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    @Transactional
    public String registerOrder(OrderDTO orderDTO, Principal principal) {
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name)
                .orElseThrow(() -> new ObjectNotFound(USER_NOT_FOUND));

        stockRepository.findById(orderDTO.getStock().getId())
                .orElseThrow(() -> new ObjectNotFound(STOCK_NOT_FOUND));

        BigDecimal orderCost = orderDTO.getPricePerShare().multiply(new BigDecimal(orderDTO.getQuantity()));
        if (user.getBalance().compareTo(orderCost) < 0) {
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE);
        }

        OrderEntity orderEntity = orderMapper.mapDTOToOrderEntity(orderDTO);
        orderEntity.setTransactionValue(orderCost);
        orderEntity.setUser(user);
        orderRepository.save(orderEntity);
        log.info("Order saved");

        if (orderDTO.getTransactionType() == BUY) {
            user.setBalance(user.getBalance().subtract(orderCost));
        } else {
            user.setBalance(user.getBalance().add(orderCost));
        }
        //TODO DODAĆ WIECEJ SPRAWDZEŃ

        userRepository.save(user);
        log.info("User updated");

        return "Order registered successfully";
    }

    public OrderDTO getOrder(Principal principal, Long orderid) {
        String username = principal.getName();
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFound(USER_NOT_FOUND));
        // Get order and check if it belongs to logged-in user
        OrderEntity orderEntity = orderRepository.findById(orderid)
                .orElseThrow(() -> new ObjectNotFound(ORDER_NOT_FOUND));

        if (!orderEntity.getUser().getId().equals(userEntity.getId())) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        return orderMapper.mapOrderEntityToDto(orderRepository.findById(orderid).get());
    }

    public List<OrderDTO> getOrdersFromUser(Principal principal) {
        String username = principal.getName();
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFound(USER_NOT_FOUND));

        return orderMapper.mapOrderEntitesToDTOList(orderRepository.findByUserId(userEntity.getId()));
    }

    public List<OrderDTO> getAllOrders() {
        return orderMapper.mapOrderEntitesToDTOList(orderRepository.findAll());
    }
}
