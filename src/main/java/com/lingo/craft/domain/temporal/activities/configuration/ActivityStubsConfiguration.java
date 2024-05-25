package com.lingo.craft.domain.temporal.activities.configuration;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "activities")
public class ActivityStubsConfiguration {
    private ActivityOptionsConfiguration contentSemanticAnalysis;
    private ActivityOptionsConfiguration contentSentimentAnalysisPersistence;

    @AllArgsConstructor
    @Setter
    @Getter
    public static class ActivityOptionsConfiguration {
        private Duration startToCloseTimeout;
        private Duration initialInterval;
        private int maximumAttempts;
        private double backoffCoefficient;
        private Duration maximumInterval;
    }
}

