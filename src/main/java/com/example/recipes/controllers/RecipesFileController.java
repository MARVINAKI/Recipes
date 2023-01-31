package com.example.recipes.controllers;

import com.example.recipes.services.FilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files/recipes")
public class RecipesFileController {
    private final FilesService filesService;

    public RecipesFileController(@Qualifier("recipesFileServiceImpl") FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File fileToDownload = filesService.getDataFile();
        if (fileToDownload.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(fileToDownload));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(fileToDownload.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile fileToUpload) {
        filesService.cleanDataFile();
        File file = filesService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            IOUtils.copy(fileToUpload.getInputStream(), fos);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
