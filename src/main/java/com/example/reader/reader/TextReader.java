package com.example.reader.reader;

import com.example.reader.processor.Processor;

import java.io.FileNotFoundException;

public interface TextReader {
    void setFiles(String inputFile, String outputFile) throws IllegalArgumentException;

    void setProcessor(Processor processor);

    void read() throws FileNotFoundException;

    void process() throws IllegalArgumentException;

    void write() throws IllegalArgumentException, FileNotFoundException;
}
