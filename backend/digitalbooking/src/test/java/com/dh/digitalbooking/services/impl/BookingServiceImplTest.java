package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.BookingEntity;
import com.dh.digitalbooking.entities.ClientEntity;
import com.dh.digitalbooking.entities.ProductEntity;
import com.dh.digitalbooking.exceptions.BookingNotFoundException;
import com.dh.digitalbooking.exceptions.InvalidDateException;
import com.dh.digitalbooking.repository.BookingRepository;
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

import java.time.LocalDate;
import java.util.Optional;

import static com.dh.digitalbooking.utils.BookingUtils.*;
import static com.dh.digitalbooking.utils.ProductUtils.productBuilder;
import static com.dh.digitalbooking.utils.UserUtils.clientBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(value = "classpath:application-test.properties")
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Autowired
    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void findAllShouldReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 20);
        when(bookingRepository.findAll(pageable)).thenReturn(bookingsPageBuilder());
        Page<BookingEntity> page = bookingService.findAll(pageable);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    void testGivenBookingThenReturnSuccessfullySavedObject() throws InvalidDateException {
        BookingEntity booking = bookingBuilderrWithoutId2();
        BookingEntity expectedBooking = bookingBuilderrWithoutId2();
        expectedBooking.setId(3);

        when(bookingRepository.findAllByProduct(any(ProductEntity.class))).thenReturn(bookingsListBuilder());
        when(bookingRepository.save(any(BookingEntity.class))).thenReturn(bookingBuilder());

        BookingEntity savedBooking = bookingService.save(booking);
        assertNotNull(savedBooking.getId());
    }

    @Test
    void testGivenBookingThenReturnException() throws InvalidDateException {
        when(bookingRepository.findAllByProduct(any(ProductEntity.class))).thenReturn(bookingsListBuilder());
        BookingEntity booking = bookingBuilderWithoutId();

        assertThrows(InvalidDateException.class, () -> bookingService.save(booking));

    }

    @Test
    void testGivenValidIdThenReturnSuccessfullyFoundObject() throws BookingNotFoundException {
        BookingEntity expectedBooking = bookingBuilder();
        when(bookingRepository.findById(1)).thenReturn(Optional.of(expectedBooking));

        BookingEntity booking = bookingService.findById(1);
        assertEquals(expectedBooking.getId(), booking.getId());
        assertEquals(expectedBooking.getCheckInDate(), booking.getCheckInDate());
        assertEquals(expectedBooking.getBookingTime(), booking.getBookingTime());
        assertEquals(expectedBooking.getCheckOutDate(), booking.getCheckOutDate());
        assertEquals(expectedBooking.getProduct(), booking.getProduct());
        assertEquals(expectedBooking.getClient(), booking.getClient());
    }

    @Test
    void testGivenValidProductThenReturnSuccessfullyFoundBookings() throws BookingNotFoundException {
        ProductEntity product = productBuilder();
        when(bookingRepository.findAllByProduct(any(ProductEntity.class), any(Pageable.class))).thenReturn(bookingsPageBuilder());

        Pageable pageable = PageRequest.of(0, 20);
        Page<BookingEntity> page = bookingService.findAllByProduct(product, pageable);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    void testGivenValidClientThenReturnSuccessfullyFoundBookings() throws BookingNotFoundException {
        ClientEntity client = clientBuilder();
        when(bookingRepository.findAllByClient(any(ClientEntity.class), any(Pageable.class))).thenReturn(bookingsPageBuilder());

        Pageable pageable = PageRequest.of(0, 20);
        Page<BookingEntity> page = bookingService.findAllByClient(client, pageable);
        assertEquals(2, page.getTotalElements());
    }

    @Test
    void testGivenValidBookingThenReturnSuccessfullyUpdatedObject() throws BookingNotFoundException, InvalidDateException {
        BookingEntity bookingInDatabase = bookingBuilder();
        bookingInDatabase.setCheckOutDate(LocalDate.of(2022,8,4));

        when(bookingRepository.findById(1)).thenReturn(Optional.of(bookingInDatabase));

        BookingEntity expectedBooking = bookingBuilder();
        when(bookingRepository.save(any(BookingEntity.class))).thenReturn(expectedBooking);

        BookingEntity booking= bookingService.update(expectedBooking);

        assertEquals(expectedBooking.getId(), booking.getId());
        assertEquals(expectedBooking.getCheckInDate(), booking.getCheckInDate());
        assertEquals(expectedBooking.getBookingTime(), booking.getBookingTime());
        assertEquals(expectedBooking.getCheckOutDate(), booking.getCheckOutDate());
        assertEquals(expectedBooking.getProduct(), booking.getProduct());
        assertEquals(expectedBooking.getClient(), booking.getClient());
    }

    @Test
    void testGivenValidBookingIdThenReturnSuccesOnDelete() throws BookingNotFoundException {
        Integer deletedBookingId = 1;
        BookingEntity expectedBookingToDelete = bookingBuilder();

        when(bookingRepository.findById(deletedBookingId)).thenReturn(Optional.of(expectedBookingToDelete));
        bookingService.deleteById(deletedBookingId);

        verify(bookingRepository, times(1)).deleteById(deletedBookingId);
    }

}