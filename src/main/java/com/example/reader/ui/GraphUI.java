package com.example.reader.ui;

import com.example.reader.reader.TextReader;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

public class GraphUI implements UI{
    private final TextReader reader;

    public GraphUI(TextReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        JFrame f = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        JButton b = new JButton("click");
        b.setBounds(130, 100, 100, 40);

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(f);
        if (result == JFileChooser.APPROVE_OPTION) {
            reader.setFiles(fileChooser.getSelectedFile().getAbsolutePath(), fileChooser.getSelectedFile().getParent() + "\\output.txt");
            try {
                reader.read();
                reader.process();
                reader.write();
                System.out.println("Check output.txt in the same folder.");
                System.exit(0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        f.setSize(400, 500);
        f.setLayout(null);
        f.setVisible(true);
    }
}
