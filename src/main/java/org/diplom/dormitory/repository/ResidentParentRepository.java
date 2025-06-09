package org.diplom.dormitory.repository;

import org.diplom.dormitory.DTO.ResidentParentDTO;
import org.diplom.dormitory.model.Parent;
import org.diplom.dormitory.model.ResidentParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentParentRepository extends JpaRepository<ResidentParent, Integer> {
    @Query("SELECT rp.parent FROM ResidentParent rp WHERE rp.resident.id = :residentId")
    List<Parent> findParentsByResidentId(@Param("residentId") Integer residentId);
}
