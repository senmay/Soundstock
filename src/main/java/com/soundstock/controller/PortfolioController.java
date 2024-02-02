package com.soundstock.controller;

import com.soundstock.model.dto.PortfolioItemDTO;
import com.soundstock.services.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("portfolio/v1")
public class PortfolioController {
    private final PortfolioService portfolioService;
    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public List<PortfolioItemDTO> calculatePortfolio(Principal principal){
        return portfolioService.calculatePortfolio(principal);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PortfolioItemDTO> getPortfolio(Principal principal) {
        return portfolioService.getPortfolioItemsForUser(principal);
    }
}
