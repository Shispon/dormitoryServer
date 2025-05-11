package org.diplom.dormitory.service;

import org.diplom.dormitory.DTO.GroupDTO;
import org.diplom.dormitory.model.Group;
import org.diplom.dormitory.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService  {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupRepository.findAll();

        List<GroupDTO> groupDTOs = groups.stream()
                .map(group -> {
                    GroupDTO dto = new GroupDTO();
                    dto.setId(group.getId());
                    dto.setGroupName(group.getGroupName());
                    dto.setCuratorId(group.getCurator_id() != null ? group.getCurator_id().getId() : null);
                    return dto;
                })
                .collect(Collectors.toList());

        return groupDTOs;
    }

    public Group createGroup(GroupDTO groupDTO) {
        Group group = new Group();
        group.setGroupName(groupDTO.getGroupName());
        return groupRepository.save(group);
    }

}
