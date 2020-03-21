package com.nin.xloyalty.repository;

import com.nin.xloyalty.model.Utility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilityRepository extends JpaRepository<Utility,Long> {

    @Query(value="SELECT u.image as image FROM utility u WHERE u.utility_id IN (:utilityIds) AND u.is_active = true",  nativeQuery = true)
    List<String> findByListId(@Param("utilityIds") List<Integer> utilityIds);
}
