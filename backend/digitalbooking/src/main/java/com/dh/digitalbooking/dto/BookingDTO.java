package com.dh.digitalbooking.dto;

import com.dh.digitalbooking.entities.BookingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class BookingDTO {

    private Integer id;

    private LocalTime bookingTime;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Integer clientId;

    private Integer productId;

    public BookingDTO(BookingEntity booking) {
        this.id = booking.getId();
        this.bookingTime = booking.getBookingTime();
        this.checkInDate = booking.getCheckInDate();
        this.checkOutDate = booking.getCheckOutDate();
        this.clientId = booking.getClient().getId();
        this.productId = booking.getProduct().getId();
    }
}
