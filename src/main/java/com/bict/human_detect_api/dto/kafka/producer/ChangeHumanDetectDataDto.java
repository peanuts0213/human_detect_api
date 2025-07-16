package com.bict.human_detect_api.dto.kafka.producer;

import java.util.List;

import com.bict.human_detect_api.dto.humanDetectConfigDto.response.ResponseHumanDetectConfigDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeHumanDetectDataDto {
  List<ResponseHumanDetectConfigDto> humanDetectConfigListDto;
}
