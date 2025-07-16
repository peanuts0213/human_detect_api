package com.bict.human_detect_api.dto.kafka.consumer.changeCctvData;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCctvDataDto {
	private List<CctvDto> cctvListDto;
	private List<AreaDto> areaListDto;
}
