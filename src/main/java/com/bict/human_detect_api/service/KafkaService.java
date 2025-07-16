package com.bict.human_detect_api.service;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.bict.human_detect_api.constants.KafkaTopic;
import com.bict.human_detect_api.dto.humanDetectConfigDto.response.ResponseHumanDetectConfigDto;
import com.bict.human_detect_api.dto.humanDetectConfigDto.response.ResponseHumanDetectConfigListDto;
import com.bict.human_detect_api.dto.kafka.consumer.changeCctvData.ChangeCctvDataDto;
import com.bict.human_detect_api.dto.kafka.producer.ChangeHumanDetectDataDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

  private final HumanDetectConfigService humanDetectConfigService;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void send(String topic, Object message) {
    kafkaTemplate.send(topic, message)
        .thenAccept(result -> log.info("Kafka 전송 성공 [topic={}, offset={}]", topic, result.getRecordMetadata().offset()))
        .exceptionally(ex -> {
          log.warn("Kafka 전송 실패 [topic={}, error={}]", topic, ex.getMessage());
          return null;
        });
  }

  public ResponseHumanDetectConfigListDto handleChangeCctvData(ChangeCctvDataDto dto) {
    return humanDetectConfigService.applyCctvData(dto);
  }

  public void produceHumanDetectData(List<ResponseHumanDetectConfigDto> dtoList) {
    this.send(KafkaTopic.CHANGE_HUMAN_DETECT_DATA, new ChangeHumanDetectDataDto(dtoList));
  }

}
