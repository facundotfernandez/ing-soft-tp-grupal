package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.domain.Order;
import com.tpIngSoft1.restApi.domain.OrderItem;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void saveOrder(Order order) {
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
}