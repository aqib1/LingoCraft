package com.lingo.craft.domain.translation.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ContentTranslationInput {
    private String text;
    private String languageCode;
    private String targetLanguageCode;
}
