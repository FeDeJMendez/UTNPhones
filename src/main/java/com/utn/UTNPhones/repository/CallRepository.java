package com.utn.UTNPhones.repository;

import com.utn.UTNPhones.domain.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {
    Page<Call> findAll(Pageable pageable);

    /*@Query(value = "SELECT * FROM v_Calls c " +
            "INNER JOIN persons p " +
            "ON p.phoneline_id = c.origin_phoneline_id " +
            "WHERE p.id = :idClient " +
            "AND c.starttime BETWEEN :startDate AND :endDate",
            nativeQuery = true)*/
    @Query(value = "CALL p_CallsByClientBetweenDates (:idClient, :startDate, :endDate)",
            nativeQuery = true)
    List<Call> findByClientBetweenDates(@Param("idClient")  Integer idClient,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT * FROM calls c " +
            "INNER JOIN phonelines pl " +
            "ON c.origin_phoneline_id = pl.id  " +
            "WHERE pl.id = ?1 " +
            "AND c.idBill = 0",
            nativeQuery = true)
    List<Call> findUnbilledByPhonelineId(Integer id);
}
