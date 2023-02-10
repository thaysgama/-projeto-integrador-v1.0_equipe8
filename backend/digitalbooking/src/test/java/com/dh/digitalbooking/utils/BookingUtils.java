package com.dh.digitalbooking.utils;

import com.dh.digitalbooking.entities.BookingEntity;
import com.dh.digitalbooking.entities.ClientEntity;
import com.dh.digitalbooking.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;;

import static com.dh.digitalbooking.utils.ProductUtils.productBuilder;
import static com.dh.digitalbooking.utils.UserUtils.clientBuilder;

public class BookingUtils {

    public static BookingEntity bookingBuilderWithoutId(){
        ProductEntity product = productBuilder();
        ClientEntity client = clientBuilder();
        BookingEntity booking = new BookingEntity(LocalTime.now(),
                LocalDate.of(2022,8,1),LocalDate.of(2022,8,10),
                client,product);
        return booking;
    }

    public static BookingEntity bookingBuilder(){
        BookingEntity booking = bookingBuilderWithoutId();
        booking.setId(1);
        return booking;
    }
    public static BookingEntity bookingBuilderrWithoutId2(){
        ProductEntity product = productBuilder();
        ClientEntity client = clientBuilder();
        BookingEntity booking = new BookingEntity(LocalTime.now(),
                LocalDate.of(2022,8,20),LocalDate.of(2022,8,25),
                client,product);
        return booking;
    }

    public static Page<BookingEntity> bookingsPageBuilder(){
        BookingEntity booking1 = bookingBuilder();
        ProductEntity product = productBuilder();
        ClientEntity client = clientBuilder();
        BookingEntity booking2 = new BookingEntity(LocalTime.now(),
                LocalDate.of(2022,8,11),LocalDate.of(2022,8,15),
                client,product);
        booking2.setId(2);
        return new PageImpl<>(Arrays.asList(booking1,booking2));
    }

    public static List<BookingEntity> bookingsListBuilder(){
        BookingEntity booking1 = bookingBuilder();
        ProductEntity product = productBuilder();
        ClientEntity client = clientBuilder();
        BookingEntity booking2 = new BookingEntity(LocalTime.now(),
                LocalDate.of(2022,8,11),LocalDate.of(2022,8,15),
                client,product);
        booking2.setId(2);
        return List.of(booking1,booking2);
    }
}
