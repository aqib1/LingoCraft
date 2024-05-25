package com.lingo.craft.domain.processing.service;

import com.lingo.craft.domain.processing.model.AccumulatedContentAnalysisEvent;
import com.lingo.craft.domain.processing.model.ContentSentimentAnalysisModel;
import com.lingo.craft.domain.processing.model.ContentSentimentScore;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TextSentimentAnalysisService {
    private static final String PERCENTAGE = "%";
    private final StanfordCoreNLP stanfordCoreNLP;

    public TextSentimentAnalysisService(StanfordCoreNLP stanfordCoreNLP) {
        this.stanfordCoreNLP = stanfordCoreNLP;
    }

    public AccumulatedContentAnalysisEvent sentimentAnalysis(
            String workflowId,
            List<ContentSentimentAnalysisEvent> contentAnalysisEvents
    ) {
        var contentSentimentAnalysis = new ArrayList<ContentSentimentAnalysisModel>();
        AtomicInteger countScore = new AtomicInteger(0);
        AtomicInteger scorePercentageCount = new AtomicInteger(0);
        contentAnalysisEvents.forEach(event -> {
            var annotation = stanfordCoreNLP.process(event.getText());
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                var tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                var sentimentScore = RNNCoreAnnotations.getPredictedClass(tree);
                var sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
                countScore.addAndGet(sentimentScore);
                scorePercentageCount.addAndGet(
                        generatePositiveSentimentPercentage(
                                sentimentScore,
                                ContentSentimentScore.maxScore()
                        )
                );
                contentSentimentAnalysis.add(
                        new ContentSentimentAnalysisModel(
                                sentence.toString(),
                                event.getLanguageCode(),
                                sentimentScore,
                                sentimentName
                        )
                );
            }
        });
        var averageSentiment = generateAverage(
                countScore.get(),
                contentSentimentAnalysis.size()
        );
        var positiveSentimentPercentage = generateAverage(
                scorePercentageCount.get(),
                contentSentimentAnalysis.size()
        ) + PERCENTAGE;

        return AccumulatedContentAnalysisEvent.builder()
                .contentSentimentAnalysisModels(contentSentimentAnalysis)
                .workflowId(workflowId)
                .accumulatedContentScore(averageSentiment)
                .positiveSentimentPercentage(positiveSentimentPercentage)
                .accumulatedContentSentimentScore(ContentSentimentScore.getScore(averageSentiment))
                .build();
    }

    private int generateAverage(int scoreCount, int limit) {
        return (int) Math.round((double) scoreCount / limit);
    }

    private int generatePositiveSentimentPercentage(int currentScore, int maxScore) {
        return (int) Math.round((double) currentScore / maxScore * 100);
    }
}
