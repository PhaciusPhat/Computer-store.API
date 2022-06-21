package com.example.demo.services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinaryConfig;

    public CloudinaryServiceImpl(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    private File ConvertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    @Override
    public String upload(MultipartFile multipartFile) {
        try {
            File file = ConvertMultipartFileToFile(multipartFile);
            var uploadResult = cloudinaryConfig.uploader().upload(file, ObjectUtils.emptyMap());
            boolean isDeleted = file.delete();
            if (isDeleted) {
                return (String) uploadResult.get("url");
            }
            else{
                return uploadResult.get("url").toString();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error in uploading file to cloudinary");
        }
    }
}
