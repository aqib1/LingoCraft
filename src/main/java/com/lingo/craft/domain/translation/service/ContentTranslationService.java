package com.lingo.craft.domain.translation.service;

import com.deepl.api.DeepLException;
import com.deepl.api.Translator;
import com.lingo.craft.domain.translation.exception.ContentTranslationException;
import com.lingo.craft.domain.translation.model.ContentTranslationInput;
import com.lingo.craft.domain.translation.model.ContentTranslationOutput;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTranslationService {

    private final Translator translator;

    public ContentTranslationService(Translator translator) {
        this.translator = translator;
    }

    public List<ContentTranslationOutput> translateContent(List<ContentTranslationInput> contentTranslationInputs) {
        return contentTranslationInputs.stream()
                .map(this::translateContent)
                .toList();
    }

    public ContentTranslationOutput translateContent(ContentTranslationInput input) {
        try {
            var translationResult = translator.translateText(
                    input.getText(),
                    input.getLanguageCode(),
                    input.getTargetLanguageCode()
            );

            return ContentTranslationOutput.builder()
                    .languageCode(translationResult.getDetectedSourceLanguage())
                    .text(translationResult.getText())
                    .build();

        } catch (DeepLException | InterruptedException ex) {
            throw new ContentTranslationException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage(),
                    ex
            );
        }
    }
}
