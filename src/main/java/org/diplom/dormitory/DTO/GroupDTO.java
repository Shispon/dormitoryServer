package org.diplom.dormitory.DTO;





public class GroupDTO {
    private Integer id;
    private String groupName;
    private Integer curatorId;

    public GroupDTO(Integer id, String groupName, Integer curatorId) {
        this.id = id;
        this.groupName = groupName;
        this.curatorId = curatorId;
    }

    public GroupDTO() {

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

    public Integer getCuratorId() {
        return curatorId;
    }

    public void setCuratorId(Integer curatorId) {
        this.curatorId = curatorId;
    }
}
