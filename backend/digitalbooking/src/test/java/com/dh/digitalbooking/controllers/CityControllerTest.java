package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.CityDTO;
import com.dh.digitalbooking.entities.CityEntity;
import com.dh.digitalbooking.exceptions.CityNotFoundException;
import com.dh.digitalbooking.services.impl.CityServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
class CityControllerTest {

    private static final String CITY_API_URL_PATH = "/api/v1/city";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityServiceImpl cityService;

    @Test
    void findAllShouldReturnSuccess() throws Exception {
        CityEntity city = new CityEntity("Fortaleza", "CE", "Brasil");
        city.setId(1);
        Page<CityEntity> citysPage = new PageImpl<>(List.of(city));
        Page<CityDTO> citysDTOPage = new PageImpl<>(List.of(new CityDTO(city)));

        Pageable pageable = PageRequest.of(0, 20);

        when(cityService.findAll(pageable)).thenReturn(citysPage);

        mockMvc.perform(get(CITY_API_URL_PATH))
                .andExpect(content().json(objectMapper.writeValueAsString(citysDTOPage)))
                .andExpect(status().isOk());
    }

    @Test
    void saveShouldReturnSucess() throws Exception {
        CityEntity city = new CityEntity("Fortaleza", "CE", "Brasil");
        city.setId(1);
        CityDTO cityDTO = new CityDTO(city);

        when(cityService.save(any(CityEntity.class))).thenReturn(city);

        mockMvc.perform(post(CITY_API_URL_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(cityDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void findByIdShouldReturnSuccess() throws Exception {
        CityEntity city = new CityEntity("Fortaleza", "CE", "Brasil");
        city.setId(1);
        CityDTO cityDTO = new CityDTO(city);

        when(cityService.findById(1)).thenReturn(city);


        mockMvc.perform(get(CITY_API_URL_PATH+"/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(cityDTO)));
    }

    @Test
    void findByIdShouldReturnNotFound() throws Exception {
        when(cityService.findById(1)).thenThrow(CityNotFoundException.class);

        mockMvc.perform(get(CITY_API_URL_PATH+"/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteByIdShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete(CITY_API_URL_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateShouldReturnSuccess() throws Exception {
        CityEntity city = new CityEntity("Fortaleza", "CE", "Brasil");
        city.setId(1);
        CityDTO cityDTO = new CityDTO(city);

        when(cityService.update(any(CityEntity.class))).thenReturn(city);


        mockMvc.perform(put(CITY_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cityDTO)))
                .andExpect(status().isOk());
    }

}