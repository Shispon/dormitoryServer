package org.diplom.dormitory.controller;

import org.diplom.dormitory.DTO.GroupDTO;
import org.diplom.dormitory.model.Group;
import org.diplom.dormitory.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return  ResponseEntity.ok(groupService.getAllGroups());
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody GroupDTO groupDTO) {
        try {
            Group createdGroup = groupService.createGroup(groupDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
        } catch (ResponseStatusException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
