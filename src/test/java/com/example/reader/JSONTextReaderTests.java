package com.example.reader;

import com.example.reader.reader.JSONTextReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.nio.file.Files;

public class JSONTextReaderTests {
    private static final String TESTS_FOLDER = "src/test/resources/static/tests/";
    private static final String RESULTS_FOLDER = "src/test/resources/static/results/";
    private static final String EXPECTED_FOLDER = "src/test/resources/static/expected/";
    private static JSONTextReader jsonTextReader;

    @BeforeAll
    public static void setup() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        jsonTextReader = context.getBean("jsonTextReaderWithManualProcessor", JSONTextReader.class);
    }

    @Test
    public void testWithOnlyText() {
        try {
            jsonTextReader.setFiles(TESTS_FOLDER + "test6.json", RESULTS_FOLDER + "result6.json");
            jsonTextReader.read();
            jsonTextReader.process();
            jsonTextReader.write();
            assert new File(RESULTS_FOLDER + "result6.json").exists();
            assert Files.mismatch(new File(RESULTS_FOLDER + "result6.json").toPath(), new File(EXPECTED_FOLDER + "expected6.json").toPath()) == -1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithTextAndNumbers() {
        try {
            jsonTextReader.setFiles(TESTS_FOLDER + "test7.json", RESULTS_FOLDER + "result7.json");
            jsonTextReader.read();
            jsonTextReader.process();
            jsonTextReader.write();
            assert new File(RESULTS_FOLDER + "result7.json").exists();
            assert Files.mismatch(new File(RESULTS_FOLDER + "result7.json").toPath(), new File(EXPECTED_FOLDER + "expected7.json").toPath()) == -1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithTextAndNumbersAndSpaces() {
        try {
            jsonTextReader.setFiles(TESTS_FOLDER + "test8.json", RESULTS_FOLDER + "result8.json");
            jsonTextReader.read();
            jsonTextReader.process();
            jsonTextReader.write();
            assert new File(RESULTS_FOLDER + "result8.json").exists();
            assert Files.mismatch(new File(RESULTS_FOLDER + "result8.json").toPath(), new File(EXPECTED_FOLDER + "expected8.json").toPath()) == -1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
