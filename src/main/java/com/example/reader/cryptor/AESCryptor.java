package com.example.reader.cryptor;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Component
public class AESCryptor implements Cryptor {
    byte[] decodedKey = Base64.getDecoder().decode("secret");
    private File inputFile;
    private File outputFile;

    public AESCryptor() {
    }

    public AESCryptor(File inputFile, File outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    public void setFiles(String inputFileName, String outputFileName) throws IllegalArgumentException {
        if (inputFileName == null || inputFileName.isEmpty() || outputFileName == null || outputFileName.isEmpty()) {
            throw new IllegalArgumentException("Input and output file names must not be null or empty.");
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
    public void encrypt() {
        if (inputFile == null || outputFile == null) {
            throw new IllegalArgumentException("Input and output files must be set before encryption.");
        }
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            Cipher desCipher = Cipher.getInstance("AES");
            byte[] text = new String(inputStream.readAllBytes()).getBytes(StandardCharsets.UTF_8);
            desCipher.init(Cipher.ENCRYPT_MODE, originalKey);
            byte[] textEncrypted = desCipher.doFinal(text);
            byte[] result = Base64.getEncoder().encode(textEncrypted);
            outputStream.write(result);
            outputStream.flush();
        } catch (Exception e) {
            System.out.println("Exception during encryption.");
        }
    }

    @Override
    public void decrypt() {
        if (inputFile == null || outputFile == null) {
            throw new IllegalArgumentException("Input and output files must be set before encryption.");
        }
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            Cipher desCipher = Cipher.getInstance("AES");
            desCipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] textDecrypted = desCipher.doFinal(Base64.getDecoder().decode(inputStream.readAllBytes()));
            outputStream.write(textDecrypted);
            outputStream.flush();
        } catch (Exception e) {
            System.out.println("Exception during decryption." + e.getMessage());
        }
    }
}
