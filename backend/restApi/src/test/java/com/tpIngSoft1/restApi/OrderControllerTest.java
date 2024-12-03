package com.tpIngSoft1.restApi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.tpIngSoft1.restApi.controller.OrderController;
import com.tpIngSoft1.restApi.domain.Order;
import com.tpIngSoft1.restApi.domain.OrderItem;
import com.tpIngSoft1.restApi.dto.OrderDTO;
import com.tpIngSoft1.restApi.service.JwtService;
import com.tpIngSoft1.restApi.service.OrderService;
import com.tpIngSoft1.restApi.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private OrderController orderController;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order("1", "testUser", LocalDateTime.now(), new ArrayList<>(), "confirmado");
    }

    @Test
    void testCreateOrder_whenValidOrder() throws Exception {
        String token = "Bearer adminToken";
        when(jwtService.getUsernameFromToken(token)).thenReturn("admin");
        when(orderService.checkAuth(token,jwtService)).thenReturn(new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Token valido", token), HttpStatus.OK));  
        Order order = new Order();
        when(orderService.checkRule(order)).thenReturn(true);
        when(orderService.createdOrder(order,jwtService.getUsernameFromToken(token))).thenReturn(new ResponseEntity<ApiResponse<String>>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Orden agregada exitosamente", null), HttpStatus.OK));
        order.setUsername("testUser");
        order.setItems(Collections.emptyList());
        ResponseEntity<ApiResponse<String>> response = orderController.createOrder(token,order);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "La respuesta no debe ser nula");
        assertNotNull(response.getBody().getMessage(), "El mensaje en la respuesta no debe ser nulo");
    }

    @Test
    void testGetAllOrders_whenAdminUser() {
        String token = "Bearer adminToken";
        when(jwtService.getUsernameFromToken(token)).thenReturn("admin");
        when(orderService.checkAuth(token,jwtService)).thenReturn(new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Token valido", token), HttpStatus.OK));
        when(orderService.getAllOrders()).thenReturn(List.of(order));       
        ResponseEntity<ApiResponse<List<Order>>> response = orderController.getAllOrders(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    void testGetAllOrders_whenNonAdminUser() {
        String token = "Bearer userToken";
        when(jwtService.getUsernameFromToken(token)).thenReturn("testUser");
        when(orderService.checkAuth(token,jwtService)).thenReturn(new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Token valido", token), HttpStatus.OK));
        when(orderService.getOrdersByUsername(jwtService.getUsernameFromToken(token))).thenReturn(List.of(order));
        ResponseEntity<ApiResponse<List<Order>>> response = orderController.getAllOrders(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    void testUpdateOrderStatus_whenOrderExists() {
        String token = "Bearer userToken";
        when(orderService.checkAuth(token,jwtService)).thenReturn(new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Token valido", token), HttpStatus.OK));
        String newStatus = "procesado";
        Map<String, String> statusMap = Map.of("status", "enviado");
        when(orderService.findById("1")).thenReturn(Optional.of(order));
        doNothing().when(orderService).updateOrder(order,statusMap.get("status"));
        ResponseEntity<ApiResponse<String>> response = orderController.updateOrderStatus(token,"1", statusMap);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order cambia a estado: enviado", response.getBody().getMessage());
    }

    @Test
    void testUpdateOrderStatus_whenOrderDoesNotExist() {
        String token = "Bearer userToken";
        when(orderService.checkAuth(token,jwtService)).thenReturn(new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Token valido", token), HttpStatus.OK));
        Map<String, String> statusMap = Map.of("status", "procesado");
        when(orderService.findById("1")).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse<String>> response = orderController.updateOrderStatus(token,"1", statusMap);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order no encontrada", response.getBody().getMessage());
    }

    @Test
    void testUpdateOrderStatus_whenInvalidStatus() {
        String token = "Bearer userToken";
        when(orderService.checkAuth(token,jwtService)).thenReturn(new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "success", "Token valido", token), HttpStatus.OK));
        Map<String, String> statusMap = Map.of("status", "INVALID_STATUS");
        ResponseEntity<ApiResponse<String>> response = orderController.updateOrderStatus(token,"1", statusMap);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Status no correcto", response.getBody().getMessage());
    }

}
