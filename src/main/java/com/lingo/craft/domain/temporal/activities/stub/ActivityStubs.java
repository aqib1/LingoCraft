package com.lingo.craft.domain.temporal.activities.stub;

import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisActivity;
import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisPersistenceActivity;
import com.lingo.craft.domain.temporal.activities.ContentTranslationActivity;
import com.lingo.craft.domain.temporal.activities.configuration.ActivityStubsConfiguration;
import com.lingo.craft.domain.temporal.activities.configuration.ActivityStubsConfiguration.ActivityOptionsConfiguration;
import com.lingo.craft.utils.SpringContext;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.Getter;

@Getter
public class ActivityStubs {
    private final ActivityStubsConfiguration activityStubsConfiguration;
    private ActivityOptions contentSemanticAnalysis;
    private ActivityOptions contentSentimentAnalysisPersistence;
    private ActivityOptions contentTranslation;
    private ContentSentimentAnalysisActivity contentSentimentAnalysisActivity;
    private ContentTranslationActivity contentTranslationActivity;
    private ContentSentimentAnalysisPersistenceActivity contentSentimentAnalysisPersistenceActivity;

    public ActivityStubs() {
        this.activityStubsConfiguration = SpringContext.getBean(ActivityStubsConfiguration.class);
        initActivities();
    }

    private void initActivities() {
        this.contentTranslation = activityOptions(
                activityStubsConfiguration.getContentTranslation()
        );
        this.contentSemanticAnalysis = activityOptions(
                activityStubsConfiguration.getContentSemanticAnalysis()
        );
        this.contentSentimentAnalysisPersistence = activityOptions(
                activityStubsConfiguration.getContentSentimentAnalysisPersistence()
        );

        this.contentSentimentAnalysisActivity = Workflow.newActivityStub(
                ContentSentimentAnalysisActivity.class,
                contentSemanticAnalysis
        );
        this.contentTranslationActivity = Workflow.newActivityStub(
                ContentTranslationActivity.class,
                contentTranslation
        );
        this.contentSentimentAnalysisPersistenceActivity = Workflow.newActivityStub(
                ContentSentimentAnalysisPersistenceActivity.class,
                contentSentimentAnalysisPersistence
        );
    }

    private ActivityOptions activityOptions(
            ActivityOptionsConfiguration config
    ) {
        return ActivityOptions.newBuilder()
                .setRetryOptions(
                        RetryOptions.newBuilder()
                                .setInitialInterval(config.getInitialInterval())
                                .setMaximumAttempts(config.getMaximumAttempts())
                                .setBackoffCoefficient(config.getBackoffCoefficient())
                                .setMaximumInterval(config.getMaximumInterval())
                                .build()
                ).setStartToCloseTimeout(config.getStartToCloseTimeout())
                .build();
    }
}
