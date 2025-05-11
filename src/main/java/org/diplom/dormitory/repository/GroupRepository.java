package org.diplom.dormitory.repository;

import org.diplom.dormitory.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {

   Optional<Group> findById(Integer id);
}
