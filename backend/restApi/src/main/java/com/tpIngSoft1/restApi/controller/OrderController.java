package com.tpIngSoft1.restApi.controller;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpIngSoft1.restApi.domain.Order;
import com.tpIngSoft1.restApi.domain.OrderItem;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.dto.OrderDTO;
import com.tpIngSoft1.restApi.rules.rule.Rule;
import com.tpIngSoft1.restApi.service.JwtService;
import com.tpIngSoft1.restApi.service.OrderService;
import com.tpIngSoft1.restApi.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.time.LocalDateTime;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<ApiResponse<String>> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Rules/Rule.json");
        try {
            Rule rule = mapper.readValue(inputStream, Rule.class);
            List<Variant> variant = orderService.convertToOrderItems(orderDTO.getItems());
            if (!rule.evaluate(variant)) {
                ApiResponse<String> errorResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "No cumple las reglas", null);
                return new ResponseEntity<ApiResponse<String>>(errorResponse, HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            System.err.println(e);
            ApiResponse<String> errorResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "Falla archivo de reglas", null);
            return new ResponseEntity<ApiResponse<String>>(errorResponse, HttpStatus.CONFLICT);
        }

        List<OrderItem> items = orderDTO.getItems();
        Order order = new Order(orderDTO.getUsername(),"confirmado", LocalDateTime.now(), items);
        orderService.saveOrder(order);

        ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "success",  "Orden agregada exitosamente",null);
        return new ResponseEntity<ApiResponse<String>>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders(@RequestHeader("Authorization") String authHeader) {
        System.out.println("Cabecera de autorización: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ApiResponse<List<Order>> errorResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "error", "Ah Ah Ah! No dijiste la palabra mágica", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);
        String username = jwtService.getUsernameFromToken(token);
        System.out.println("Usuario extraído del token: " + username);

        List<Order> orders;

        try {
            if ("admin".equals(username)) {
                orders = orderService.getAllOrders();
            } else {
                orders = orderService.getOrdersByUsername(username);
            }
            System.out.println("Órdenes recuperadas: " + orders);
        } catch (Exception e) {
            System.err.println("Error al recuperar órdenes: " + e.getMessage());
            ApiResponse<List<Order>> errorResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error", "Error al recuperar órdenes: " + e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ApiResponse<List<Order>> response = new ApiResponse<>(HttpStatus.OK.value(), "success", "La orden existe", orders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateOrderStatus(
            @PathVariable("id") String id,
            @RequestBody Map<String, String>  status) {

        if (status.size() != 1 || !status.containsKey("status")) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "Body no correcto", null), HttpStatus.CONFLICT);
        }

        if (!status.get("status").equals("EN PROCESO") && !status.get("status").equals("PROCESADO") && !status.get("status").equals("COMPLETADO") && !status.get("status").equals("CANCELADO") ) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CONFLICT.value(), "error", "Status no correcto", null), HttpStatus.CONFLICT);
        }

        Optional<Order> order = orderService.findById(id);
        if (order.isPresent()) {
            Order orderToUp = order.get();
            orderToUp.setStatus(status.get("status"));
            orderService.saveOrder(orderToUp);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Order cambia a estado: " + status, null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "error", "Order no encontrada", null), HttpStatus.NOT_FOUND);
        }
    }
}