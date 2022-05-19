package com.utn.UTNPhones.repository;

import com.utn.UTNPhones.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Boolean existsByPrefix(Integer prefix);

    Page<City>findAll(Pageable pageable);

}
