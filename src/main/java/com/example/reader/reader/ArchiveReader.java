package com.example.reader.reader;

import com.example.reader.processor.ArchiveProcessor;

import java.io.FileNotFoundException;

public interface ArchiveReader {
    void setFiles(String inputFile, String outputFile) throws IllegalArgumentException;

    void setProcessor(ArchiveProcessor processor);

    void unarchive() throws FileNotFoundException;

    void process() throws IllegalArgumentException;

    void archive() throws IllegalArgumentException, FileNotFoundException;
}
