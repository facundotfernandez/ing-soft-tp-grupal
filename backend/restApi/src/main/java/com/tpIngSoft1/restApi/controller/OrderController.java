package com.tpIngSoft1.restApi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpIngSoft1.restApi.domain.Order;
import com.tpIngSoft1.restApi.domain.OrderItem;
import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.dto.OrderDTO;
import com.tpIngSoft1.restApi.rules.rule.Rule;
import com.tpIngSoft1.restApi.service.JwtService;
import com.tpIngSoft1.restApi.service.OrderService;
import com.tpIngSoft1.restApi.service.ProductService;
import com.tpIngSoft1.restApi.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
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
    private ProductService productService;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createOrder(@Valid @RequestBody Order order) throws IOException {

        if(!orderService.checkRule(order)) {
            ApiResponse<String> errorResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "No cumple las reglas", null);
            return new ResponseEntity<ApiResponse<String>>(errorResponse, HttpStatus.CONFLICT);
        }

        // List<OrderItem> items = orderDTO.getItems();
        // Order order = new Order(orderDTO.getUsername(), "confirmado", LocalDateTime.now(), items);
        orderService.saveOrder(order);

        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "success", "Orden agregada exitosamente", null);
        return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Token no proporcionado", null), HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Token inválido", null), HttpStatus.UNAUTHORIZED);
        }

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
    public ResponseEntity<ApiResponse<String>> updateOrderStatus(@PathVariable("id") String id, @RequestBody Map<String, String> status) {

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
            orderToUp.setStatus(newStatus);
            orderService.saveOrder(orderToUp);
            if (newStatus.equals("cancelado")) {
                for (OrderItem item: orderToUp.getItems()) {
                    String pid = item.getPid();
                    String vid = item.getVid();
                    if (productService.saveStockProduct(pid,vid,item.getQuantity())==false){
                        new ResponseEntity<>(new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "Variante al modificar stock", null), HttpStatus.CONFLICT);
                    }
                }
            }

            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Order cambia a estado: " + newStatus, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "error", "Order no encontrada", null), HttpStatus.NOT_FOUND);
        }
    }
}