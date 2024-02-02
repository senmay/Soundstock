package com.soundstock.controller;

import com.soundstock.model.dto.OrderDTO;
import com.soundstock.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("order/v1")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addOrder(Principal principal, @RequestBody OrderDTO orderDTO){
        return orderService.registerOrder(orderDTO, principal);
    }
    @GetMapping("/orderid")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getMyOrder(Principal principal, Long orderid){
        return orderService.getOrder(principal,orderid);
    }
    @GetMapping("/my-orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllOrdersFromUser(Principal principal){
        return orderService.getOrdersFromUser(principal);
    }
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllOrders(){
        return orderService.getAllOrders();
    }
    @GetMapping("/test")
    @ResponseStatus(HttpStatus.CREATED)
    public String test(Principal principal){
        return principal.getName();
    }
}
