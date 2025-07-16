package com.bict.human_detect_api.entitiy;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roi")
public class RoiEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "roi", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RoiPointsEntity> roi;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "human_detect_config", nullable = false)
  private HumanDetectConfigEntity humanDetectConfig;
}
