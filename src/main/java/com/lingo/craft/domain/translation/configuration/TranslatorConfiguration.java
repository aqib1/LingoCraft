package com.lingo.craft.domain.translation.configuration;

import com.deepl.api.Translator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TranslatorConfiguration {

    @Value("${deepl.apiKey}")
    private String apiKey;

    @Bean
    public Translator translator() {
        return new Translator(apiKey);
    }
}
