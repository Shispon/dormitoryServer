package org.diplom.dormitory.repository;

import org.diplom.dormitory.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {

   Optional<Group> findById(Integer id);
   @Modifying
   @Transactional
   @Query("UPDATE Group g SET g.curator_id.id = :curatorId WHERE g.id = :groupId")
   void setCurator(@Param("groupId") Integer groupId, @Param("curatorId") Integer curatorId);
}
