package com.utn.UTNPhones.repository;

import com.utn.UTNPhones.domain.Phoneline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhonelineRepository extends JpaRepository<Phoneline, Integer> {
    Boolean existsByNumber(String number);

    Optional<Phoneline> findByNumber(String number);
}
