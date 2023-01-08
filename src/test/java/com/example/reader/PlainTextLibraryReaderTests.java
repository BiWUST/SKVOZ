package com.example.reader;

import com.example.reader.reader.PlainTextReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.nio.file.Files;

public class PlainTextLibraryReaderTests {
    private static PlainTextReader plainTextReader;
    private static final String TESTS_FOLDER = "src/test/resources/static/tests/";
    private static final String RESULTS_FOLDER = "src/test/resources/static/results/";
    private static final String EXPECTED_FOLDER = "src/test/resources/static/expected/";

    @BeforeAll
    public static void setup() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        plainTextReader = context.getBean("plainTextReaderWithLibraryProcessor", PlainTextReader.class);
    }

    @Test
    public void testWithOnlyText() {
        try {
            plainTextReader.setFiles(TESTS_FOLDER + "test1.txt", RESULTS_FOLDER + "result1.txt");
            plainTextReader.read();
            plainTextReader.process();
            plainTextReader.write();
            assert new File(RESULTS_FOLDER + "result1.txt").exists();
            assert Files.mismatch(new File(RESULTS_FOLDER + "result1.txt").toPath(), new File(EXPECTED_FOLDER + "expected1.txt").toPath()) == -1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithTextAndNumbers() {
        try {
            plainTextReader.setFiles(TESTS_FOLDER + "test2.txt", RESULTS_FOLDER + "result2.txt");
            plainTextReader.read();
            plainTextReader.process();
            plainTextReader.write();
            assert new File(RESULTS_FOLDER + "result2.txt").exists();
            assert Files.mismatch(new File(RESULTS_FOLDER + "result2.txt").toPath(), new File(EXPECTED_FOLDER + "expected2.txt").toPath()) == -1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithTextAndNumbersAndSpaces() {
        try {
            plainTextReader.setFiles(TESTS_FOLDER + "test5.txt", RESULTS_FOLDER + "result5.txt");
            plainTextReader.read();
            plainTextReader.process();
            plainTextReader.write();
            assert new File(RESULTS_FOLDER + "result5.txt").exists();
            assert Files.mismatch(new File(RESULTS_FOLDER + "result5.txt").toPath(), new File(EXPECTED_FOLDER + "expected5.txt").toPath()) == -1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
