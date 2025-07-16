package com.bict.human_detect_api.dto.humanDetectConfigDto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.bict.human_detect_api.dto.humanDetectConfigDto.RoiDto;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPutHumanDetectConfigDto {
    private Float conf;
    private Float iou;
    private Long imgsz;
    List<RoiDto> roiList;
}
