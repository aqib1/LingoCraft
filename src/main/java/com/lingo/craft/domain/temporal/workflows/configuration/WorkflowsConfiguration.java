package com.lingo.craft.domain.temporal.workflows.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "workflows")
public class WorkflowsConfiguration {
    private ContentAnalysisEventWorkflow contentAnalysisEventWorkflow;
    @AllArgsConstructor
    @Setter
    @Getter
    public static class ContentAnalysisEventWorkflow {
        private String workflowIdPrefix;
    }
}
