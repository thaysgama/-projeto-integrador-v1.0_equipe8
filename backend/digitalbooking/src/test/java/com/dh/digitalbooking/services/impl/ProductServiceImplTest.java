package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.*;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;
import com.dh.digitalbooking.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static com.dh.digitalbooking.utils.ProductUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Autowired
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findAllShouldReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 20);
        when(productRepository.findAll(pageable)).thenReturn(productsPageBuilder());
        Page<ProductEntity> page = productService.findAll(pageable);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    void testGivenCategoryThenReturnSuccessfullySavedObject() {
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productBuilder());

        ProductEntity product = productBuilderWithoutId();
        ProductEntity savedProduct = productService.save(product);
        assertNotNull(savedProduct.getId());
    }

    @Test
    void testGivenValidIdThenReturnSuccessfullyFoundObject() throws ProductNotFoundException {
        when(productRepository.findById(1)).thenReturn(Optional.of(productBuilder()));

        ProductEntity expectedProduct = productBuilder();
        ProductEntity product = productService.findById(1);
        assertEquals(expectedProduct.getId(), product.getId());
        assertEquals(expectedProduct.getName(), product.getName());
        assertEquals(expectedProduct.getDescription(), product.getDescription());
        assertEquals(expectedProduct.getLatitude(), product.getLatitude());
        assertEquals(expectedProduct.getLongitude(), product.getLongitude());
    }

    @Test
    void testGivenValidCategoryThenReturnSuccessfullyUpdatedObject() throws ProductNotFoundException {
        ProductEntity productInDatabase = productBuilder();
        productInDatabase.setName("Caucaia");

        when(productRepository.findById(1)).thenReturn(Optional.of(productInDatabase));

        ProductEntity expectedProduct = productBuilder();
        when(productRepository.save(any(ProductEntity.class))).thenReturn(expectedProduct);

        ProductEntity product= productService.update(expectedProduct);

        assertEquals(expectedProduct.getId(), product.getId());
        assertEquals(expectedProduct.getName(), product.getName());
        assertEquals(expectedProduct.getDescription(), product.getDescription());
        assertEquals(expectedProduct.getLatitude(), product.getLatitude());
        assertEquals(expectedProduct.getLongitude(), product.getLongitude());
    }

    @Test
    void testGivenValidCategoryIdThenReturnSuccesOnDelete() throws ProductNotFoundException {
        Integer deletedProductId = 1;
        ProductEntity expectedProductToDelete = productBuilder();

        when(productRepository.findById(deletedProductId)).thenReturn(Optional.of(expectedProductToDelete));
        productService.deleteById(deletedProductId);

        verify(productRepository, times(1)).deleteById(deletedProductId);
    }



}