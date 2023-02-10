package com.dh.digitalbooking.repository;

import com.dh.digitalbooking.entities.CategoryEntity;
import com.dh.digitalbooking.entities.CityEntity;
import com.dh.digitalbooking.entities.ProductEntity;
import com.dh.digitalbooking.entities.ProprietorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    Page<ProductEntity> findAllByCity(CityEntity city, Pageable pageable);
    Page<ProductEntity> findAllByCategory(CategoryEntity category, Pageable pageable);
    Page<ProductEntity> findAllByCityAndCategory(CityEntity city, CategoryEntity category, Pageable pageable);
    Page<ProductEntity> findAllByProprietor(ProprietorEntity proprietor, Pageable pageable);

    @Query(value = "SELECT p FROM ProductEntity p WHERE NOT EXISTS" +
            "(SELECT p FROM ProductEntity p INNER JOIN p.bookings b " +
            "WHERE b.checkOutDate >= :checkIn AND b.checkInDate <= :checkOut)")
    Page<ProductEntity> findAllByDate(@Param("checkIn")LocalDate checkIn, @Param("checkOut")LocalDate checkOut, Pageable pageable);

    @Query(value = "SELECT p FROM ProductEntity p WHERE NOT EXISTS" +
            "(SELECT p FROM ProductEntity p INNER JOIN p.bookings b " +
            "WHERE b.checkOutDate >= :checkIn AND b.checkInDate <= :checkOut) AND p.city.id = :cityId")
    Page<ProductEntity> findAllByCityAndDate(@Param("cityId")Integer cityId, @Param("checkIn")LocalDate checkIn, @Param("checkOut")LocalDate checkOut, Pageable pageable);
}
