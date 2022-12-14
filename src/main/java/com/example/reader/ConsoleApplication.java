package com.example.reader;

import com.example.reader.reader.PlainTextReader;
import com.example.reader.reader.XMLTextReader;
import com.example.reader.ui.ConsoleUI;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsoleApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        PlainTextReader plainTextReaderWithManualProcessor = context.getBean("plainTextReaderWithManualProcessor", PlainTextReader.class);
        XMLTextReader xmlTextReaderWithManualProcessor = context.getBean("xmlTextReaderWithManualProcessor", XMLTextReader.class);

        ConsoleUI ui = new ConsoleUI(plainTextReaderWithManualProcessor);
        ui.run();

//        ConsoleUI ui = new ConsoleUI(xmlTextReaderWithManualProcessor);
//        ui.run();
    }
}
