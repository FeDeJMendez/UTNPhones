package com.utn.UTNPhones.repository;

import com.utn.UTNPhones.domain.Backoffice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackofficeRepository extends JpaRepository<Backoffice, Integer> {
    Boolean existsByDni(Integer dni);

    Page<Backoffice> findAll(Pageable pageable);
}
