package com.husseinelbhrawy.MovieAPI.Service.Implementation;

import com.husseinelbhrawy.MovieAPI.Service.Base.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImplementation implements FileService {

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        //!1. Get name of the file
        String fileName = file.getOriginalFilename();

        //!2. Get The File Path
        String filePath = path + File.separator + fileName;


        //!3. Create File Object
        File localFile = new File(filePath);

        //!4. Check if file exists
        if (!localFile.getParentFile().exists()) localFile.getParentFile().mkdirs();

        //!5. Copy File or upload file to the path
        if (Files.exists(Path.of(localFile.getAbsolutePath()))){
            throw new FileNotFoundException("File already exists! Please choose another one");
        }
        //باختصار، هذا السطر ينقل محتوى الملف الذي تم رفعه إلى الموقع المحدد على الجهاز، ويستبدل أي ملف قديم يحمل نفس الاسم.
        Files.copy(file.getInputStream(), localFile.toPath()  );
        //!        , StandardCopyOption. REPLACE_EXISTING

        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        return new FileInputStream(filePath);
    }

    @Override
    public void deleteFile(String path, String fileName) throws IOException {
        String filePath = path + File.separator + fileName;
        Files.deleteIfExists(Path.of(filePath));
    }
}
