package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.RoleDTO;
import com.dh.digitalbooking.entities.RoleEntity;
import com.dh.digitalbooking.exceptions.RoleNotFoundException;
import com.dh.digitalbooking.services.impl.RoleServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.dh.digitalbooking.utils.RoleUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
class RoleControllerTest {

    private static final String ROLE_API_URL_PATH = "/api/v1/role";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleServiceImpl roleService;

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void findAllShouldReturnSuccess() throws Exception {
        Page<RoleEntity> rolesPage = rolesPageBuilder();
        Page<RoleDTO> rolesDTOPage = rolesPage.map(RoleDTO::new);

        when(roleService.findAll(any(Pageable.class))).thenReturn(rolesPage);

        mockMvc.perform(get(ROLE_API_URL_PATH))
                .andExpect(content().json(objectMapper.writeValueAsString(rolesDTOPage)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void saveShouldReturnSucess() throws Exception {
        RoleEntity role = roleBuilderWithoutId();
        RoleDTO roleDTO = new RoleDTO(role);
        role.setId(1);

        when(roleService.save(any(RoleEntity.class))).thenReturn(role);

        mockMvc.perform(post(ROLE_API_URL_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void findByIdShouldReturnSuccess() throws Exception {
        RoleEntity role = roleBuilder();
        RoleDTO roleDTO = new RoleDTO(role);

        when(roleService.findById(1)).thenReturn(role);

        mockMvc.perform(get(ROLE_API_URL_PATH+"/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(roleDTO)));
    }

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void findByIdShouldReturnNotFound() throws Exception {
        when(roleService.findById(1)).thenThrow(RoleNotFoundException.class);

        mockMvc.perform(get(ROLE_API_URL_PATH+"/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void deleteByIdShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete(ROLE_API_URL_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void updateShouldReturnSuccess() throws Exception {
        RoleEntity role = roleBuilder();
        RoleDTO roleDTO = new RoleDTO(role);

        when(roleService.update(any(RoleEntity.class))).thenReturn(role);

        mockMvc.perform(put(ROLE_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleDTO)))
                .andExpect(status().isOk());
    }

}