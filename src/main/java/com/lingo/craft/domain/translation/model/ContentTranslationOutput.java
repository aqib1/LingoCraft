package com.lingo.craft.domain.translation.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ContentTranslationOutput {
    private String text;
    private String languageCode;
}
