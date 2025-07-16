package com.bict.human_detect_api.dto.humanDetectConfigDto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.bict.human_detect_api.entitiy.HumanDetectConfigEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseHumanDetectConfigListDto {
    private List<ResponseHumanDetectConfigDto> responseHumanDetectConfigList;

    public ResponseHumanDetectConfigListDto(List<HumanDetectConfigEntity> HumanDetectConfigEntityList) {
        this.responseHumanDetectConfigList = HumanDetectConfigEntityList.stream().map(ResponseHumanDetectConfigDto::new)
                .collect(Collectors.toList());
    };
}
