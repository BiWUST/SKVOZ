package com.example.reader.service;

import com.example.reader.processor.ManualProcessor;
import com.example.reader.processor.ZIPProcessor;
import com.example.reader.reader.ArchiveReader;
import com.example.reader.reader.JSONTextReader;
import com.example.reader.reader.PlainTextReader;
import com.example.reader.reader.TextReader;
import com.example.reader.reader.XMLTextReader;
import com.example.reader.reader.ZIPArchiveReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class ReaderServiceImplementation implements ReaderService {
    private TextReader textReader;
    private ArchiveReader archiveReader;

    @Autowired
    private ManualProcessor manualProcessor;

    @Autowired
    private ZIPProcessor zipProcessor;

    @Override
    public MultipartFile processFile(MultipartFile file) throws IOException {
        switch (file.getContentType()) {
            case "text/plain":
                textReader = new PlainTextReader(manualProcessor);
                return processTextFile(file);
            case "application/zip":
                zipProcessor.setTextProcessor(manualProcessor);
                archiveReader = new ZIPArchiveReader(zipProcessor);
                return processZipFile(file);
            case "application/json":
                textReader = new JSONTextReader(manualProcessor);
                return processTextFile(file);
            case "application/xml":
                textReader = new XMLTextReader(manualProcessor);
                return processTextFile(file);
            default:
                throw new IllegalArgumentException("Unsupported file type");
        }
    }

    private MultipartFile processTextFile(MultipartFile file) throws IOException {
        File tempFolder = new File("temp");
        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }
        File inputFile = new File("temp/" + file.getOriginalFilename());
        File outputFile = new File("temp/" + "result_" + file.getOriginalFilename());
        try (OutputStream outputStream = new FileOutputStream(inputFile)) {
            outputStream.write(file.getBytes());
        }
        textReader.setFiles(inputFile.getPath(), outputFile.getPath());
        textReader.read();
        textReader.process();
        textReader.write();
        //apache commons не хочет работать со спрингом, поэтому ничего не будем возвращать
//        FileItem fileItem = new DiskFileItem("fileData", "text/plain",true, outputFile.getName(), 100000000, outputFile.getParentFile());
//        return new CommonsMultipartFile(fileItem);
        return null;
    }

    private MultipartFile processZipFile(MultipartFile file) throws IOException {
        File tempFolder = new File("temp");
        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }
        File inputFile = new File("temp/" + file.getOriginalFilename());
        File outputFile = new File("temp/" + "result_" + file.getOriginalFilename());
        try (OutputStream outputStream = new FileOutputStream(inputFile)) {
            outputStream.write(file.getBytes());
        }
        archiveReader.setFiles(inputFile.getPath(), outputFile.getPath());
        archiveReader.unarchive();
        archiveReader.process();
        archiveReader.archive();
//apache commons не хочет работать со спрингом, поэтому ничего не будем возвращать
//        FileItem fileItem = new DiskFileItem("fileData", "text/plain",true, outputFile.getName(), 100000000, outputFile.getParentFile());
//        return new CommonsMultipartFile(fileItem);
        return null;
    }
}
