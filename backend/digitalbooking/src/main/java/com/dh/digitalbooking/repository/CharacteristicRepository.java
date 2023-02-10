package com.dh.digitalbooking.repository;

import com.dh.digitalbooking.entities.CharacteristicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, Integer> {
}
