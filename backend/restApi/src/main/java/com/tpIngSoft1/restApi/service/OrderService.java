package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.domain.Order;
import com.tpIngSoft1.restApi.domain.OrderItem;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;

import com.tpIngSoft1.restApi.rules.rule.Rule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.io.IOException;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Order> findById(String id) {
        return repository.findById(id);
    }

    public boolean checkRule(Order order) {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Rules/Rule.json");
        try {
            Rule rule = mapper.readValue(inputStream, Rule.class);
            List<Variant> variant = this.convertToOrderItems(order.getItems());
            return rule.evaluate(variant);
        } catch (IOException e) {
            System.err.println(e);
        }
        return false; 
    }

    public void saveOrder(Order order, String username) {
        order.setConfirmationDate(LocalDateTime.now());
        order.setStatus("confirmado");
        order.setUsername(username);
        repository.save(order);
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