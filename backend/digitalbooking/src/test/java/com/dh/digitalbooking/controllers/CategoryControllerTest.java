package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.CategoryDTO;
import com.dh.digitalbooking.entities.CategoryEntity;
import com.dh.digitalbooking.exceptions.CategoryNotFoundException;
import com.dh.digitalbooking.services.impl.CategoryServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
class CategoryControllerTest {

    private static final String CATEGORY_API_URL_PATH = "/api/v1/category";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryServiceImpl categoryService;


    @Test
    void findAllShouldReturnSuccess() throws Exception {
        CategoryEntity category = new CategoryEntity("Cidade","destinos urbanos", "www.image.com/urb");
        category.setId(1);
        Page<CategoryEntity> categoriesPage = new PageImpl<>(List.of(category));
        Page<CategoryDTO> categoriesDTOPage = new PageImpl<>(List.of(new CategoryDTO(category)));

        Pageable pageable = PageRequest.of(0, 20);

        when(categoryService.findAll(pageable)).thenReturn(categoriesPage);

        mockMvc.perform(get(CATEGORY_API_URL_PATH))
                .andExpect(content().json(objectMapper.writeValueAsString(categoriesDTOPage)))
                .andExpect(status().isOk());
    }

    @Test
    void saveShouldReturnSucess() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(1, "Cidade","destinos urbanos", "www.image.com/urb");
        CategoryEntity category = new CategoryEntity("Cidade","destinos urbanos", "www.image.com/urb");
        category.setId(1);

        when(categoryService.save(any(CategoryEntity.class))).thenReturn(category);

        mockMvc.perform(post(CATEGORY_API_URL_PATH)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void findByIdShouldReturnSuccess() throws Exception {
        CategoryEntity category = new CategoryEntity("Cidade","destinos urbanos", "www.image.com/urb");
        category.setId(1);
        when(categoryService.findById(1)).thenReturn(category);

        CategoryDTO categoryDTO = new CategoryDTO(category);

        mockMvc.perform(get(CATEGORY_API_URL_PATH+"/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryDTO)));
    }

    @Test
    void findByIdShouldReturnNotFound() throws Exception {
        when(categoryService.findById(1)).thenThrow(CategoryNotFoundException.class);

        mockMvc.perform(get(CATEGORY_API_URL_PATH+"/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteByIdShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete(CATEGORY_API_URL_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateShouldReturnSuccess() throws Exception {
        CategoryEntity category = new CategoryEntity("Cidade","destinos urbanos", "www.image.com/urb");
        category.setId(1);

        when(categoryService.update(any(CategoryEntity.class))).thenReturn(category);

        CategoryDTO categoryDTO = new CategoryDTO(category);

        mockMvc.perform(put(CATEGORY_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk());
    }
}