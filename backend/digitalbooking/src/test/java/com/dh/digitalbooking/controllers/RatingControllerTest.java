package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.ProductDTOResponse;
import com.dh.digitalbooking.dto.RatingDTO;
import com.dh.digitalbooking.entities.ProductEntity;
import com.dh.digitalbooking.entities.RatingEntity;
import com.dh.digitalbooking.repository.ProductRepository;
import com.dh.digitalbooking.repository.UserRepository;
import com.dh.digitalbooking.services.impl.RatingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static com.dh.digitalbooking.utils.ProductUtils.productBuilder;
import static com.dh.digitalbooking.utils.RatingUtils.ratingBuilder;
import static com.dh.digitalbooking.utils.UserUtils.userBuilder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
class RatingControllerTest {

    private static final String RATING_API_URL_PATH = "/api/v1/rating";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RatingServiceImpl ratingService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "CLIENT")
    void saveShouldReturnSuccess() throws Exception {
        ProductEntity product = productBuilder();
        product.setRatings(Set.of(ratingBuilder()));

        when(ratingService.save(any(RatingEntity.class))).thenReturn(product);
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(userBuilder()));
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(productBuilder()));

        RatingDTO rating = new RatingDTO(1, 1, 5);;

        mockMvc.perform(put(RATING_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rating)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ProductDTOResponse(product))));
    }
}