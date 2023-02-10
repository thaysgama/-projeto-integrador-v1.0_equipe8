package com.dh.digitalbooking.repository;

import com.dh.digitalbooking.entities.RatingEntity;
import com.dh.digitalbooking.entities.RatingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, RatingPK> {
}
