package com.utn.UTNPhones.repository;

import com.utn.UTNPhones.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Boolean existsByDni(Integer dni);

    Page<Client>findAll(Pageable pageable);

    Optional<Client> findByDni(Integer dni);

    @Query(value = "SELECT * FROM persons " +
            "WHERE phoneline_id = ?1",
            nativeQuery = true)
    Client findByIdPhoneline(Integer id);
}
