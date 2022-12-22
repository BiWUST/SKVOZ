package com.example.reader;

import com.example.reader.cryptor.AESCryptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.nio.file.Files;

public class AESCryptorTests {
    private static final String TESTS_FOLDER = "src/test/resources/static/tests/";
    private static final String RESULTS_FOLDER = "src/test/resources/static/results/";
    private static final String EXPECTED_FOLDER = "src/test/resources/static/expected/";
    private static AESCryptor aesCryptor;

    @BeforeAll
    public static void setup() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        aesCryptor = context.getBean("aesCryptor", AESCryptor.class);
    }

    @Test
    public void encryptTextTest() {
        try {
            aesCryptor.setFiles(TESTS_FOLDER + "test10.txt", RESULTS_FOLDER + "result10.txt");
            aesCryptor.encrypt();
            assert new File(RESULTS_FOLDER + "result10.txt").exists();
            aesCryptor.setFiles(RESULTS_FOLDER + "result10.txt", RESULTS_FOLDER + "result10_2.txt");
            aesCryptor.decrypt();
            assert Files.mismatch(new File(RESULTS_FOLDER + "result10_2.txt").toPath(), new File(EXPECTED_FOLDER + "expected10.txt").toPath()) == -1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
