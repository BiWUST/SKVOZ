package com.example.reader;

import com.example.reader.reader.PlainTextReader;
import com.example.reader.reader.TextReader;
import com.example.reader.ui.GraphUI;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GraphApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PlainTextReader plainTextReaderWithManualProcessor = context.getBean("plainTextReaderWithManualProcessor", PlainTextReader.class);

        GraphUI ui = new GraphUI(plainTextReaderWithManualProcessor);
        ui.run();
    }
}
