package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.ImageDTO;
import com.dh.digitalbooking.dto.ProductDTORequest;
import com.dh.digitalbooking.dto.ProductDTOResponse;
import com.dh.digitalbooking.entities.*;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;
import com.dh.digitalbooking.services.impl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.dh.digitalbooking.utils.CategoryUtils.categoryBuilder;
import static com.dh.digitalbooking.utils.CharacteristicUtils.characteristicBuilder;
import static com.dh.digitalbooking.utils.CityUtils.cityBuilder;
import static com.dh.digitalbooking.utils.ProductUtils.productBuilder;
import static com.dh.digitalbooking.utils.UserUtils.proprietorBuilder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
class ProductControllerTest {

    private static final String PRODUCT_API_URL_PATH = "/api/v1/product";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductServiceImpl productService;

    @MockBean
    private CityServiceImpl cityService;

    @MockBean
    private CategoryServiceImpl categoryService;

    @MockBean
    private CharacteristicServiceImpl characteristicService;

    @MockBean
    private UserServiceImpl userService;

    @Test
    void findAllShouldReturnSuccess() throws Exception {
        ProductEntity product = productBuilder();
        Page<ProductEntity> productsPage = new PageImpl<>(List.of(product));
        Page<ProductDTOResponse> productDTOPage = new PageImpl<>(List.of(new ProductDTOResponse(product)));

        Pageable pageable = PageRequest.of(0, 20);

        when(productService.findAll(pageable)).thenReturn(productsPage);

        mockMvc.perform(get(PRODUCT_API_URL_PATH))
                .andExpect(content().json(objectMapper.writeValueAsString(productDTOPage)))
                .andExpect(status().isOk());
    }

    @Test
    void saveShouldReturnSucess() throws Exception {
        ProductEntity product = productBuilder();
        ProductDTOResponse productDTOResponse = new ProductDTOResponse(product);

        ImageDTO imageDTO = new ImageDTO("Sala-1", "www.image.com/imagem1");
        ProductDTORequest productDTORequest = new ProductDTORequest(1,"Flat", "Esta acomodação está a beira mar.",
                "Rua do amor, 88",-3.71852584954214,-38.520076257714315,"Não aceita fumantes",
                "Comprovante de vacinação","Grátis", List.of(1), List.of(imageDTO), 1, 1,1);

        CityEntity city = cityBuilder();
        CategoryEntity category = categoryBuilder();
        CharacteristicEntity characteristic = characteristicBuilder();

        when(userService.findById(1)).thenReturn(proprietorBuilder());
        when(productService.save(any(ProductEntity.class))).thenReturn(product);
        when(cityService.findById(1)).thenReturn(city);
        when(categoryService.findById(1)).thenReturn(category);
        when(characteristicService.findByIdOrNull(1)).thenReturn(characteristic);

        mockMvc.perform(post(PRODUCT_API_URL_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productDTORequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(productDTOResponse)));

    }

    @Test
    void findByIdShouldReturnSuccess() throws Exception {
        ProductEntity product = productBuilder();
        product.setId(1);
        ProductDTOResponse productDTOResponse = new ProductDTOResponse(product);

        when(productService.findById(1)).thenReturn(product);


        mockMvc.perform(get(PRODUCT_API_URL_PATH+"/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDTOResponse)));
    }

    @Test
    void findByIdShouldReturnNotFound() throws Exception {
        when(productService.findById(1)).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(get(PRODUCT_API_URL_PATH+"/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteByIdShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete(PRODUCT_API_URL_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateShouldReturnSuccess() throws Exception {
        ProductEntity product = productBuilder();
        ProductDTOResponse productDTOResponse = new ProductDTOResponse(product);

        ImageDTO imageDTO = new ImageDTO("Sala-1", "www.image.com/imagem1");
        imageDTO.setId(1);
        ProductDTORequest productDTORequest = new ProductDTORequest(1,"Flat", "Esta acomodação está a beira mar.",
                "Rua do amor, 88",-3.71852584954214,-38.520076257714315,"Não aceita fumantes",
                "Comprovante de vacinação","Grátis", List.of(1), List.of(imageDTO), 1, 1,1);

        CityEntity city = cityBuilder();
        CategoryEntity category = categoryBuilder();
        CharacteristicEntity characteristic = characteristicBuilder();

        when(productService.update(any(ProductEntity.class))).thenReturn(product);
        when(productService.findById(1)).thenReturn(product);
        when(cityService.findById(1)).thenReturn(city);
        when(categoryService.findById(1)).thenReturn(category);
        when(characteristicService.findByIdOrNull(1)).thenReturn(characteristic);

        mockMvc.perform(put(PRODUCT_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTORequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDTOResponse)));
    }



}