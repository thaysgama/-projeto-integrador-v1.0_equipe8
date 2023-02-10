package com.dh.digitalbooking.repository;

import com.dh.digitalbooking.entities.ImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    @Query(value = "SELECT i FROM ImageEntity i WHERE i.product.id = :id")
    Page<ImageEntity> findAllImagesOfProduct(@Param("id") Integer id, Pageable pageable);
}
