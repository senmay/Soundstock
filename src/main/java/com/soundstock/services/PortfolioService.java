package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.PortfolioItemMapper;
import com.soundstock.model.dto.PortfolioItemDTO;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.OrderRepository;
import com.soundstock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

import static com.soundstock.exceptions.ErrorMessages.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final OrderRepository orderRepository;
    private final PortfolioItemMapper portfolioItemMapper;
    private final UserRepository userRepository;

    public List<PortfolioItemDTO> calculatePortfolio(Principal principal){
        String name = principal.getName();
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new ObjectNotFound(USER_NOT_FOUND));
        Long id = user.getId();
        return null;


    }
}
