package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.controller.ProductController;
import com.tpIngSoft1.restApi.domain.Order;
import com.tpIngSoft1.restApi.domain.OrderItem;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.InputStream;

import com.tpIngSoft1.restApi.utils.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    
    @Autowired
    private ProductController productController;

    private RuleService ruleService = new RuleService();

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Order> findById(String id) {
        return repository.findById(id);
    }


    public ResponseEntity<ApiResponse<String>> createdOrder(Order order, String username) {
        ResponseEntity<String> response = this.updateVariant(order, false);
        if (response.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(new ApiResponse<>(response.getStatusCode().value(), "error", response.getBody() , null), response.getStatusCode());
        }
        this.saveOrder(order,username);
        return new ResponseEntity<ApiResponse<String>>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Orden agregada exitosamente", null), HttpStatus.OK);
    }

    public boolean checkRule(Order order) {
        List<Variant> variant = this.convertToOrderItems(order.getItems());
        return ruleService.checkOrder(variant); 
    }

    public ResponseEntity<ApiResponse<String>> checkAuth(String authHeader, JwtService jwtService) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Token no proporcionado", null), HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Token inválido", null), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Token valido", token), HttpStatus.OK);
    }

    public void saveOrder(Order order, String username) {
        order.setConfirmationDate(LocalDateTime.now());
        order.setStatus("confirmado");
        order.setUsername(username);
        repository.save(order);
    }

    public ResponseEntity<String> updateVariant(Order order, Boolean isAdd) {
        for (OrderItem item: order.getItems()) {
            String pid = item.getPid();
            String vid = item.getVid();
            Variant variant = productController.getVariantByProductIdAndVid(pid,vid).getBody();
            Integer stock = isAdd?(variant.getStock()+item.getQuantity()):(variant.getStock()-item.getQuantity());
            ResponseEntity<String> response =  productController.updateVariantStock(pid,vid,stock );
            if (response.getStatusCode() != HttpStatus.OK) {
                return response;
            }
        }
        return new ResponseEntity<>( "Variant actualizados", HttpStatus.OK);
    }

    public void updateOrder(Order order, String status) {
        order.setStatus(status);
        repository.save(order);
    }

    public void deleteOrder(String id) {
        repository.deleteById(id);
    }

    public List<Order> getOrdersByUsername(String username) {
        return repository.findByUsername(username);
    }

    public List<Variant> convertToOrderItems(List<OrderItem> items) {
        return items.stream().map(orderitem -> new Variant(orderitem.getSpecs(), orderitem.getQuantity(), orderitem.getVid() )).collect(Collectors.toList());
    }
    public InputStream getResourceAsStream(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }
}