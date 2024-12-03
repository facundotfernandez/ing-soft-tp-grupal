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
import static org.mockito.ArgumentMatchers.any;
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
        product.setVariants(new ArrayList<>());
        Map<String, String> specs = Map.of("color", "amarillo", "size", "M");
        when(productService.addVariantToProduct("1", specs)).thenReturn(new ResponseEntity<>("Variante agregada exitosamente", HttpStatus.OK));
        ResponseEntity<String> response = productController.addVariantToProduct("1", specs);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Variante agregada exitosamente", response.getBody());
    }

    @Test
    void testGetProductById_whenProductExists() {
        when(productService.getProductById("1")).thenReturn(new ResponseEntity<>(Optional.of(product), HttpStatus.OK));
        ResponseEntity<Optional<Product>> response = productController.getProductById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody().get());
    }

    @Test
    void testGetProductById_whenProductDoesNotExist() {
        when(productService.getProductById("1")).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        ResponseEntity<Optional<Product>> response = productController.getProductById("1");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testUpdateVariantStock_whenVariantExists() {

        Variant variant = new Variant();
        variant.setStock(10);
        product.getVariants().add(variant);
        when(productService.updateVariantStock("1",variant.getVid(),20)).thenReturn(new ResponseEntity<>("Variante modificada exitosamente", HttpStatus.OK));
        ResponseEntity<String> response = productController.updateVariantStock("1", variant.getVid(), 20);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Variante modificada exitosamente", response.getBody());
    }

    @Test
    void testAddProductWithVariants() {
        Product newProduct = new Product();
        newProduct.setName("New Product");
        Variant variant1 = new Variant();
        Variant variant2 = new Variant();
        newProduct.setVariants(List.of(variant1, variant2));

        when(productService.saveProduct(newProduct)).thenReturn(new ResponseEntity<>("Producto agregado exitosamente", HttpStatus.CREATED));

        ResponseEntity<String> response = productController.addProduct(newProduct);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Producto agregado exitosamente", response.getBody());
        verify(productService, times(1)).saveProduct(newProduct);
    }

    @Test
    void testDeleteProduct_whenProductExists() {
        when(productService.deleteProduct("1")).thenReturn(new ResponseEntity<>("Producto eliminado exitosamente", HttpStatus.OK));
        ResponseEntity<String> response = productController.deleteProduct("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto eliminado exitosamente", response.getBody());
        verify(productService, times(1)).deleteProduct("1");
    }

    @Test
    void testDeleteProduct_whenProductDoesNotExist() {
        when(productService.deleteProduct("1")).thenReturn(new ResponseEntity<>("El producto no existe", HttpStatus.NOT_FOUND));
        ResponseEntity<String> response = productController.deleteProduct("1");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("El producto no existe", response.getBody());
    }



    @Test
    void testUpdateVariantStock_whenVariantDoesNotExist() {
        // when(productService.getProductById("1")).thenReturn(new ResponseEntity<>(Optional.of(product), HttpStatus.OK));
        Variant variant = new Variant();
        variant.setStock(10);
        product.getVariants().add(variant);
        String nonExistentVid = UUID.randomUUID().toString();
        when(productService.updateVariantStock("1",nonExistentVid,20)).thenReturn(new ResponseEntity<>("Variante no encontrada", HttpStatus.NOT_FOUND));
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
        when(productService.getAllProducts()).thenReturn(new ResponseEntity<>(products, HttpStatus.OK));
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testAddProduct_whenProductDoesNotExist() {
        Product newProduct = new Product();
        newProduct.setName("Nuevo Producto");
        when(productService.saveProduct(newProduct)).thenReturn(new ResponseEntity<>("Producto agregado exitosamente", HttpStatus.CREATED));
        ResponseEntity<String> response = productController.addProduct(newProduct);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Producto agregado exitosamente", response.getBody());
        verify(productService, times(1)).saveProduct(newProduct);
    }

    @Test
    void testAddVariantToProduct_withInvalidSpecs() {
        Map<String, String> invalidSpecs = Map.of("color", "amarillo", "size", "M", "stock", "abc"); // stock inválido
        when(productService.addVariantToProduct("1",invalidSpecs)).thenReturn(new ResponseEntity<>("El stock tiene que ser un numero", HttpStatus.BAD_REQUEST));
        ResponseEntity<String> response = productController.addVariantToProduct("1", invalidSpecs);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El stock tiene que ser un numero", response.getBody());
    }

    @Test
    void testGetProductById_whenProductHasNoVariants() {
        product.setVariants(new ArrayList<>());
        when(productService.getProductById("1")).thenReturn(new ResponseEntity<>(Optional.of(product), HttpStatus.OK));
        ResponseEntity<Optional<Product>> response = productController.getProductById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody().get());
    }


    @Test
    void testGetVariantByProductIdAndVid_whenVariantDoesNotExist() {
        
        String nonExistentVid = UUID.randomUUID().toString();
        when(productService.findByPIDandVID("1",nonExistentVid)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        ResponseEntity<Variant> response = productController.getVariantByProductIdAndVid("1", nonExistentVid);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteVariant_whenVariantDoesNotExist() {

        String nonExistentVid = UUID.randomUUID().toString();
        when(productService.deleteVariant("1",nonExistentVid)).thenReturn(new ResponseEntity<>("Variante no encontrada", HttpStatus.NOT_FOUND));
        ResponseEntity<String> response = productController.deleteVariant("1", nonExistentVid);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Variante no encontrada", response.getBody());
    }

    @Test
    void testGetAllProducts_whenNoProductsExist() {
        when(productService.getAllProducts()).thenReturn(new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT));
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

}
