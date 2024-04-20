package com.lingo.craft.adapters.configuration;

import org.apache.tika.Tika;
import org.apache.tika.parser.AutoDetectParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.langdetect.optimaize.OptimaizeLangDetector;

import java.io.IOException;

@Configuration
public class TikaConfiguration {

    @Bean
    public Tika tika() {
        return new Tika();
    }

    @Bean
    public AutoDetectParser autoDetectParser() {
        return new AutoDetectParser();
    }

    @Bean
    public LanguageDetector languageDetector() throws IOException {
        var detector = OptimaizeLangDetector.getDefaultLanguageDetector();
        detector.loadModels();
        return detector;
    }


}
