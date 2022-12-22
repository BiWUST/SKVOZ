package com.example.reader.cryptor;

public interface Cryptor {
    void setFiles(String inputFile, String outputFile) throws IllegalArgumentException;

    void encrypt();

    void decrypt();
}
