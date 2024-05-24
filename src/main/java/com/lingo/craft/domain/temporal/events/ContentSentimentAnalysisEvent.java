package com.lingo.craft.domain.temporal.events;


import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ContentSentimentAnalysisEvent implements Serializable {
    private String text;
    private String languageCode;
    private String workflowId;
}
