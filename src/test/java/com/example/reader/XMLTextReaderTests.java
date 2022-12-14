package com.example.reader;

import com.example.reader.reader.XMLTextReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.nio.file.Files;

public class XMLTextReaderTests {
    private static XMLTextReader xmlTextReader;
    private static final String TESTS_FOLDER = "src/test/resources/static/tests/";
    private static final String RESULTS_FOLDER = "src/test/resources/static/results/";
    private static final String EXPECTED_FOLDER = "src/test/resources/static/expected/";

    @BeforeAll
    public static void setup() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        xmlTextReader = context.getBean("xmlTextReaderWithManualProcessor", XMLTextReader.class);
    }

    @Test
    public void testWithTextAndNumbersInXML() {
        try {
            xmlTextReader.setFiles(TESTS_FOLDER + "test3.xml", RESULTS_FOLDER + "result3.xml");
            xmlTextReader.read();
            xmlTextReader.process();
            xmlTextReader.write();
            assert new File(RESULTS_FOLDER + "result3.xml").exists();
            assert Files.mismatch(new File(RESULTS_FOLDER + "result3.xml").toPath(), new File(EXPECTED_FOLDER + "expected3.xml").toPath()) == -1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithTextAndNumbersInXML2() {
        try {
            xmlTextReader.setFiles(TESTS_FOLDER + "test4.xml", RESULTS_FOLDER + "result4.xml");
            xmlTextReader.read();
            xmlTextReader.process();
            xmlTextReader.write();
            assert new File(RESULTS_FOLDER + "result4.xml").exists();
            assert Files.mismatch(new File(RESULTS_FOLDER + "result4.xml").toPath(), new File(EXPECTED_FOLDER + "expected4.xml").toPath()) == -1L;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
