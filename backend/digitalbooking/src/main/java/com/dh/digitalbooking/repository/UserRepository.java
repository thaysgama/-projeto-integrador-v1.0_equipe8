package com.dh.digitalbooking.repository;

import com.dh.digitalbooking.entities.ClientEntity;
import com.dh.digitalbooking.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);
    @Query (value = "SELECT c FROM UserEntity c WHERE c.id = :idClient")
    Optional<ClientEntity> findClientById(@Param("idClient") Integer idClient);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);
}
