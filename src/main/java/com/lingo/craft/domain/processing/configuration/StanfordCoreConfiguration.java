package com.lingo.craft.domain.processing.configuration;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class StanfordCoreConfiguration {
    @Value("${stanfordCore.annotators}")
    private String stanfordAnnotators;

    @Bean
    public StanfordCoreNLP stanfordCoreNLP() {
        var properties = new Properties();
        properties.setProperty("annotators", stanfordAnnotators);
        return new StanfordCoreNLP(properties);
    }
}
