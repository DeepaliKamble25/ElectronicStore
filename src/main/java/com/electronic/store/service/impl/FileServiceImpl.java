package com.electronic.store.service.impl;

import com.electronic.store.exception.BadApiRequest;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        logger.info("Initiating request to uploadFile" + file + "" + path);
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName  :{} " + originalFilename);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = fileName + extension;
        String fullPathWithFileName = path + fileNameWithExtension;

        logger.info("full image path: {}" + fullPathWithFileName);

        if ((extension.equalsIgnoreCase(".png")) || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
             logger.info("file extension" +
                     " is : {}"+extension);
            File folder = new File(path);
            if (!folder.exists()) {
//                folder we are using multiple label then use mkdirs***
                folder.mkdirs();
            }// upload
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;


        } else {
            throw new BadApiRequest(ApiConstant.Bad_ApiRequest1 + extension + ApiConstant.Bad_ApiRequest2);
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullpath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullpath);
        return inputStream;
    }
}
