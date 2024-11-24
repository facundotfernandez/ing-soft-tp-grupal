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
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUsername("testUser");
        orderDTO.setItems(Collections.emptyList());
        ResponseEntity<ApiResponse<String>> response = orderController.createOrder(orderDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "La respuesta no debe ser nula");
        assertNotNull(response.getBody().getMessage(), "El mensaje en la respuesta no debe ser nulo");
    }

    @Test
    void testGetAllOrders_whenAdminUser() {
        String token = "Bearer adminToken";
        when(jwtService.getUsernameFromToken("adminToken")).thenReturn("admin");
        when(orderService.getAllOrders()).thenReturn(List.of(order));
        ResponseEntity<ApiResponse<List<Order>>> response = orderController.getAllOrders(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    void testGetAllOrders_whenNonAdminUser() {
        String token = "Bearer userToken";
        when(jwtService.getUsernameFromToken("userToken")).thenReturn("testUser");
        when(orderService.getOrdersByUsername("testUser")).thenReturn(List.of(order));
        ResponseEntity<ApiResponse<List<Order>>> response = orderController.getAllOrders(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    void testUpdateOrderStatus_whenOrderExists() {
        String newStatus = "PROCESADO";
        Map<String, String> statusMap = Map.of("status", "COMPLETADO");
        when(orderService.findById("1")).thenReturn(Optional.of(order));
        ResponseEntity<ApiResponse<String>> response = orderController.updateOrderStatus("1", statusMap);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order cambia a estado: {status=COMPLETADO}", response.getBody().getMessage());
        verify(orderService, times(1)).saveOrder(order);
    }

    @Test
    void testUpdateOrderStatus_whenOrderDoesNotExist() {
        Map<String, String> statusMap = Map.of("status", "PROCESADO");
        when(orderService.findById("1")).thenReturn(Optional.empty());
        ResponseEntity<ApiResponse<String>> response = orderController.updateOrderStatus("1", statusMap);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order no encontrada", response.getBody().getMessage());
    }
    @Test
    void testUpdateOrderStatus_whenInvalidStatus() {
        Map<String, String> statusMap = Map.of("status", "INVALID_STATUS");
        ResponseEntity<ApiResponse<String>> response = orderController.updateOrderStatus("1", statusMap);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Status no correcto", response.getBody().getMessage());
    }

}
