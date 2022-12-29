package com.example.reader.controller;

import com.example.reader.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class ReaderController {
    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @PostMapping("/process")
    public MultipartFile upload(@RequestParam(value = "file") MultipartFile file) {
        try {
            return readerService.processFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
