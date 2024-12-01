package com.tpIngSoft1.restApi.controller;

import com.tpIngSoft1.restApi.domain.Order;
import com.tpIngSoft1.restApi.service.JwtService;
import com.tpIngSoft1.restApi.service.OrderService;
import com.tpIngSoft1.restApi.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/orders")
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://ing-soft-tp-grupal.vercel.app"})
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createOrder(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody Order order) throws IOException {

        ResponseEntity<ApiResponse<String>> response = orderService.checkAuth(authHeader, jwtService);
        
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        String token = response.getBody().getData();
        String username = jwtService.getUsernameFromToken(token);

        if(!orderService.checkRule(order)) {
            ApiResponse<String> errorResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "No cumple las reglas", null);
            return new ResponseEntity<ApiResponse<String>>(errorResponse, HttpStatus.CONFLICT);
        }

        return orderService.createdOrder(order,username);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders(@RequestHeader("Authorization") String authHeader) {

        ResponseEntity<ApiResponse<String>> response = orderService.checkAuth(authHeader, jwtService);
        
        if (response.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(new ApiResponse<>(response.getStatusCode().value(), "error", response.getBody().getData() , null), response.getStatusCode());
        }
        
        String token = response.getBody().getData();

        String username = jwtService.getUsernameFromToken(token);
        List<Order> orders;

        try {
            if ("admin".equals(username)) {
                orders = orderService.getAllOrders();
            } else {
                orders = orderService.getOrdersByUsername(username);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error", "Error al recuperar órdenes: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Órdenes recuperadas", orders), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateOrderStatus(@RequestHeader("Authorization") String authHeader, @PathVariable("id") String id, @RequestBody Map<String, String> status) {
        
        ResponseEntity<ApiResponse<String>> response = orderService.checkAuth(authHeader, jwtService);
        
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        if (status.size() != 1 || !status.containsKey("status")) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "Body no correcto", null), HttpStatus.CONFLICT);
        }

        String newStatus = status.get("status");
        Set<String> validStatuses = Set.of("en proceso", "procesado", "enviado", "cancelado");

        if (!validStatuses.contains(newStatus)) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "Status no correcto", null), HttpStatus.CONFLICT);
        }

        Optional<Order> order = orderService.findById(id);
        if (order.isPresent()) {
            Order orderToUp = order.get();
            orderService.updateOrder(orderToUp, newStatus);
 
            if (newStatus.equals("cancelado")) {
                orderService.updateVariant(orderToUp,true);
            }

            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Order cambia a estado: " + newStatus, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "error", "Order no encontrada", null), HttpStatus.NOT_FOUND);
        }
    }
}