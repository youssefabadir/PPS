package com.pps.controller.domain.picture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

    List<PictureEntity> findByStatus(String status);
}
