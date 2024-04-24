package com.lingo.craft.utils;

import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

public class LingoHelper {
    private static final String SRC_DIR = "src";
    private static final String MAIN_DIR = "main";
    private static final String RESOURCES_DIR = "resources";
    private static final List<String> NOT_ALLOWED_METADATA_KEYS = List.of(
            "X-TIKA:Parsed-By",
            "X-TIKA:Parsed-By-Full-Set"
    );

    public static boolean isMetadataKeyAllowed(String key) {
        return !NOT_ALLOWED_METADATA_KEYS.contains(key);
    }

    public static String getResourcePath() {
        return Paths.get(SRC_DIR, MAIN_DIR, RESOURCES_DIR)
                .toFile()
                .getAbsolutePath();
    }

    public static String getDisplayLanguage(String languageCode) {
        var loc = new Locale(languageCode);
        return loc.getDisplayLanguage(loc);
    }

    private LingoHelper() {

    }
}
