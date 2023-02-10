package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.dto.BookingDTO;
import com.dh.digitalbooking.entities.*;
import com.dh.digitalbooking.exceptions.InvalidDateException;
import com.dh.digitalbooking.exceptions.UserNotFoundException;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;
import com.dh.digitalbooking.exceptions.BookingNotFoundException;
import com.dh.digitalbooking.services.impl.UserServiceImpl;
import com.dh.digitalbooking.services.impl.ProductServiceImpl;
import com.dh.digitalbooking.services.impl.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/v1/booking")
@CrossOrigin(origins = "*", allowedHeaders = "")
public class BookingController {

    private final BookingServiceImpl bookingService;
    private final UserServiceImpl userService;
    private final ProductServiceImpl productService;

    @Autowired
    public BookingController(BookingServiceImpl bookingService, UserServiceImpl userService,
                             ProductServiceImpl productService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BookingDTO> findAll(Pageable pageable){
        Page<BookingEntity> bookingsList = bookingService.findAll(pageable);
        return bookingsList.map(BookingDTO::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDTO save(@RequestBody @Valid BookingDTO bookingDTO) throws
            UserNotFoundException, ProductNotFoundException, InvalidDateException {
        ProductEntity product = productService.findById(bookingDTO.getProductId());
        UserEntity user = userService.findById(bookingDTO.getClientId());

        ClientEntity clientEntity = userService.findClientById(bookingDTO.getClientId());

        BookingEntity booking = new BookingEntity(LocalTime.now().truncatedTo(ChronoUnit.SECONDS), bookingDTO.getCheckInDate(),
                bookingDTO.getCheckOutDate(), clientEntity, product);

        return new BookingDTO(bookingService.save(booking));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDTO findById(@PathVariable Integer id) throws BookingNotFoundException {
        return new BookingDTO(bookingService.findById(id));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) throws BookingNotFoundException {
        bookingService.deleteById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BookingDTO update(@RequestBody @Valid BookingDTO bookingDTO) throws
            UserNotFoundException, ProductNotFoundException, BookingNotFoundException, InvalidDateException {
        BookingEntity booking = bookingService.findById(bookingDTO.getId());
        ProductEntity product = productService.findById(bookingDTO.getProductId());
        ClientEntity client = userService.findClientById(bookingDTO.getClientId());

        booking.setBookingTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        booking.setProduct(product);
        booking.setClient(client);

        return new BookingDTO(bookingService.update(booking));
    }

    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookingDTO> findAllByProduct(@PathVariable Integer id, Pageable pageable) throws ProductNotFoundException {
        ProductEntity product = productService.findById(id);
        Page<BookingEntity> bookingsList = bookingService.findAllByProduct(product, pageable);
        return bookingsList.map(BookingDTO::new);
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookingDTO> findAllByClient(@PathVariable Integer id, Pageable pageable) throws UserNotFoundException {
        ClientEntity client = userService.findClientById(id);
        Page<BookingEntity> bookingsList = bookingService.findAllByClient(client, pageable);
        return bookingsList.map(BookingDTO::new);
    }
}
