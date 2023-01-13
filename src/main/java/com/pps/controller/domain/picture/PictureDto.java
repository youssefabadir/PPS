package com.pps.controller.domain.picture;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PictureDto {

    private MultipartFile image;
    private String category;
    private String description;
}
