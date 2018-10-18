package ru.javawebinar;

import java.io.File;

public class MainFile {
    private static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        String pathname = ".\\src\\";
        File file = new File(pathname);
        printAllFiles(file);
    }

    public static void printAllFiles(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File tempFile : files) {
                if (tempFile.isDirectory()) {
                    System.out.println( sb.toString() +"Directory: " + tempFile.getName());
                    sb.append("\t");
                    printAllFiles(tempFile);
                    sb.deleteCharAt(sb.length() - 1);
                } else {
                    System.out.println( sb.toString() +"File: " + tempFile.getName());
                }
            }
        }
    }
}
