package com.lingo.craft.domain.processing.model;

public enum ContentSentiment {
    VERY_POSITIVE("Very positive"),
    POSITIVE("Positive"),
    NEUTRAL("NEUTRAL"),
    NEGATIVE("NEGATIVE"),
    VERY_NEGATIVE("VERY_NEGATIVE");
    final String sentimentName;

    ContentSentiment(String sentimentName) {
        this.sentimentName = sentimentName;
    }

    public static ContentSentiment getScore(int score) {
        return switch (score) {
            case 4 -> ContentSentiment.VERY_POSITIVE;
            case 3 -> ContentSentiment.POSITIVE;
            case 2 -> ContentSentiment.NEUTRAL;
            case 1 -> ContentSentiment.NEGATIVE;
            case 0 -> ContentSentiment.VERY_NEGATIVE;
            default -> throw new IllegalStateException("Unexpected value: " + score);
        };
    }

    public static int maxScore() {
        return 4;
    }
}
