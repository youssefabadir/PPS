package com.pps.controller.domain.picture;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PictureService {

    void savePicture(PictureEntity pictureEntity);

    PictureEntity getPictureById(Long id);

    List<PictureEntity> getPicturesByStatus(String status);

    String setPictureLocation(String fileName, String extension);

    void savePictureFile(String filePath, MultipartFile multipartFile) throws IOException;

    void updatePictureStatus(Long id, String status);
}
