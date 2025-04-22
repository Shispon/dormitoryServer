package org.diplom.dormitory.repository;

import org.diplom.dormitory.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

    // Метод для поиска сотрудника по email
    Optional<Staff> findByMail(String mail);
}