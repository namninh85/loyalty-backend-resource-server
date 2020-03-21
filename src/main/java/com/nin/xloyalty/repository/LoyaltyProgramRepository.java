package com.nin.xloyalty.repository;

import com.nin.xloyalty.model.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram,Long> {
    @Query("SELECT  p FROM LoyaltyProgram p WHERE p.isActive=true " +
            "AND p.startDate <= :date AND p.endDate >= :date ORDER BY p.startDate DESC ")
    List<LoyaltyProgram> findLoyaltyProgramByDate(@Param("date") Date date);
    
   
    LoyaltyProgram findByLoyaltyProgramId(Long Id);
    @Query("SELECT  p FROM LoyaltyProgram p WHERE p.isActive=true AND p.loyaltyProgramId=:id " +
            "AND p.startDate <= :date AND p.endDate >= :date")
    LoyaltyProgram findLoyaltyProgramByIdAndDate(@Param("date") Date date , @Param("id") Long Id);
}
