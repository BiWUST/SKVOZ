package com.example.reader.processor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManualProcessor implements Processor {
    private final List<String> digits = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "-");

    @Override
    public String process(String text) {
        text = processOperation(text, "*");
        text = processOperation(text, "/");
        text = processOperation(text, "+");
        text = processOperation(text, "-");
        return text;
    }

    private String processOperation(String text, String operation) {
        int indexOfOperation = text.indexOf(operation);
        while (indexOfOperation > -1 && indexOfOperation < text.length()) {
            int indexOfFirstNumber = getIndexOfFirstNumber(text, indexOfOperation);
            int indexOfSecondNumber = getIndexOfSecondNumber(text, indexOfOperation);
            String firstNumber = text.substring(indexOfFirstNumber + 1, indexOfOperation).trim();
            String secondNumber = text.substring(indexOfOperation + 1, indexOfSecondNumber).trim();
            if (!firstNumber.isEmpty() && !secondNumber.isEmpty()) {
                text = replaceText(text, indexOfFirstNumber, indexOfSecondNumber, firstNumber, secondNumber, operation);
            }
            indexOfOperation = findNextIndexOfOperation(text, indexOfOperation, operation);
        }
        return text;
    }

    private int findNextIndexOfOperation(String text, int indexOfOperation, String operation) {
        for (int i = indexOfOperation + 1; i < text.length(); i++) {
            if (Character.toString(text.charAt(i)).equals(operation)) {
                return i;
            }
        }
        return text.length();
    }

    private int getIndexOfFirstNumber(String text, int indexOfOperation) {
        int indexOfFirstNumber = indexOfOperation - 1;
        if(Character.toString(text.charAt(indexOfFirstNumber)).equals(" ")) {
            indexOfFirstNumber--;
        }
        while (indexOfFirstNumber > 0 && digits.contains(Character.toString(text.charAt(indexOfFirstNumber)))) {
            indexOfFirstNumber--;
        }
        return indexOfFirstNumber;
    }

    private int getIndexOfSecondNumber(String text, int indexOfOperation) {
        int indexOfSecondNumber = indexOfOperation + 1;
        if(Character.toString(text.charAt(indexOfSecondNumber)).equals(" ")) {
            indexOfSecondNumber++;
        }
        while (indexOfSecondNumber < text.length() && digits.contains(Character.toString(text.charAt(indexOfSecondNumber)))) {
            indexOfSecondNumber++;
        }
        return indexOfSecondNumber;
    }

    private String replaceText(String text, int indexOfFirstNumber, int indexOfSecondNumber, String firstNumber, String secondNumber, String operation) {
        if (!firstNumber.isEmpty() && !secondNumber.isEmpty()) {
            switch (operation) {
                case "*" -> {
                    String result = String.valueOf(Double.parseDouble(firstNumber) * Double.parseDouble(secondNumber));
                    text = text.substring(0, indexOfFirstNumber + 1) + result + text.substring(indexOfSecondNumber);
                }
                case "/" -> {
                    String result = String.valueOf(Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber));
                    text = text.substring(0, indexOfFirstNumber + 1) + result + text.substring(indexOfSecondNumber);
                }
                case "+" -> {
                    String result = String.valueOf(Double.parseDouble(firstNumber) + Double.parseDouble(secondNumber));
                    text = text.substring(0, indexOfFirstNumber + 1) + result + text.substring(indexOfSecondNumber);
                }
                case "-" -> {
                    String result = String.valueOf(Double.parseDouble(firstNumber) - Double.parseDouble(secondNumber));
                    text = text.substring(0, indexOfFirstNumber + 1) + result + text.substring(indexOfSecondNumber);
                }
            }
        }
        return text;
    }
}
