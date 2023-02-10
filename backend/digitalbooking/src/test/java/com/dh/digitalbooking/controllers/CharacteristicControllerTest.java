package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.CharacteristicDTO;
import com.dh.digitalbooking.entities.CharacteristicEntity;
import com.dh.digitalbooking.exceptions.CharacteristicNotFoundException;
import com.dh.digitalbooking.services.impl.CharacteristicServiceImpl;
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
class CharacteristicControllerTest {

    private static final String CHARACTERISTIC_API_URL_PATH = "/api/v1/characteristic";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CharacteristicServiceImpl characteristicService;

    @Test
    void findAllShouldReturnSuccess() throws Exception {
        CharacteristicEntity characteristic = new CharacteristicEntity("wifi","icon");
        characteristic.setId(1);
        Page<CharacteristicEntity> characteristicsPage = new PageImpl<>(List.of(characteristic));
        Page<CharacteristicDTO> characteristicDTOPage = new PageImpl<>(List.of(new CharacteristicDTO(characteristic)));

        Pageable pageable = PageRequest.of(0, 20);

        when(characteristicService.findAll(pageable)).thenReturn(characteristicsPage);

        mockMvc.perform(get(CHARACTERISTIC_API_URL_PATH))
                .andExpect(content().json(objectMapper.writeValueAsString(characteristicDTOPage)))
                .andExpect(status().isOk());
    }

    @Test
    void saveShouldReturnSucess() throws Exception {
        CharacteristicDTO characteristicDTO = new CharacteristicDTO(1,"wifi","icon");
        CharacteristicEntity characteristic = new CharacteristicEntity("wifi","icon");
        characteristic.setId(1);

        when(characteristicService.save(any(CharacteristicEntity.class))).thenReturn(characteristic);

        mockMvc.perform(post(CHARACTERISTIC_API_URL_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(characteristicDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void findByIdShouldReturnSuccess() throws Exception {
        CharacteristicEntity characteristic = new CharacteristicEntity("wifi","icon");
        characteristic.setId(1);
        when(characteristicService.findById(1)).thenReturn(characteristic);

        CharacteristicDTO characteristicDTO = new CharacteristicDTO(characteristic);

        mockMvc.perform(get(CHARACTERISTIC_API_URL_PATH+"/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(characteristicDTO)));
    }

    @Test
    void findByIdShouldReturnNotFound() throws Exception {
        when(characteristicService.findById(1)).thenThrow(CharacteristicNotFoundException.class);

        mockMvc.perform(get(CHARACTERISTIC_API_URL_PATH+"/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteByIdShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete(CHARACTERISTIC_API_URL_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateShouldReturnSuccess() throws Exception {
        CharacteristicEntity characteristic = new CharacteristicEntity("wifi","icon");
        characteristic.setId(1);

        when(characteristicService.update(any(CharacteristicEntity.class))).thenReturn(characteristic);

        CharacteristicDTO characteristicDTO = new CharacteristicDTO(characteristic);

        mockMvc.perform(put(CHARACTERISTIC_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characteristicDTO)))
                .andExpect(status().isOk());
    }
}