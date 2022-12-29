package com.example.reader.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ReaderService {
    MultipartFile processFile(MultipartFile file) throws IOException;
}
