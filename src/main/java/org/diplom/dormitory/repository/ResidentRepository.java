package org.diplom.dormitory.repository;

import org.diplom.dormitory.DTO.ResidentTelegramDTO;
import org.diplom.dormitory.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, Integer> {

    List<ResidentTelegramDTO> findAllByPhoneNumber(String phoneNumber);
}
