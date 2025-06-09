package org.diplom.dormitory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "groups", schema = "diplom")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curator_id")
    @ToString.Exclude
    private Staff curator_id;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Resident> residents;


    public Group() {
    }

    public Group(Integer id, String groupName, Staff curator_id, List<Resident> residents) {
        this.id = id;
        this.groupName = groupName;
        this.curator_id = curator_id;
        this.residents = residents;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Staff getCurator_id() {
        return curator_id;
    }

    public void setCurator_id(Staff curator_id) {
        this.curator_id = curator_id;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

}
