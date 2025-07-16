package com.bict.human_detect_api.dto.humanDetectConfigDto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.bict.human_detect_api.dto.humanDetectConfigDto.RoiDto;
import com.bict.human_detect_api.entitiy.HumanDetectConfigEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseHumanDetectConfigDto {
    private Long id;
    private String rtspUrl;
    private Boolean isActivate;
    private Long cctvId;
    private Float conf;
    private Float iou;
    private Long imgsz;
    private List<RoiDto> roiList;

    public ResponseHumanDetectConfigDto(HumanDetectConfigEntity humanDetectConfigEntity) {
        this.id = humanDetectConfigEntity.getId();
        this.rtspUrl = humanDetectConfigEntity.getRtspUrl();
        this.isActivate = humanDetectConfigEntity.getIsActivate();
        this.cctvId = humanDetectConfigEntity.getCctvId();
        this.conf = humanDetectConfigEntity.getConf();
        this.iou = humanDetectConfigEntity.getIou();
        this.imgsz = humanDetectConfigEntity.getImgsz();
        this.roiList = humanDetectConfigEntity.getRoiList().stream().map(RoiDto::new).collect(Collectors.toList());
    }
}
