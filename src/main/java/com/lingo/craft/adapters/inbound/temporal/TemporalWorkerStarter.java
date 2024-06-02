package com.lingo.craft.adapters.inbound.temporal;

import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisActivity;
import com.lingo.craft.domain.temporal.activities.ContentSentimentAnalysisPersistenceActivity;
import com.lingo.craft.domain.temporal.activities.ContentTranslationActivity;
import com.lingo.craft.domain.temporal.workflows.impl.ContentAnalysisEventWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import static com.lingo.craft.utils.LingoHelper.TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE;

@Component
public class TemporalWorkerStarter {
    private final WorkflowClient workflowClient;
    private final ContentSentimentAnalysisActivity contentSentimentAnalysisActivity;
    private final ContentTranslationActivity contentTranslationActivity;
    private final ContentSentimentAnalysisPersistenceActivity contentSentimentAnalysisPersistenceActivity;
    public TemporalWorkerStarter(
            WorkflowClient workflowClient,
            ContentSentimentAnalysisActivity contentSentimentAnalysisActivity,
            ContentTranslationActivity contentTranslationActivity,
            ContentSentimentAnalysisPersistenceActivity contentSentimentAnalysisPersistenceActivity
    ) {
        this.workflowClient = workflowClient;
        this.contentSentimentAnalysisActivity = contentSentimentAnalysisActivity;
        this.contentTranslationActivity = contentTranslationActivity;
        this.contentSentimentAnalysisPersistenceActivity = contentSentimentAnalysisPersistenceActivity;
    }

    @PostConstruct
    public void startWorker() {
        var workerFactory = WorkerFactory.newInstance(workflowClient);
        var worker = workerFactory.newWorker(TASK_CONTENT_SEMANTIC_ANALYSIS_QUEUE);
        worker.registerWorkflowImplementationTypes(ContentAnalysisEventWorkflowImpl.class);
        worker.registerActivitiesImplementations(contentSentimentAnalysisActivity);
        worker.registerActivitiesImplementations(contentTranslationActivity);
        worker.registerActivitiesImplementations(contentSentimentAnalysisPersistenceActivity);

        workerFactory.start();
    }
}
