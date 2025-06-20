package org.diplom.dormitory.service;

import org.diplom.dormitory.DTO.ParentTelegramDTO;
import org.diplom.dormitory.DTO.ResidentDTO;
import org.diplom.dormitory.DTO.ResidentTelegramDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final KafkaTemplate<String, ResidentDTO> kafkaTemplate;
    private final KafkaTemplate<String, ResidentTelegramDTO> kafkaTelegramTemplate;
    private final KafkaTemplate<String, ParentTelegramDTO> kafkaParentTelegramTemplate;

    @Autowired
    public KafkaService(KafkaTemplate<String, ResidentDTO> kafkaTemplate, KafkaTemplate<String, ResidentTelegramDTO> kafkaTelegramTemplate, KafkaTemplate<String, ParentTelegramDTO> kafkaParentTelegramTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTelegramTemplate = kafkaTelegramTemplate;
        this.kafkaParentTelegramTemplate = kafkaParentTelegramTemplate;
    }

    public void sendResidentStatus(ResidentDTO residentDTO) {
        kafkaTemplate.send("is-present", residentDTO);
    }

    public void sendResidentTelegram(ResidentTelegramDTO dto) {
        kafkaTelegramTemplate.send("notify-residents",dto);
    }

    public void sendParentTelegram(ParentTelegramDTO dto) {
        kafkaParentTelegramTemplate.send("notify-parents",dto);
    }
}
