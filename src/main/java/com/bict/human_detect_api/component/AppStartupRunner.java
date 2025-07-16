package com.bict.human_detect_api.component;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bict.human_detect_api.service.HumanDetectConfigService;
import com.bict.human_detect_api.service.KafkaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {
  private final KafkaService kafkaService;
  private final HumanDetectConfigService humanDetectConfigService;

  @Override
  public void run(ApplicationArguments args) {
    kafkaService.produceHumanDetectData(humanDetectConfigService.findAll().getResponseHumanDetectConfigList());
    ;
  }
}