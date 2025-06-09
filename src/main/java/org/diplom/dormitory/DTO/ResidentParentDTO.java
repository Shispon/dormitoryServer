package org.diplom.dormitory.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentParentDTO {
    private Integer parentId;
    private Integer residentId;
}
