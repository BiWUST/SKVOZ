package com.example.reader.processor;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.parser.ParseException;
import org.springframework.stereotype.Component;

@Component
public class LibraryProcessor implements Processor {
    @Override
    public String process(String text) {
        int startOfOperation = 0;
        int endOfOperation = text.length() - 1;

        for(int i = 0; i < text.length(); i++) {
            if(text.charAt(i) >= 42 && text.charAt(i) < 58) {
                startOfOperation = i;
                for(int j = i + 1; j < text.length(); j++) {
                    if((text.charAt(i) < 42 || text.charAt(i) >= 58) && text.charAt(i) != ' ') {
                        endOfOperation = j;
                        break;
                    }
                }
                Expression expression = new Expression(text.substring(startOfOperation, endOfOperation + 1));
                try {
                    text = text.substring(0, i) + expression.evaluate().getNumberValue() + text.substring(endOfOperation + 1);
                } catch (EvaluationException | ParseException e) {
                }
                i = endOfOperation;
                endOfOperation = text.length() - 1;
            }
        }
        return text;
    }
}
