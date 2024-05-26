package com.lingo.craft.domain.analysis.service;

import com.lingo.craft.domain.analysis.exception.LanguageAnalysisFailure;
import com.lingo.craft.domain.analysis.model.LanguageAnalysisModel;
import com.lingo.craft.domain.analysis.util.TikaParserHelper;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import com.lingo.craft.domain.temporal.service.ContentAnalysisWorkflowService;
import com.lingo.craft.domain.user.exception.UserNotFoundException;
import com.lingo.craft.domain.user.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.lingo.craft.utils.LingoHelper.getDisplayLanguage;
import static com.lingo.craft.utils.LingoHelper.getResourcePath;

@Service
public class LanguageAnalysisService {
    private final ContentAnalysisWorkflowService contentAnalysisWorkflowService;
    private final UserService userService;

    public LanguageAnalysisService(
            ContentAnalysisWorkflowService contentAnalysisWorkflowService,
            UserService userService
    ) {
        this.contentAnalysisWorkflowService = contentAnalysisWorkflowService;
        this.userService = userService;
    }

    public LanguageAnalysisModel detectLanguage(LanguageAnalysisModel model) throws IOException {
        var tikaParserHelper = new TikaParserHelper();
        return createLanguageAnalysisModel(
                tikaParserHelper,
                model.getText(),
                model.getUserId()
        );
    }

    public LanguageAnalysisModel detectLanguageFromFile(String userId, MultipartFile multipartFile) throws IOException, TikaException, SAXException {
        var tempFile = new File(getResourcePath().concat("/").concat(multipartFile.getName()));
        multipartFile.transferTo(tempFile);
        var tikaParserHelper = new TikaParserHelper();
        try (var inputStream = new FileInputStream(tempFile)) {
            tikaParserHelper.parse(inputStream);
        } finally {
            FileUtils.delete(tempFile);
        }

        return createLanguageAnalysisModel(
                tikaParserHelper,
                tikaParserHelper.getContent(),
                userId
        );
    }

    private void isValidUser(String userId) {
        try {
            userService.getUserById(userId);
        } catch (UserNotFoundException ex) {
            throw new LanguageAnalysisFailure(
                    HttpStatus.BAD_REQUEST,
                    "Language analysis failed as there is no such user",
                    ex
            );
        }
    }

    private LanguageAnalysisModel createLanguageAnalysisModel(
            TikaParserHelper tikaParserHelper,
            String text,
            String userId
    ) {
        isValidUser(userId);

        var languageResult = tikaParserHelper.detectLanguage(text);
        var languageCode = languageResult.getLanguage();
        var languageAnalysisModel = LanguageAnalysisModel.builder()
                .userId(userId)
                .languageCode(languageCode)
                .language(getDisplayLanguage(languageCode))
                .text(text)
                .confidence(languageResult.getConfidence())
                .rawScore(languageResult.getRawScore())
                .metadata(tikaParserHelper.readMetadata())
                .build();

        languageAnalysisModel.setLanguageAnalysisWorkflowId(
                publishContentAnalysisEvents(languageAnalysisModel)
        );

        return languageAnalysisModel;
    }

    private String publishContentAnalysisEvents(
            LanguageAnalysisModel languageAnalysisModel
    ) {
        return contentAnalysisWorkflowService.publishContentAnalysisEvents(
                languageAnalysisModel.getUserId(),
                ContentSentimentAnalysisEvent.builder()
                        .languageCode(languageAnalysisModel.getLanguageCode())
                        .text(languageAnalysisModel.getText())
                        .build()
        );
    }
}

