package org.example.logger;

import com.google.inject.Singleton;

@Singleton
public class ConsoleLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println("[CONSOLE] " + message);
    }

}
