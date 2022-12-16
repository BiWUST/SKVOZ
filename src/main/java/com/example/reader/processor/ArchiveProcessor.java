package com.example.reader.processor;

import java.io.File;

public interface ArchiveProcessor {
    void setTextProcessor(Processor processor);

    File process(File file);
}
