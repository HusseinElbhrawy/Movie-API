package com.husseinelbhrawy.MovieAPI.Controller;

import com.husseinelbhrawy.MovieAPI.Payload.UploadFileResponse;
import com.husseinelbhrawy.MovieAPI.Service.Base.FileService;
import com.husseinelbhrawy.MovieAPI.Utils.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.DEFAULT_API_FILES)
public class FilesController {

    private  final FileService fileService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public UploadFileResponse uploadFileHandler(@Valid @RequestPart("image") MultipartFile file , HttpServletRequest request) throws IOException{

        String fileName = fileService.uploadFile(AppConstants.DEFAULT_POSTER_PATH, file);
        String fileUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + AppConstants.DEFAULT_API_FILES + fileName;

        UploadFileResponse response = new UploadFileResponse();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("File is Uploaded Successfully");
        response.setUrl(fileUrl);
        return response;
    }

    @GetMapping("/{fileName}")
    public void getFileHandler(@PathVariable String fileName , HttpServletResponse response) throws IOException{
        InputStream resourceFile = fileService.getResourceFile(AppConstants.DEFAULT_POSTER_PATH, fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile, response.getOutputStream());
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFileHandler(@PathVariable String fileName) throws IOException{
        InputStreamResource resource = new InputStreamResource(fileService.getResourceFile(AppConstants.DEFAULT_POSTER_PATH, fileName));
        return ResponseEntity.ok().contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
