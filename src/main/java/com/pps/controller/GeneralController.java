package com.pps.controller;

import com.pps.controller.domain.picture.PictureEntity;
import com.pps.controller.domain.picture.PictureService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GeneralController {
    private final PictureService pictureService;
    private final String currentDirectory = System.getProperty("user.dir");

    @GetMapping("/")
    public ModelAndView home(Model model) {
        List<PictureEntity> pictures = pictureService.getPicturesByStatus("ACCEPTED");
        model.addAttribute("pictures", pictures);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/load/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "image/jpeg")),
            @ApiResponse(responseCode = "404", description = "Picture not found",
                    content = @Content) })
    public ResponseEntity<byte[]> getPicture(@PathVariable("id") String id) throws IOException {
        PictureEntity picture = pictureService.getPictureById(Long.parseLong(id));
        if (picture != null) {

            byte[] bytes = Files.readAllBytes(Paths.get(currentDirectory + File.separator + picture.getPath()));
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }
        return ResponseEntity.notFound().build();
    }
}
