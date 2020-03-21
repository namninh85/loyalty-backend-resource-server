package com.nin.xloyalty.repository;

import com.nin.xloyalty.model.InterestedField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestedFieldRepository extends JpaRepository<InterestedField,Long> {
    @Query(value = "SELECT i.interested_field_id,i.name FROM interested_field i WHERE i.is_active ='true' AND i.interested_field_id IN (:interestedFieldId)" , nativeQuery = true)
    List<Object[]> findByListId(@Param("interestedFieldId") List<Integer> listId);
}
