package com.bict.human_detect_api.dto.humanDetectConfigDto;

import java.util.List;
import java.util.stream.Collectors;

import com.bict.human_detect_api.entitiy.RoiEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoiDto {
  private Long id;
  private List<RoiPointsDto> roi;

  public RoiDto(RoiEntity roiEntity) {
    this.id = roiEntity.getId();
    this.roi = roiEntity.getRoi().stream().map(RoiPointsDto::new).collect(Collectors.toList());
  }
}
