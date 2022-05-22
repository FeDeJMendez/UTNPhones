package com.utn.UTNPhones.repository;

import com.utn.UTNPhones.domain.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {
    Page<Call> findAll(Pageable pageable);
}
