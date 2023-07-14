package com.electronic.store.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface CoverImageService {



    public String uploadCoverImage(MultipartFile multipartFile,String coverpath) throws IOException;

    InputStream getResource (String coverpath ,String covername) throws FileNotFoundException;
}
