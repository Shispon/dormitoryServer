package org.diplom.dormitory.service;

import org.diplom.dormitory.DTO.ResidentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final KafkaTemplate<String, ResidentDTO> kafkaTemplate;

    @Autowired
    public KafkaService(KafkaTemplate<String, ResidentDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendResidentStatus(ResidentDTO residentDTO) {
        kafkaTemplate.send("is-present", residentDTO);
    }
}
