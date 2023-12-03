package com.soundstock.services;

import com.soundstock.enums.UserRole;
import com.soundstock.model.entity.StockEntity;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.StockRepository;
import com.soundstock.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DummyDataLoader {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final StockRepository stockRepository;

    @PostConstruct
    public void loadDummyData(){
        UserEntity admin = UserEntity.builder().enabled(true).username("A").email("admin@wp.pl").role(UserRole.ADMIN).password(passwordEncoder.encode("password")).balance(BigDecimal.valueOf(1000)).build();
        UserEntity user1 = UserEntity.builder().enabled(true).username("U1").email("user1@wp.pl").role(UserRole.USER).password(passwordEncoder.encode("password1")).balance(BigDecimal.valueOf(500)).build();
        UserEntity user2 = UserEntity.builder().enabled(true).username("U2").email("user2@wp.pl").role(UserRole.USER).password(passwordEncoder.encode("password2")).balance(BigDecimal.valueOf(100)).build();
        List<UserEntity> users = List.of(admin,user1,user2);
        userRepository.saveAll(users);
        System.out.println("Load users into database");
        StockEntity stockEntity = StockEntity.builder().name("Bitcoin").stockQuantity(20).price(BigDecimal.TEN).percentageChange(BigDecimal.valueOf(-0.045)).shortcut("BTC").build();
        StockEntity stockEntity1 = StockEntity.builder().name("Ethereum").stockQuantity(30).price(BigDecimal.ONE).percentageChange(BigDecimal.valueOf(0.05)).shortcut("ETH").build();
        List<StockEntity> stocks = List.of(stockEntity,stockEntity1);
        stockRepository.saveAll(stocks);

    }
}
