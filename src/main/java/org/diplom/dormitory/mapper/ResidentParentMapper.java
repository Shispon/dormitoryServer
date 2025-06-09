package org.diplom.dormitory.mapper;

import org.diplom.dormitory.DTO.ResidentParentDTO;
import org.diplom.dormitory.model.Parent;
import org.diplom.dormitory.model.Resident;
import org.diplom.dormitory.model.ResidentParent;

public class ResidentParentMapper {

    public static ResidentParentDTO residentParentToResidentParentDTO(ResidentParent residentParent) {
        if (residentParent == null) {
            return null;
        }
        ResidentParentDTO residentParentDTO = new ResidentParentDTO();
        residentParentDTO.setParentId(residentParent.getParent().getId());
        residentParentDTO.setResidentId(residentParent.getResident().getId());
        return residentParentDTO;
    }

    public static ResidentParent residentParentDTOToResidentParent(ResidentParentDTO residentParentDTO) {
        if (residentParentDTO == null) {
            return null;
        }
        ResidentParent residentParent = new ResidentParent();
        if (residentParentDTO.getParentId() != null) {
            Parent parent = new Parent();
            parent.setId(residentParentDTO.getParentId());
            residentParent.setParent(parent);
        }
        if (residentParentDTO.getResidentId() != null) {
            Resident resident = new Resident();
            resident.setId(residentParentDTO.getResidentId());
            residentParent.setResident(resident);
        }
        return residentParent;
    }
}
