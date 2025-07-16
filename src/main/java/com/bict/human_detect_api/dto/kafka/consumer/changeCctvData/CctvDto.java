package com.bict.human_detect_api.dto.kafka.consumer.changeCctvData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CctvDto {
    private Long id;
    private Long iciIdx;
    private String name;
    private String onvifUrl;
    private String userName;
    private String password;
    private String rtspUrl;
    private Long areaId;
}
