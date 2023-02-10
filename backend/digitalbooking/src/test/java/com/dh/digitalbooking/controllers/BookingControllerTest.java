package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.BookingDTO;
import com.dh.digitalbooking.entities.BookingEntity;
import com.dh.digitalbooking.exceptions.BookingNotFoundException;
import com.dh.digitalbooking.services.impl.BookingServiceImpl;
import com.dh.digitalbooking.services.impl.ProductServiceImpl;
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
import org.springframework.test.web.servlet.MockMvc;

import static com.dh.digitalbooking.utils.BookingUtils.*;
import static com.dh.digitalbooking.utils.ProductUtils.productBuilder;
import static com.dh.digitalbooking.utils.UserUtils.clientBuilder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "classpath:application-test.properties")
@AutoConfigureMockMvc
class BookingControllerTest {

    private static final String BOOKING_API_URL_PATH = "/api/v1/booking";
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private BookingServiceImpl bookingService;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private ProductServiceImpl productService;


    @Test
    @WithMockUser(username = "thays@gama.com", roles = "CLIENT")
    void findAllShouldReturnSuccess() throws Exception {
        Page<BookingEntity> bookingsPage = bookingsPageBuilder();
        Page<BookingDTO> bookingsDTOPage = bookingsPage.map(BookingDTO::new);

        when(bookingService.findAll(any(Pageable.class))).thenReturn(bookingsPage);

        mockMvc.perform(get(BOOKING_API_URL_PATH))
                .andExpect(content().json(objectMapper.writeValueAsString(bookingsDTOPage)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "CLIENT")
    void saveShouldReturnSucess() throws Exception {
        BookingEntity booking = bookingBuilderWithoutId();
        BookingDTO bookingDTO = new BookingDTO(booking);
        booking.setId(1);

        when(bookingService.save(any(BookingEntity.class))).thenReturn(booking);

        mockMvc.perform(post(BOOKING_API_URL_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "CLIENT")
    void findByIdShouldReturnSuccess() throws Exception {
        BookingEntity booking = bookingBuilder();
        when(bookingService.findById(1)).thenReturn(booking);

        BookingDTO bookingDTO = new BookingDTO(booking);

        mockMvc.perform(get(BOOKING_API_URL_PATH+"/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bookingDTO)));
    }

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "CLIENT")
    void findByIdShouldReturnNotFound() throws Exception {
        when(bookingService.findById(1)).thenThrow(BookingNotFoundException.class);

        mockMvc.perform(get(BOOKING_API_URL_PATH+"/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(username = "thays@gama.com", roles = "CLIENT")
    void deleteByIdShouldReturnSuccess() throws Exception {
        mockMvc.perform(delete(BOOKING_API_URL_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "thays@gama.com", roles = "CLIENT")
    void updateShouldReturnSuccess() throws Exception {
        BookingEntity booking = bookingBuilder();

        when(bookingService.update(any(BookingEntity.class))).thenReturn(booking);
        when(bookingService.findById(any(Integer.class))).thenReturn(booking);
        when(productService.findById(any(Integer.class))).thenReturn(productBuilder());
        when(userService.findClientById(any(Integer.class))).thenReturn(clientBuilder());

        BookingDTO bookingDTO = new BookingDTO(booking);

        mockMvc.perform(put(BOOKING_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isOk());
    }
}