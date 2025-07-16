package com.bict.human_detect_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bict.human_detect_api.entitiy.HumanDetectConfigEntity;

import java.util.List;
import java.util.Optional;

public interface HumanDetectConfigRepository extends JpaRepository<HumanDetectConfigEntity, Long> {
    public Optional<HumanDetectConfigEntity> findByCctvId(Long cctvId);

    public void deleteByCctvId(Long cctvId);

    public void deleteAllByIciIdxIn(List<Long> IciIdxList);

}
