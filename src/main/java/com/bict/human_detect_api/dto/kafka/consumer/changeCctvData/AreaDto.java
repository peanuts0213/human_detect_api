package com.bict.human_detect_api.dto.kafka.consumer.changeCctvData;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AreaDto {
  private Long id;
  private Long iaiIdx;
  private String name;
  private List<Long> cctvIdList;
}