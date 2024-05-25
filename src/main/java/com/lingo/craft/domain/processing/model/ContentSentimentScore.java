package com.lingo.craft.domain.processing.model;

public enum ContentSentimentScore {
    VERY_POSITIVE("Very positive"),
    POSITIVE("Positive"),
    NEUTRAL("NEUTRAL"),
    NEGATIVE("NEGATIVE"),
    VERY_NEGATIVE("VERY_NEGATIVE");
    final String sentimentName;

    ContentSentimentScore(String sentimentName) {
        this.sentimentName = sentimentName;
    }

    public static ContentSentimentScore getScore(int score) {
        return switch (score) {
            case 4 -> ContentSentimentScore.VERY_POSITIVE;
            case 3 -> ContentSentimentScore.POSITIVE;
            case 2 -> ContentSentimentScore.NEUTRAL;
            case 1 -> ContentSentimentScore.NEGATIVE;
            case 0 -> ContentSentimentScore.VERY_NEGATIVE;
            default -> throw new IllegalStateException("Unexpected value: " + score);
        };
    }

    public static int maxScore() {
        return 4;
    }
}
