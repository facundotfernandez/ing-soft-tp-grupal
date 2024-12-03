package com.tpIngSoft1.restApi;

import com.tpIngSoft1.restApi.controller.CartController;
import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.service.CartService;
import com.tpIngSoft1.restApi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartService cartService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    public void testGetCart_Success() throws Exception {
        // Prepare mock data
        Product product = new Product("Product1", "Description", "Brand", 100.0);
        when(cartService.getCart("userId")).thenReturn(Arrays.asList(product));

        // Perform the GET request and verify the response
        mockMvc.perform(get("/cart/{id}", "userId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product1"));
        
        // Verify that the service method was called once
        verify(cartService, times(1)).getCart("userId");
    }

    @Test
    public void testAddProductToCart_Success() throws Exception {
        // Prepare mock data
        Product product = new Product("Product1", "Description", "Brand", 100.0);
        
        // Mock service call (no actual logic here as it's a simple post)
        doNothing().when(cartService).addProduct(any(String.class), any(Product.class));

        // Perform the POST request and verify the response
        mockMvc.perform(post("/cart/{id}", "userId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Product1\", \"description\": \"Description\", \"brand\": \"Brand\", \"price\": 100.0 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Producto agregado al carrito"));

        // Verify that the service method was called once
        verify(cartService, times(1)).addProduct(any(String.class), any(Product.class));
    }

    @Test
    public void testGetCart_Empty() throws Exception {
        // Mock the service to return an empty cart
        when(cartService.getCart("userId")).thenReturn(Arrays.asList());

        // Perform the GET request and verify the response
        mockMvc.perform(get("/cart/{id}", "userId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        // Verify that the service method was called once
        verify(cartService, times(1)).getCart("userId");
    }

    @Test
    public void testAddProductToCart_Failure() throws Exception {
        // Mock service to simulate an error, such as product not found
        doThrow(new RuntimeException("Product not found")).when(cartService).addProduct(any(String.class), any(Product.class));

        // Perform the POST request and verify the failure
        mockMvc.perform(post("/cart/{id}", "userId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Product1\", \"description\": \"Description\", \"brand\": \"Brand\", \"price\": 100.0 }"))
                .andExpect(status().isInternalServerError());

        // Verify the service was called
        verify(cartService, times(1)).addProduct(any(String.class), any(Product.class));
    }
}
