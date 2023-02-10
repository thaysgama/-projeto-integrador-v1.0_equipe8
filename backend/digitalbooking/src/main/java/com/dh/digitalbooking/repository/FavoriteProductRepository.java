package com.dh.digitalbooking.repository;

import com.dh.digitalbooking.entities.FavoriteProductEntity;
import com.dh.digitalbooking.entities.FavoriteProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProductEntity, FavoriteProductPK> {
}
