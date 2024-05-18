package com.lingo.craft.domain.detection.service;

import com.lingo.craft.domain.detection.model.LanguageDetectionModel;
import com.lingo.craft.domain.detection.util.TikaParserHelper;
import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.lingo.craft.utils.LingoHelper.getDisplayLanguage;
import static com.lingo.craft.utils.LingoHelper.getResourcePath;

@Service
public class LanguageDetectionService {

    public LanguageDetectionModel detectLanguage(LanguageDetectionModel model) throws IOException {
        var tikaParserHelper = new TikaParserHelper();
        return createLanguageDetectionResponse(
                tikaParserHelper,
                model.getText()
        );
    }

    public LanguageDetectionModel detectLanguageFromFile(MultipartFile multipartFile) throws IOException, TikaException, SAXException {
        var tempFile = new File(getResourcePath().concat("/").concat(multipartFile.getName()));
        multipartFile.transferTo(tempFile);
        var tikaParserHelper = new TikaParserHelper();
        try (var inputStream = new FileInputStream(tempFile)) {
            tikaParserHelper.parse(inputStream);
        } finally {
            FileUtils.delete(tempFile);
        }

        return createLanguageDetectionResponse(
                tikaParserHelper,
                tikaParserHelper.getContent()
        );
    }

    private LanguageDetectionModel createLanguageDetectionResponse(
            TikaParserHelper tikaParserHelper,
            String text
    ) {
        var languageResult = tikaParserHelper.detectLanguage(text);
        var languageCode = languageResult.getLanguage();
        return LanguageDetectionModel.builder()
                .languageCode(languageCode)
                .language(getDisplayLanguage(languageCode))
                .text(text)
                .confidence(languageResult.getConfidence())
                .rawScore(languageResult.getRawScore())
                .metadata(tikaParserHelper.readMetadata())
                .build();
    }
}

