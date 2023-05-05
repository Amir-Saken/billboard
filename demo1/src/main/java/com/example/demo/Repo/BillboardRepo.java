package com.example.demo.Repo;

import com.example.demo.models.Billboard;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface BillboardRepo extends CrudRepository<Billboard, Long> {
    @Query("SELECT COUNT(b) FROM Billboard b WHERE b.status = :status AND b.inWork = true AND b.address = :address")
    int countByStatusAndInWorkIsTrue(@Param("status") String status,@Param("address") String address);
    List<Billboard> findByStatus(String status);

//    List<String> findDistinctAddressByStatusAndInWorkIsTrue(String status);
//
//    long countByAddressAndStatusAndInWorkIsTrue(String address, String status, boolean inWork);
//
//    @Modifying
//    @Transactional
//    @Query("update Billboard b set b.inWork = :inWork where b.address = :address and b.status = :status")
//    void updateInWorkByAddressAndStatus(@Param("address") String address, @Param("status") String status, @Param("inWork") boolean inWork);

}
