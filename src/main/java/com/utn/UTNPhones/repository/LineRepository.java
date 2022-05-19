package com.utn.UTNPhones.repository;

import com.utn.UTNPhones.domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LineRepository extends JpaRepository<Line, Integer> {
    Boolean existsByNumber(String number);

    Optional<Line> findByNumber(String number);
}
