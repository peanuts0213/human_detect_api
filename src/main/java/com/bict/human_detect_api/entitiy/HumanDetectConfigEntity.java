package com.bict.human_detect_api.entitiy;

import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "human_detect_config")
public class HumanDetectConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ici_idx", nullable = true)
    private Long iciIdx;

    @Column(unique = true, name = "cctv_id", nullable = false)
    private Long cctvId;

    @Column(name = "rtsp_url", nullable = false)
    private String rtspUrl;

    @Column(name = "is_activate", nullable = false)
    @ColumnDefault("true")
    private Boolean isActivate;

    @Column(nullable = false)
    @ColumnDefault("0.5")
    private Float conf;

    @Column(nullable = false)
    @ColumnDefault("0.5")
    private Float iou;

    @Column(nullable = false)
    @ColumnDefault("1280")
    private Long imgsz;

    @OneToMany(mappedBy = "humanDetectConfig", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoiEntity> roiList;

}
