package com.example.reader.ui;

import com.example.reader.reader.TextReader;

import java.util.Scanner;

public class ConsoleUI implements UI {
    private final TextReader reader;

    public ConsoleUI(TextReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        try (Scanner scanner =  new Scanner(System.in)) {
            System.out.println("Enter input file name:");
            String inputFileName = scanner.nextLine();
            System.out.println("Enter output file name:");
            String outputFileName = scanner.nextLine();
            reader.setFiles(inputFileName, outputFileName);
        }
        try {
            reader.read();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            reader.process();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            reader.write();
            System.out.println("File successfully written.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
