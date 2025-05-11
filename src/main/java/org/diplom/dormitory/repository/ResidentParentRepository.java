package org.diplom.dormitory.repository;

import org.diplom.dormitory.model.ResidentParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentParentRepository extends JpaRepository<ResidentParent, Integer> {
}
