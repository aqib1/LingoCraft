package com.lingo.craft.utils;

import java.nio.file.Paths;

public class FileHelper {
    private static final String SRC_DIR = "src";
    private static final String MAIN_DIR = "main";
    private static final String RESOURCES_DIR = "resources";
    private static final String MP3_EXTENSION = ".mp3";

    public static String getResourcePath() {
        return Paths.get(SRC_DIR, MAIN_DIR, RESOURCES_DIR)
                .toFile()
                .getAbsolutePath();
    }

    public static boolean isMP3(String path) {
        return path.toLowerCase().endsWith(MP3_EXTENSION);
    }

    private FileHelper() {

    }
}
