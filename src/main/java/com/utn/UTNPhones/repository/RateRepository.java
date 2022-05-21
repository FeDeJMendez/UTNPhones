package com.utn.UTNPhones.repository;

import com.utn.UTNPhones.domain.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {

    @Query(value = "SELECT * FROM rates WHERE origin_city_id = ?1 AND destination_city_id = ?2",nativeQuery = true)
    List<Rate> findByOriginAndDestination(Integer idCityOrigin, Integer idCityDestination);

    Page<Rate>findAll(Pageable pageable);
}
