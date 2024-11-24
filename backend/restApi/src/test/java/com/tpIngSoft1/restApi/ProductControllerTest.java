package com.tpIngSoft1.restApi;
import com.tpIngSoft1.restApi.controller.ProductController;
import com.tpIngSoft1.restApi.domain.Product;
import com.tpIngSoft1.restApi.domain.Variant;
import com.tpIngSoft1.restApi.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId("1");
        product.setName("Product 1");
    }



    @Test
    void testAddVariantToProduct_whenProductExists() {
        when(productService.findById("1")).thenReturn(Optional.of(product));
        product.setVariants(new ArrayList<>());
        Map<String, String> specs = Map.of("color", "amarillo", "size", "M");
        ResponseEntity<String> response = productController.addVariantToProduct("1", specs);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Variante agregada exitosamente", response.getBody());
        verify(productService, times(1)).saveProduct(any(Product.class));
    }

    @Test
    void testGetProductById_whenProductExists() {
        when(productService.findById("1")).thenReturn(Optional.of(product));
        ResponseEntity<Product> response = productController.getProductById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testGetProductById_whenProductDoesNotExist() {
        when(productService.findById("1")).thenReturn(Optional.empty());
        ResponseEntity<Product> response = productController.getProductById("1");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testUpdateVariantStock_whenVariantExists() {
        when(productService.findById("1")).thenReturn(Optional.of(product));
        Variant variant = new Variant();
        variant.setStock(10);
        product.getVariants().add(variant);
        ResponseEntity<String> response = productController.updateVariantStock("1", variant.getVid(), 20);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Variante modificada exitosamente", response.getBody());
        assertEquals(20, variant.getStock());
        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    void testAddProductWithVariants() {
        Product newProduct = new Product();
        newProduct.setName("New Product");
        Variant variant1 = new Variant();
        Variant variant2 = new Variant();
        newProduct.setVariants(List.of(variant1, variant2));

        doNothing().when(productService).saveProduct(newProduct);

        ResponseEntity<String> response = productController.addProduct(newProduct);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Producto agregado exitosamente", response.getBody());
        verify(productService, times(1)).saveProduct(newProduct);
    }

    @Test
    void testDeleteProduct_whenProductExists() {
        when(productService.findById("1")).thenReturn(Optional.of(product));
        ResponseEntity<String> response = productController.deleteProduct("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto eliminado exitosamente", response.getBody());
        verify(productService, times(1)).deleteProduct("1");
    }

    @Test
    void testDeleteProduct_whenProductDoesNotExist() {
        when(productService.findById("1")).thenReturn(Optional.empty());
        ResponseEntity<String> response = productController.deleteProduct("1");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("El producto no existe", response.getBody());
    }



    @Test
    void testUpdateVariantStock_whenVariantDoesNotExist() {
        when(productService.findById("1")).thenReturn(Optional.of(product));
        Variant variant = new Variant();
        variant.setStock(10);
        product.getVariants().add(variant);
        String nonExistentVid = UUID.randomUUID().toString();
        ResponseEntity<String> response = productController.updateVariantStock("1", nonExistentVid, 20);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Variante no encontrada", response.getBody());
    }


    @Test
    void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("Producto 1");
        Product product2 = new Product();
        product2.setName("Producto 2");
        products.add(product1);
        products.add(product2);
        when(productService.getAllProducts()).thenReturn(products);
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testAddProduct_whenProductDoesNotExist() {
        Product newProduct = new Product();
        newProduct.setName("Nuevo Producto");
        when(productService.findByName("Nuevo Producto")).thenReturn(Optional.empty());
        ResponseEntity<String> response = productController.addProduct(newProduct);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Producto agregado exitosamente", response.getBody());
        verify(productService, times(1)).saveProduct(newProduct);
    }

    @Test
    void testAddVariantToProduct_withInvalidSpecs() {
        when(productService.findById("1")).thenReturn(Optional.of(product));
        Map<String, String> invalidSpecs = Map.of("color", "amarillo", "size", "M", "stock", "abc"); // stock inválido
        ResponseEntity<String> response = productController.addVariantToProduct("1", invalidSpecs);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El stock tiene que ser un numero", response.getBody());
    }

    @Test
    void testGetProductById_whenProductHasNoVariants() {
        product.setVariants(new ArrayList<>());
        when(productService.findById("1")).thenReturn(Optional.of(product));
        ResponseEntity<Product> response = productController.getProductById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }


    @Test
    void testGetVariantByProductIdAndVid_whenVariantDoesNotExist() {
        when(productService.findById("1")).thenReturn(Optional.of(product));
        String nonExistentVid = UUID.randomUUID().toString();
        ResponseEntity<Variant> response = productController.getVariantByProductIdAndVid("1", nonExistentVid);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteVariant_whenVariantDoesNotExist() {
        when(productService.findById("1")).thenReturn(Optional.of(product));
        String nonExistentVid = UUID.randomUUID().toString();
        ResponseEntity<String> response = productController.deleteProduct("1", nonExistentVid);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Variante no encontrada", response.getBody());
    }

    @Test
    void testGetAllProducts_whenNoProductsExist() {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

}
