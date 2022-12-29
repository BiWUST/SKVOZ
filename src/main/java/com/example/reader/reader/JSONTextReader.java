package com.example.reader.reader;

import com.example.reader.processor.Processor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

@Component
public class JSONTextReader implements TextReader {
    private Processor processor;
    private File inputFile;
    private File outputFile;
    private String text;
    private boolean isProcessed;

    private JSONTextReader() {
    }

    public JSONTextReader(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void setFiles(String inputFileName, String outputFileName) throws IllegalArgumentException {
        if (inputFileName == null || inputFileName.isEmpty() || outputFileName == null || outputFileName.isEmpty()) {
            throw new IllegalArgumentException("Input and output file names must not be null or empty.");
        }
        if (!inputFileName.endsWith(".json") || !outputFileName.endsWith(".json")) {
            throw new IllegalArgumentException("Input and output file names must end with .json.");
        }
        File inputFile = new File(inputFileName);
        if (!inputFile.exists()) {
            throw new IllegalArgumentException("Input file does not exist.");
        }
        File outputFile = new File(outputFileName);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                throw new IllegalArgumentException("Output file could not be created.");
            }
        }
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public void read() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(inputFile)) {
            this.text = scanner.useDelimiter("\\Z").next();
            this.isProcessed = false;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Input file not found.");
        }
    }

    @Override
    public void process() throws IllegalArgumentException {
        if (inputFile == null || text == null) {
            throw new IllegalArgumentException("Input file must be set and read before processing.");
        }
        if (processor == null) {
            throw new IllegalArgumentException("Processor must be set before processing.");
        }
        this.text = processor.process(text);
        this.isProcessed = true;
    }

    @Override
    public void write() throws IllegalArgumentException, FileNotFoundException {
        if (inputFile == null || outputFile == null) {
            throw new IllegalStateException("Input and output files must be set before reading and writing.");
        }
        if (text == null || !isProcessed) {
            throw new IllegalArgumentException("Text must be read and processed before writing.");
        }
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            writer.print(text);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Error while writing to output file.");
        }
    }
}
