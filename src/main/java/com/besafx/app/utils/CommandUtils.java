package com.besafx.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandUtils {

    public static void excecuteCommand(String[] command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(command);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
        }
    }

    public static boolean isWindows(){
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }
}
