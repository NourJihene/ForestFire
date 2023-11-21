package com.example.foret_kata.util;

import java.io.InputStream;

public class PropertiesReader {
    public static InputStream loadFile(String fileDirectory) {
        InputStream  input = PropertiesReader.class.getClassLoader().getResourceAsStream(fileDirectory);
        if (input == null) {
            System.out.println("Sorry, unable to find " + fileDirectory);
            return null;
        }
        return input;
    }

}

