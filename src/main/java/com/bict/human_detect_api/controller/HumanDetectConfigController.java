package com.bict.human_detect_api.controller;

import java.util.Collections;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bict.human_detect_api.dto.humanDetectConfigDto.request.RequestPutHumanDetectConfigDto;
import com.bict.human_detect_api.dto.humanDetectConfigDto.response.ResponseHumanDetectConfigDto;
import com.bict.human_detect_api.dto.humanDetectConfigDto.response.ResponseHumanDetectConfigListDto;
import com.bict.human_detect_api.dto.kafka.consumer.changeCctvData.ChangeCctvDataDto;
import com.bict.human_detect_api.service.HumanDetectConfigService;
import com.bict.human_detect_api.service.KafkaService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/human_detect_config")
public class HumanDetectConfigController {

    private final HumanDetectConfigService humanDetectConfigService;
    private final KafkaService kafkaService;

    @GetMapping("/read")
    public ResponseEntity<ResponseHumanDetectConfigListDto> getAllHumanDetectConfig() {
        return ResponseEntity.ok(humanDetectConfigService.findAll());
    }

    @GetMapping("/read/")
    public ResponseEntity<ResponseHumanDetectConfigDto> getHumanDetectConfigByCctvId(
            @RequestParam(value = "cctvId") Long cctvId) {
        return ResponseEntity.ok(humanDetectConfigService.findByCctvId(cctvId));
    }

    @PostMapping("/update/")
    public ResponseEntity<ResponseHumanDetectConfigDto> putHumanDetectConfigByCctvId(
            @RequestParam(value = "cctvId") Long cctvId,
            @RequestBody RequestPutHumanDetectConfigDto dto) {
        ResponseHumanDetectConfigDto responseHumanDetectConfigDto = humanDetectConfigService
                .updateHumanDetectConfigByCctvId(cctvId, dto);
        kafkaService.produceHumanDetectData(Collections.singletonList(responseHumanDetectConfigDto));
        return ResponseEntity.ok(responseHumanDetectConfigDto);
    }

    @GetMapping("/delete/")
    public ResponseEntity<ResponseHumanDetectConfigListDto> deleteHumanDetectConfigByCctvId(
            @RequestParam(value = "cctvId") Long cctvId) {
        ResponseHumanDetectConfigListDto responseHumanDetectConfigListDto = humanDetectConfigService
                .deleteByCctvId(cctvId);
        kafkaService.produceHumanDetectData(responseHumanDetectConfigListDto.getResponseHumanDetectConfigList());
        return ResponseEntity.ok(responseHumanDetectConfigListDto);
    }

    @PostMapping("/apply_cctv_data")
    public ResponseEntity<ResponseHumanDetectConfigListDto> postMethodName(
            @RequestBody ChangeCctvDataDto dto) {
        ResponseHumanDetectConfigListDto responseHumanDetectConfigListDto = humanDetectConfigService
                .applyCctvData(dto);
        kafkaService.produceHumanDetectData(responseHumanDetectConfigListDto.getResponseHumanDetectConfigList());
        return ResponseEntity.ok(responseHumanDetectConfigListDto);
    }

    @GetMapping("/activate/by_cctv_id/")
    public ResponseEntity<ResponseHumanDetectConfigDto> activateByCctvId(
            @RequestParam(value = "cctvId") Long cctvId) {
        ResponseHumanDetectConfigDto responseHumanDetectConfigDto = humanDetectConfigService
                .activateByCctvId(cctvId);
        kafkaService.produceHumanDetectData(Collections.singletonList(responseHumanDetectConfigDto));
        return ResponseEntity.ok(responseHumanDetectConfigDto);
    }

    @GetMapping("/deactivate/by_cctv_id/")
    public ResponseEntity<ResponseHumanDetectConfigDto> deactivateByCctvId(
            @RequestParam(value = "cctvId") Long cctvId) {
        ResponseHumanDetectConfigDto responseHumanDetectConfigDto = humanDetectConfigService
                .deactivateByCctvId(cctvId);
        kafkaService.produceHumanDetectData(Collections.singletonList(responseHumanDetectConfigDto));
        return ResponseEntity.ok(responseHumanDetectConfigDto);
    }

    @GetMapping("/activate")
    public ResponseEntity<ResponseHumanDetectConfigListDto> activateAllModuel() {
        ResponseHumanDetectConfigListDto responseHumanDetectConfigListDto = humanDetectConfigService
                .activateAllModuel();
        kafkaService.produceHumanDetectData(responseHumanDetectConfigListDto.getResponseHumanDetectConfigList());
        return ResponseEntity.ok(responseHumanDetectConfigListDto);
    }

    @GetMapping("/deactivate")
    public ResponseEntity<ResponseHumanDetectConfigListDto> deactivateAllModuel() {
        ResponseHumanDetectConfigListDto responseHumanDetectConfigListDto = humanDetectConfigService
                .deactivateAllModuel();
        kafkaService.produceHumanDetectData(responseHumanDetectConfigListDto.getResponseHumanDetectConfigList());
        return ResponseEntity.ok(responseHumanDetectConfigListDto);
    }

}
