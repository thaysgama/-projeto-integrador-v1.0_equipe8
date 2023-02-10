package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.UserDTORequest;
import com.dh.digitalbooking.dto.UserDTOResponse;
import com.dh.digitalbooking.entities.UserEntity;
import com.dh.digitalbooking.exceptions.UserNotFoundException;
import com.dh.digitalbooking.services.impl.RoleServiceImpl;
import com.dh.digitalbooking.services.impl.UserServiceImpl;
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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static com.dh.digitalbooking.utils.ProductUtils.productBuilder;
import static com.dh.digitalbooking.utils.RoleUtils.roleBuilder;
import static com.dh.digitalbooking.utils.UserUtils.*;
import static com.dh.digitalbooking.utils.UserUtils.userBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
class UserControllerTest {

    private static final String USER_API_URL_PATH = "/api/v1/user";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private RoleServiceImpl roleService;

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void findAllShouldReturnSuccess() throws Exception {
        Page<UserEntity> usersPage = usersPageBuilder();
        Page<UserDTOResponse> usersDTOPage = usersPage.map(UserDTOResponse::new);

        when(userService.findAll(any(Pageable.class))).thenReturn(usersPage);

        mockMvc.perform(get(USER_API_URL_PATH))
                .andExpect(content().json(objectMapper.writeValueAsString(usersDTOPage)))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"/data_test_categories.sql"})
    void saveShouldReturnSucess() throws Exception {
        UserEntity user = clientBuilderWithoutId();
        UserDTORequest userDTO = new UserDTORequest(user.getFirstName(),user.getLastName(),
                user.getEmail(),user.getPassword(),user.getRole().getId());

        String token = UUID.randomUUID().toString();
        when(userService.save(any(UserEntity.class))).thenReturn(token);
        when(roleService.findById(any(Integer.class))).thenReturn(roleBuilder());


            mockMvc.perform(post(USER_API_URL_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
   @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void findByIdShouldReturnSuccess() throws Exception {
        UserEntity user = userBuilder();
        UserDTOResponse userDTO = new UserDTOResponse(user);

        when(userService.findById(1)).thenReturn(user);

        mockMvc.perform(get(USER_API_URL_PATH+"/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDTO)));
    }

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void findByIdShouldReturnNotFound() throws Exception {
        when(userService.findById(1)).thenThrow(UserNotFoundException.class);

        mockMvc.perform(get(USER_API_URL_PATH+"/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void deleteByIdShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete(USER_API_URL_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "ADMIN")
    void updateShouldReturnSuccess() throws Exception {
        UserEntity user = userBuilder();
        UserDTORequest userDTO = new UserDTORequest(user.getFirstName(),user.getLastName(),
                user.getEmail(),user.getPassword(),user.getRole().getId());
        userDTO.setId(1);

        when(userService.update(any(UserEntity.class))).thenReturn(user);
        when(userService.findById(any(Integer.class))).thenReturn(userBuilder());
        when(roleService.findById(any(Integer.class))).thenReturn(roleBuilder());

        mockMvc.perform(put(USER_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

}