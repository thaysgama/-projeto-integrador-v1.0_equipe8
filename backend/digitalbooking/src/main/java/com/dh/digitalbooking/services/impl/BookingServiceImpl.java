package com.dh.digitalbooking.services.impl;

import com.dh.digitalbooking.entities.BookingEntity;
import com.dh.digitalbooking.entities.ClientEntity;
import com.dh.digitalbooking.entities.ProductEntity;
import com.dh.digitalbooking.exceptions.BookingNotFoundException;
import com.dh.digitalbooking.exceptions.InvalidDateException;
import com.dh.digitalbooking.repository.BookingRepository;
import com.dh.digitalbooking.services.IBookingService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements IBookingService<BookingEntity> {

    @Autowired
    private BookingRepository bookingRepository;
    private final Logger log = Logger.getLogger(BookingServiceImpl.class);

    @Override
    public Page<BookingEntity> findAll(Pageable pageable){
        log.info("Listing all bookings.");
        return bookingRepository.findAll(pageable);
    }

    @Override
    public BookingEntity save(BookingEntity booking) throws InvalidDateException {
        validateDatesSave(booking.getProduct(), booking.getCheckInDate(), booking.getCheckOutDate());
        log.info("Saving booking.");
        return bookingRepository.save(booking);
    }

    @Override
    public BookingEntity update(BookingEntity booking) throws BookingNotFoundException, InvalidDateException {
        verifyIfExists(booking.getId());
        validateDatesUpdate(booking.getProduct(), booking.getCheckInDate(), booking.getCheckOutDate(), booking);
        log.info("Updating booking with id = "+booking.getId()+".");
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Integer id) throws BookingNotFoundException {
        verifyIfExists(id);
        log.info("Deleting booking with id = "+id+".");
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingEntity findById(Integer id) throws BookingNotFoundException {
        BookingEntity booking = verifyIfExists(id);
        log.info("Finding booking with id = "+id+".");
        return booking;
    }

    @Override
    public Page<BookingEntity> findAllByProduct(ProductEntity product, Pageable pageable) {
        log.info("Listing all bookings of product with id = "+ product.getId()+".");
        return bookingRepository.findAllByProduct(product, pageable);
    }

    @Override
    public Page<BookingEntity> findAllByClient(ClientEntity client, Pageable pageable) {
        log.info("Listing all bookings of client with id = "+ client.getId()+".");
        return bookingRepository.findAllByClient(client, pageable);
    }

    private BookingEntity verifyIfExists(Integer id) throws BookingNotFoundException {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
    }

    private void validateDatesSave(ProductEntity product, LocalDate checkInDate, LocalDate checkOutDate) throws InvalidDateException {
        List<BookingEntity> bookingsList = bookingRepository.findAllByProduct(product);
        validateDates(checkInDate, checkOutDate, bookingsList);
    }

    private void validateDatesUpdate(ProductEntity product, LocalDate checkInDate, LocalDate checkOutDate, BookingEntity booking) throws InvalidDateException {
        List<BookingEntity> bookingsList = bookingRepository.findAllByProduct(product);
        bookingsList.remove(booking);
        validateDates(checkInDate, checkOutDate, bookingsList);
    }

    private void validateDates(LocalDate checkInDate, LocalDate checkOutDate, List<BookingEntity> bookingsList) throws InvalidDateException {
        LocalDate today = LocalDate.now();
        if(!checkInDate.isAfter(today) || !checkOutDate.isAfter(checkInDate)) {
            throw new InvalidDateException();
        }
        for(BookingEntity existing : bookingsList) {
            if(!(checkInDate.isAfter(existing.getCheckOutDate()) || checkOutDate.isBefore(existing.getCheckInDate())))
                throw new InvalidDateException();
        }
    }
}
