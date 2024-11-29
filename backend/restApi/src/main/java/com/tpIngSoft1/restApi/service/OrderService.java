package com.tpIngSoft1.restApi.service;

import com.tpIngSoft1.restApi.domain.Order;
import com.tpIngSoft1.restApi.domain.OrderItem;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

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
            List<Variant> variant = orderService.convertToOrderItems(order.getItems());
            return rule.evaluate(variant);
        } catch (IOException e) {
            System.err.println(e);
            ApiResponse<String> errorResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "Falla archivo de reglas", null);
            return new ResponseEntity<ApiResponse<String>>(errorResponse, HttpStatus.CONFLICT);
        }
        return false;
    }

    public void saveOrder(Order order) {
        order.setConfirmationDate(LocalDateTime.now());
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