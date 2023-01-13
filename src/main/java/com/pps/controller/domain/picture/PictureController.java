package com.pps.controller.domain.picture;

import com.pps.controller.domain.category.CategoryService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/picture")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PictureController {

    private final PictureService pictureService;
    private final CategoryService categoryService;

    @GetMapping("/upload")
    public ModelAndView uploadPicturePage(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploadPicture");
        return modelAndView;
    }

    @PostMapping("/upload")
    public ModelAndView uploadPicture(@ModelAttribute PictureDto picture) throws IOException {
        String originalName = picture.getImage().getOriginalFilename();
        long size = picture.getImage().getSize() / 1024;
        ModelAndView modelAndView = new ModelAndView();
        if (size > 2048) {
            modelAndView.setViewName("redirect:/picture/upload?error");
            return modelAndView;
        }
        String[] list = originalName.split("\\.");
        String extension = "." + list[list.length - 1];
        String uuid = UUID.randomUUID().toString();
        String filePath = pictureService.setPictureLocation(uuid, extension);
        pictureService.savePictureFile(filePath, picture.getImage());

        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setName(originalName);
        pictureEntity.setPath("uploaded" + File.separator + uuid + extension);
        pictureEntity.setDescription(picture.getDescription());
        pictureEntity.setStatus("PENDING");
        pictureEntity.setCategoryEntity(categoryService.getCategoryByName(picture.getCategory()));
        pictureService.savePicture(pictureEntity);

        modelAndView.setViewName("redirect:/picture/upload?success");
        return modelAndView;
    }

    @PostMapping("/reviewPictures/accept/{id}")
    public ModelAndView acceptPicture(@PathVariable("id") Long id) {
        pictureService.updatePictureStatus(id, "ACCEPTED");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/picture/reviewPictures");
        return modelAndView;
    }

    @PostMapping("/reviewPictures/reject/{id}")
    public ModelAndView rejectPicture(@PathVariable("id") Long id) {
        pictureService.updatePictureStatus(id, "REJECTED");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/picture/reviewPictures");
        return modelAndView;
    }

    @GetMapping("/reviewPictures")
    public ModelAndView reviewPicturesPage(Model model) {
        List<PictureEntity> pictures = pictureService.getPicturesByStatus("PENDING");
        model.addAttribute("pictures", pictures);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("reviewPictures");
        return modelAndView;
    }

    @PostMapping("/uploadPicture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Picture has been uploaded",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Picture size is larger than 2MB",
                    content = @Content) })
    public ResponseEntity<String> pictureUpload(@ModelAttribute PictureDto picture) throws IOException {
        String originalName = picture.getImage().getOriginalFilename();
        long size = picture.getImage().getSize() / 1024;
        if (size > 2048) {
            return ResponseEntity.status(400).body("Picture size is larger than 2MB");
        }
        String[] list = originalName.split("\\.");
        String extension = "." + list[list.length - 1];
        String uuid = UUID.randomUUID().toString();
        String filePath = pictureService.setPictureLocation(uuid, extension);
        pictureService.savePictureFile(filePath, picture.getImage());

        PictureEntity pictureEntity = new PictureEntity();
        pictureEntity.setName(originalName);
        pictureEntity.setPath("uploaded" + File.separator + uuid + extension);
        pictureEntity.setDescription(picture.getDescription());
        pictureEntity.setStatus("PENDING");
        pictureEntity.setCategoryEntity(categoryService.getCategoryByName(picture.getCategory()));
        pictureService.savePicture(pictureEntity);

        return ResponseEntity.ok("Image has been uploaded");
    }

    @PostMapping("/review/accept/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Picture has been accepted",
                    content = @Content)})
    public ResponseEntity<String> pictureAccept(@PathVariable("id") Long id) {
        pictureService.updatePictureStatus(id, "ACCEPTED");
        return ResponseEntity.ok("Picture has been accepted");
    }

    @PostMapping("/review/reject/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Picture has been rejected",
                    content = @Content)})
    public ResponseEntity<String> pictureReject(@PathVariable("id") Long id) {
        pictureService.updatePictureStatus(id, "ACCEPTED");
        return ResponseEntity.ok("Picture has been rejected");
    }

    @GetMapping("/review/pending")
    public ResponseEntity<List<PictureEntity>> reviewPictures() {
        List<PictureEntity> pictures = pictureService.getPicturesByStatus("PENDING");
        return ResponseEntity.ok(pictures);
    }
}
