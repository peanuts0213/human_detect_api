package com.bict.human_detect_api.dto.humanDetectConfigDto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestHumanDetectConfigDto {
    private Long cctvId;
    private Float conf;
    private Float iou;
    private Long imgsz;
}
