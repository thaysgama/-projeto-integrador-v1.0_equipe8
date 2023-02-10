package com.dh.digitalbooking.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reservas")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hora_reserva")
    private LocalTime bookingTime;

    @Column(name = "data_entrada")
    private LocalDate checkInDate;

    @Column(name = "data_saida")
    private LocalDate checkOutDate;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private ProductEntity product;

    public BookingEntity(LocalTime bookingTime, LocalDate checkInDate, LocalDate checkOutDate,
                         ClientEntity client, ProductEntity product) {
        this.bookingTime = bookingTime;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.client = client;
        this.product = product;
    }
}
