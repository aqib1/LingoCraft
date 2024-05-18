package com.lingo.craft.domain.processing.service;

import com.lingo.craft.domain.processing.model.ContentSentimentAnalysisModel;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextSentimentAnalysisService {
    private final StanfordCoreNLP stanfordCoreNLP;

    public TextSentimentAnalysisService(StanfordCoreNLP stanfordCoreNLP) {
        this.stanfordCoreNLP = stanfordCoreNLP;
    }

    public List<ContentSentimentAnalysisModel> sentimentAnalysis(
            List<ContentSentimentAnalysisEvent> contentAnalysisEvents
    ) {
        var contentSentimentAnalysis = new ArrayList<ContentSentimentAnalysisModel>();
        contentAnalysisEvents.forEach(event -> {
            var annotation = stanfordCoreNLP.process(event.getText());
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                var tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                var sentimentScore = RNNCoreAnnotations.getPredictedClass(tree);
                var sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
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
        System.out.println(contentSentimentAnalysis);
        return contentSentimentAnalysis;
    }
}
