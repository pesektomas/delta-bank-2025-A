package org.example.logger;

import com.google.inject.Singleton;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Singleton
public class FileSystemLogger implements Logger {

    private final String filePath = "application.log";

    @Override
    public void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("[FILE] " + message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

}
