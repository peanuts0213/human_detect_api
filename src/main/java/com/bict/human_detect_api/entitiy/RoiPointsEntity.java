package com.bict.human_detect_api.entitiy;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "roi_points")
public class RoiPointsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Float x;

    @Column(nullable = false)
    private Float y;

    @Column(name = "order_index", nullable = false)
    private Long orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roi", nullable = false)
    private RoiEntity roi;

}
