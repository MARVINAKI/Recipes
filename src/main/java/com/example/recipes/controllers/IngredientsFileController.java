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
@RequestMapping("/files/ingredients")
public class IngredientsFileController {
    private final FilesService filesService;

    public IngredientsFileController(@Qualifier("ingredientsFileServiceImpl") FilesService filesService) {
        this.filesService = filesService;
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File file = filesService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"ingredients.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        filesService.cleanDataFile();
        File dataFile = filesService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        try (BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
//             FileOutputStream fos = new FileOutputStream(dataFile);
//             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
//            byte[] buffer = new byte[1024];
//            while (bis.read(buffer) > 0) {
//                bos.write(buffer);
//            }
//            file.getInputStream();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
