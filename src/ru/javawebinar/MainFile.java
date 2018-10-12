package ru.javawebinar;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String pathname = ".\\src\\ru\\javawebinar\\";
        File file = new File(pathname);
        printAllFiles(file);
    }

    public static void printAllFiles(File file) {
        String[] names = file.list();
        if (names != null) {
            for (String name : names) {
                File tempFile;
                try {
                    String pathname = file.getCanonicalPath() + "\\" + name;
                    tempFile = new File(pathname);
                } catch (IOException e) {
                    throw new RuntimeException("Error", e);
                }
                if (tempFile.isDirectory()) {
                    printAllFiles(tempFile);
                } else {
                    System.out.println(name);
                }
            }
        }
    }
}
