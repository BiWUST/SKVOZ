package com.example.reader;

import com.example.reader.reader.ZIPArchiveReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.nio.file.Files;

public class ZIPArchiveReaderTests {
    private static final String TESTS_FOLDER = "src/test/resources/static/tests/";
    private static final String RESULTS_FOLDER = "src/test/resources/static/results/";
    private static final String EXPECTED_FOLDER = "src/test/resources/static/expected/";
    private static ZIPArchiveReader zipArchiveReader;

    @BeforeAll
    public static void setup() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        zipArchiveReader = context.getBean("zipArchiveReaderWithManualProcessor", ZIPArchiveReader.class);
    }

    @Test
    public void testWithTextArchive() {
        try {
            zipArchiveReader.setFiles(TESTS_FOLDER + "test9.zip", RESULTS_FOLDER + "result9.zip");
            zipArchiveReader.unarchive();
            zipArchiveReader.process();
            zipArchiveReader.archive();
            assert new File(RESULTS_FOLDER + "result9.zip").exists();
            //Почему-то сравнивать не удается таким образом, хотя содержание архива какое и должно быть, возможно из-за разных названий файлов
            assert Files.mismatch(new File(RESULTS_FOLDER + "result9.zip").toPath(), new File(EXPECTED_FOLDER + "expected9.zip").toPath()) == -1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
