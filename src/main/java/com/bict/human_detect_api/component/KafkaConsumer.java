package com.bict.human_detect_api.component;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.bict.human_detect_api.constants.KafkaTopic;
import com.bict.human_detect_api.dto.kafka.consumer.changeCctvData.ChangeCctvDataDto;
import com.bict.human_detect_api.service.HumanDetectConfigService;
import com.bict.human_detect_api.service.KafkaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

  private final KafkaService kafkaService;
  private final HumanDetectConfigService humanDetectConfigService;

  @KafkaListener(topics = KafkaTopic.CHANGE_CCTV_DATA, groupId = "human_detect_api_${random.value}", properties = {
      "spring.json.value.default.type=com.bict.human_detect_api.dto.kafka.consumer.changeCctvData.ChangeCctvDataDto",
      "spring.json.trusted.packages=*"
  })
  public void changeCctvDataListen(ChangeCctvDataDto dto) {
    kafkaService.produceHumanDetectData(kafkaService.handleChangeCctvData(dto).getResponseHumanDetectConfigList());
  }

  @KafkaListener(topics = KafkaTopic.HUMAN_DETECT_MODULE_IS_ON, groupId = "human_detect_api_${random.value}", properties = {
      "spring.json.value.default.type=java.lang.Void",
      "spring.json.trusted.packages=*"
  })
  public void humanDetectModelIsOn() {
    kafkaService.produceHumanDetectData(humanDetectConfigService.findAll().getResponseHumanDetectConfigList());
  }

}
