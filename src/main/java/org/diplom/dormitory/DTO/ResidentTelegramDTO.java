package org.diplom.dormitory.DTO;

import lombok.NoArgsConstructor;


public interface ResidentTelegramDTO {
    Integer getId();
    String getFirstName();
    String getSecondName();
    String getLastName();
    String getPhoneNumber();
    String getMail();
    String getTelegramId();
    String getChatId();
}
