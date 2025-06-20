package org.diplom.dormitory.repository;

import org.diplom.dormitory.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
    @Query("SELECT rp.parent FROM ResidentParent rp WHERE rp.resident.id = :residentId")
    List<Parent> findParentsByResidentId(@Param("residentId") Integer residentId);

    @Query("SELECT p FROM Parent p WHERE p.phoneNumber = :phoneNumber")
    Optional<Parent> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);


}
