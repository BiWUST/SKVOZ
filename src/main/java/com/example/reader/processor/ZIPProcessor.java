package com.example.reader.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ZIPProcessor implements ArchiveProcessor {
    private Processor textProcessor;

    public ZIPProcessor() {
    }

    public ZIPProcessor(Processor processor) {
        this.textProcessor = processor;
    }

    @Override
    public void setTextProcessor(Processor processor) {
        this.textProcessor = processor;
    }

    @Override
    public File process(File file) {
        if (textProcessor == null) {
            throw new IllegalStateException("Text processor is not set.");
        }
        try (Scanner scanner = new Scanner(file)) {
            String text = scanner.useDelimiter("\\Z").next();
            String result = textProcessor.process(text);
            File resultFile = new File(file.getParent() + "/" + "r_" + file.getName());
            try (PrintWriter writer = new PrintWriter(resultFile)) {
                writer.print(result);
            } catch (FileNotFoundException e) {
                throw new IllegalStateException("Result file could not be created.");
            }
            return resultFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }
}
