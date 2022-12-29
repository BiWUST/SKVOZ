package com.example.reader.reader;

import com.example.reader.processor.ArchiveProcessor;
import com.example.reader.processor.ZIPProcessor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
public class ZIPArchiveReader implements ArchiveReader {
    private ArchiveProcessor processor;
    private File inputFile;
    private File outputFile;
    private boolean isProcessed;

    private ZIPArchiveReader() {
    }

    public ZIPArchiveReader(ZIPProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void setFiles(String inputFileName, String outputFileName) throws IllegalArgumentException {
        if (inputFileName == null || inputFileName.isEmpty() || outputFileName == null || outputFileName.isEmpty()) {
            throw new IllegalArgumentException("Input and output file names must not be null or empty.");
        }
        if (!inputFileName.endsWith(".zip") || !outputFileName.endsWith(".zip")) {
            throw new IllegalArgumentException("Input and output file names must end with .zip.");
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
    public void setProcessor(ArchiveProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void unarchive() {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(inputFile))) {
            ZipEntry entry;
            String name;
            long size;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                size = entry.getSize();
                System.out.printf("File name: %s \t File size: %d \n", name, size);

                FileOutputStream fout = new FileOutputStream(getTempDirectory(inputFile) + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println("Error in reading archive.");
        }
    }

    private String getTempDirectory(File file) {
        File dir = new File(file.getParent() + "/temp_" + file.getName() + "/");
        dir.mkdir();
        return dir.getAbsolutePath() + "/";
    }

    @Override
    public void process() throws IllegalArgumentException {
        if (inputFile == null) {
            throw new IllegalArgumentException("Input file must be set and unarchive before processing.");
        }
        if (processor == null) {
            throw new IllegalArgumentException("Processor must be set before processing.");
        }
        File folder = new File(getTempDirectory(inputFile));
        for (File file : folder.listFiles()) {
            processor.process(file);
            file.delete();
        }
        this.isProcessed = true;
    }

    @Override
    public void archive() throws IllegalArgumentException, FileNotFoundException {
        if (inputFile == null || outputFile == null) {
            throw new IllegalStateException("Input and output files must be set before reading and writing.");
        }
        if (!isProcessed) {
            throw new IllegalArgumentException("Archive must be read and processed before writing.");
        }
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(outputFile))) {
            for (File file : new File(getTempDirectory(inputFile)).listFiles()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    zout.putNextEntry(new ZipEntry(file.getName().substring(2)));
                    for (int c = fis.read(); c != -1; c = fis.read()) {
                        zout.write(c);
                    }
                    zout.closeEntry();
                } catch (IOException e) {
                    System.out.println("Error in writing archive.");
                }
                file.delete();
            }
            new File(getTempDirectory(inputFile)).delete();
        } catch (IOException e) {
            System.out.println("Error in writing archive.");
        }
    }
}
