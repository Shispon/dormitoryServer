package org.diplom.dormitory.repository;

import org.diplom.dormitory.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, Integer> {

}
