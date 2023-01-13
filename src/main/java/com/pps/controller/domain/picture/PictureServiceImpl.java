package com.pps.controller.domain.picture;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PictureServiceImpl implements PictureService {

    @Value("${pps.upload.dir}")
    private String directory;

    private final String currentDirectory = System.getProperty("user.dir");
    private final PictureRepository pictureRepository;

    @Override
    public void savePicture(PictureEntity picture) {
        pictureRepository.save(picture);
    }

    @Override
    public PictureEntity getPictureById(Long id) {
        return pictureRepository.findById(id).orElse(null);
    }

    @Override
    public List<PictureEntity> getPicturesByStatus(String status) {
        return pictureRepository.findByStatus(status);
    }


    public String setPictureLocation(String fileName, String extension) {
        return currentDirectory +
                File.separator +
                directory +
                File.separator +
                fileName +
                extension;
    }

    public void savePictureFile(String filePath, MultipartFile multipartFile) throws IOException {
        File file = new File(filePath);

        if (!file.exists() && file.mkdirs()) {
            multipartFile.transferTo(file);
        } else {
            throw new IOException("File already exists with the name: " + filePath);
        }
    }

    @Override
    public void updatePictureStatus(Long id, String status) {
        PictureEntity picture = getPictureById(id);
        picture.setStatus(status);
        pictureRepository.save(picture);
    }
}
