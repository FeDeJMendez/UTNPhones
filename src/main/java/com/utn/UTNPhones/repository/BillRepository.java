package com.utn.UTNPhones.repository;

import com.utn.UTNPhones.domain.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    Page<Bill> findAll (Pageable pageable);

    /*@Query(value = "SELECT * FROM bills b " +
            "INNER JOIN persons p " +
            "ON p.dni = b.dni " +
            "WHERE p.id = :idClient " +
            "AND b.datecreation BETWEEN :startDate AND :endDate",
            nativeQuery = true)*/
    @Query(value = "CALL p_BillsByClientBetweenDates (:idClient, :startDate, :endDate)",
            nativeQuery = true)
    List<Bill> findByClientBetweenDates(@Param("idClient")  Integer idClient,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);


    List<Bill> findByDni(Integer dni);

    @Query(value = "SELECT * FROM bills " +
            "WHERE dni = ?1 " +
            "AND paid = FALSE",
            nativeQuery = true)
    List<Bill> findUnpaidByDni(Integer dni);
}
