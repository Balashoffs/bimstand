package com.bablshoff.bimstand.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFromResource {
    public static String readConfigFile(Class<?> clazz) throws IOException {
        InputStream inputStream = clazz.getResourceAsStream("/fileTest.txt");
        return readFromInputStream(inputStream);
    }

    private static  String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
