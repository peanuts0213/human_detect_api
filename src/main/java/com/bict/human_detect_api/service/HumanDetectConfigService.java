package com.bict.human_detect_api.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bict.human_detect_api.dto.humanDetectConfigDto.request.RequestPutHumanDetectConfigDto;
import com.bict.human_detect_api.dto.humanDetectConfigDto.response.ResponseHumanDetectConfigDto;
import com.bict.human_detect_api.dto.humanDetectConfigDto.response.ResponseHumanDetectConfigListDto;
import com.bict.human_detect_api.dto.kafka.consumer.changeCctvData.CctvDto;
import com.bict.human_detect_api.dto.kafka.consumer.changeCctvData.ChangeCctvDataDto;
import com.bict.human_detect_api.entitiy.HumanDetectConfigEntity;
import com.bict.human_detect_api.entitiy.RoiEntity;
import com.bict.human_detect_api.entitiy.RoiPointsEntity;
import com.bict.human_detect_api.repository.HumanDetectConfigRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HumanDetectConfigService {

    @PersistenceContext
    private EntityManager entityManager;

    private final HumanDetectConfigRepository humanDetectConfigRepository;

    @Transactional
    public ResponseHumanDetectConfigListDto findAll() {
        return new ResponseHumanDetectConfigListDto(
                humanDetectConfigRepository.findAll());
    }

    @Transactional
    public ResponseHumanDetectConfigDto findByCctvId(
            Long cctvId) {
        return new ResponseHumanDetectConfigDto(
                humanDetectConfigRepository.findByCctvId(cctvId).get());
    }

    @Transactional
    public ResponseHumanDetectConfigDto updateHumanDetectConfigByCctvId(
            Long cctvId,
            RequestPutHumanDetectConfigDto dto) {
        HumanDetectConfigEntity entity = humanDetectConfigRepository.findByCctvId(cctvId)
                .orElseThrow(() -> new EntityNotFoundException("해당 CCTV ID를 찾을 수 없습니다: " + cctvId));

        entity.setConf(dto.getConf());
        entity.setIou(dto.getIou());
        entity.setImgsz(dto.getImgsz());

        List<RoiEntity> roiEntities = dto.getRoiList().stream()
                .map(roiDto -> {
                    RoiEntity roiEntity = new RoiEntity();
                    roiEntity.setId(roiDto.getId());
                    roiEntity.setHumanDetectConfig(entity);
                    List<RoiPointsEntity> roiPointsEntities = roiDto.getRoi().stream()
                            .map(pointDto -> {
                                RoiPointsEntity point = new RoiPointsEntity();
                                point.setId(pointDto.getId());
                                point.setX(pointDto.getX());
                                point.setY(pointDto.getY());
                                point.setOrderIndex(pointDto.getOrderIndex());
                                point.setRoi(roiEntity);
                                return point;
                            })
                            .collect(Collectors.toList());

                    roiEntity.setRoi(roiPointsEntities);
                    return roiEntity;
                })
                .collect(Collectors.toList());
        entity.getRoiList().clear();
        entity.getRoiList().addAll(roiEntities);
        humanDetectConfigRepository.save(entity);
        return new ResponseHumanDetectConfigDto(entity);
    }

    @Transactional
    public ResponseHumanDetectConfigListDto deleteByCctvId(
            Long cctvId) {
        humanDetectConfigRepository.deleteByCctvId(cctvId);
        return new ResponseHumanDetectConfigListDto(humanDetectConfigRepository.findAll());
    }

    @Transactional
    public ResponseHumanDetectConfigListDto applyCctvData(
            ChangeCctvDataDto dto) {

        List<CctvDto> cctvListDto = dto.getCctvListDto();
        List<HumanDetectConfigEntity> humanDetectConfigEntityList = humanDetectConfigRepository.findAll();
        Map<Long, CctvDto> cctvDtoMap = cctvListDto.stream()
                .filter(cctv -> cctv.getIciIdx() != null)
                .collect(Collectors.toMap(
                        CctvDto::getIciIdx,
                        Function.identity(),
                        (a, b) -> a // 중복은 첫 번째 유지
                ));
        Set<Long> cctvIciIdxSet = cctvDtoMap.keySet();
        Set<Long> apcIciIdxSet = humanDetectConfigEntityList.stream()
                .map(HumanDetectConfigEntity::getIciIdx)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (HumanDetectConfigEntity entity : humanDetectConfigEntityList) {
            Long iciIdx = entity.getIciIdx();
            if (iciIdx == null)
                continue; // 통과

            if (cctvDtoMap.containsKey(iciIdx)) {
                CctvDto cctv = cctvDtoMap.get(iciIdx);

                entity.setCctvId(cctv.getId());
                entity.setRtspUrl(cctv.getRtspUrl());

                humanDetectConfigRepository.save(entity);
            }
        }

        Set<Long> toCreateHumanDetectConfig = new HashSet<>(cctvIciIdxSet);
        toCreateHumanDetectConfig.removeAll(apcIciIdxSet);

        for (Long iciIdx : toCreateHumanDetectConfig) {
            CctvDto cctv = cctvDtoMap.get(iciIdx);
            if (cctv != null) {
                HumanDetectConfigEntity newEntity = new HumanDetectConfigEntity(
                        null,
                        iciIdx,
                        cctv.getId(),
                        cctv.getRtspUrl(),
                        true,
                        0.5f,
                        0.5f,
                        640L,
                        new ArrayList<RoiEntity>());
                humanDetectConfigRepository.save(newEntity);
            }
        }

        Set<Long> toDeleteHumanDetect = new HashSet<>(apcIciIdxSet);
        toDeleteHumanDetect.removeAll(cctvIciIdxSet);
        for (HumanDetectConfigEntity entity : humanDetectConfigEntityList) {
            Long iciIdx = entity.getIciIdx();
            if (iciIdx != null && toDeleteHumanDetect.contains(iciIdx)) {
                humanDetectConfigRepository.delete(entity);
            }
        }

        humanDetectConfigRepository.flush();
        entityManager.clear();

        return new ResponseHumanDetectConfigListDto(humanDetectConfigRepository.findAll());
    }

    @Transactional
    public ResponseHumanDetectConfigDto activateByCctvId(Long cctvId) {
        Optional<HumanDetectConfigEntity> humanDetectConfigEntityOptional = humanDetectConfigRepository
                .findByCctvId(cctvId);
        if (humanDetectConfigEntityOptional.isEmpty()) {
            return new ResponseHumanDetectConfigDto();
        }
        HumanDetectConfigEntity humanDetectConfigEntity = humanDetectConfigEntityOptional.get();
        humanDetectConfigEntity.setIsActivate(true);
        return new ResponseHumanDetectConfigDto(humanDetectConfigRepository.save(humanDetectConfigEntity));
    }

    @Transactional
    public ResponseHumanDetectConfigDto deactivateByCctvId(Long cctvId) {
        Optional<HumanDetectConfigEntity> humanDetectConfigEntityOptional = humanDetectConfigRepository
                .findByCctvId(cctvId);
        if (humanDetectConfigEntityOptional.isEmpty()) {
            return new ResponseHumanDetectConfigDto();
        }
        HumanDetectConfigEntity humanDetectConfigEntity = humanDetectConfigEntityOptional.get();
        humanDetectConfigEntity.setIsActivate(false);
        return new ResponseHumanDetectConfigDto(humanDetectConfigRepository.save(humanDetectConfigEntity));
    }

    @Transactional
    public ResponseHumanDetectConfigListDto activateAllModuel() {
        List<HumanDetectConfigEntity> humanDetectConfigEntityList = humanDetectConfigRepository.findAll();
        List<HumanDetectConfigEntity> updatedHumanDetectConfigEntityList = humanDetectConfigEntityList.stream()
                .map(humanDetectConfigEntity -> {
                    humanDetectConfigEntity.setIsActivate(true);
                    return humanDetectConfigRepository.save(humanDetectConfigEntity);
                }).collect(Collectors.toList());
        return new ResponseHumanDetectConfigListDto(updatedHumanDetectConfigEntityList);
    }

    @Transactional
    public ResponseHumanDetectConfigListDto deactivateAllModuel() {
        List<HumanDetectConfigEntity> humanDetectConfigEntityList = humanDetectConfigRepository.findAll();
        List<HumanDetectConfigEntity> updatedHumanDetectConfigEntityList = humanDetectConfigEntityList.stream()
                .map(humanDetectConfigEntity -> {
                    humanDetectConfigEntity.setIsActivate(false);
                    return humanDetectConfigRepository.save(humanDetectConfigEntity);
                }).collect(Collectors.toList());
        return new ResponseHumanDetectConfigListDto(updatedHumanDetectConfigEntityList);
    }

}
