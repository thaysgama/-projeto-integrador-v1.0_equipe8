package com.dh.digitalbooking.repository;

import com.dh.digitalbooking.entities.BookingEntity;
import com.dh.digitalbooking.entities.ClientEntity;
import com.dh.digitalbooking.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity,Integer> {
    Page<BookingEntity> findAllByProduct(ProductEntity product, Pageable pageable);
    Page<BookingEntity> findAllByClient(ClientEntity client, Pageable pageable);
    List<BookingEntity> findAllByProduct(ProductEntity product);
}
