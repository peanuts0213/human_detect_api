package com.bict.human_detect_api.dto.humanDetectConfigDto;

import com.bict.human_detect_api.entitiy.RoiPointsEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoiPointsDto {
    private Long id;
    private Float x;
    private Float y;
    private Long orderIndex;

    public RoiPointsDto(RoiPointsEntity roiPointsEntity) {
        this.id = roiPointsEntity.getId();
        this.x = roiPointsEntity.getX();
        this.y = roiPointsEntity.getY();
        this.orderIndex = roiPointsEntity.getOrderIndex();
    }
}
