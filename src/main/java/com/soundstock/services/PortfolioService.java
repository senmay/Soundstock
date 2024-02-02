package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.PortfolioItemMapper;
import com.soundstock.model.PortfolioItemProjection;
import com.soundstock.model.dto.PortfolioItemDTO;
import com.soundstock.model.entity.OrderEntity;
import com.soundstock.model.entity.PortfolioItemEntity;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.OrderRepository;
import com.soundstock.repository.PortfolioItemRepository;
import com.soundstock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.soundstock.exceptions.ErrorMessages.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final OrderRepository orderRepository;
    private final PortfolioItemRepository portfolioRepository;
    private final PortfolioItemMapper portfolioItemMapper;
    private final UserRepository userRepository;

    public List<PortfolioItemDTO> calculatePortfolio(Principal principal){
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new ObjectNotFound(USER_NOT_FOUND));
        Long id = user.getId();
        List<OrderEntity> orders = orderRepository.findByUserId(id);
        return portfolioRepository.findTotalQuantitiesAndValuesForBuyTransactions(orders);
    }

    public List<PortfolioItemDTO> getPortfolioItemsForUser(Principal principal) {
        return null;
    }
}
