package com.blogapp.blogappiapi.services.impl;

import com.blogapp.blogappiapi.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();

        String randomID = UUID.randomUUID().toString();
        assert name != null;
        String filename = randomID.concat(name.substring(name.lastIndexOf(".")));

        String fullPath = path + File.separator + filename;

        File f = new File(path);
        if(!f.exists()) {
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(fullPath));
        return filename;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        String fullPath = path+ File.separator+filename;
        InputStream is  = new FileInputStream(fullPath);
        return is;
    }
}