package com.lingo.craft.domain.detection.service;

import com.lingo.craft.domain.detection.model.LanguageDetectionModel;
import com.lingo.craft.domain.detection.util.TikaParserHelper;
import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.lingo.craft.utils.FileHelper.getResourcePath;
import static com.lingo.craft.utils.FileHelper.isMP3;

@Service
public class LanguageDetectionService {
    private final LanguageDetector languageDetector;

    public LanguageDetectionService(LanguageDetector languageDetector) {
        this.languageDetector = languageDetector;
    }

    public LanguageDetectionModel detectLanguage(LanguageDetectionModel model) {
        return createLanguageDetectionResponse(
                languageDetector.detect(model.getText()),
                model.getText()
        );
    }

    public LanguageDetectionModel detectLanguageFromFile(MultipartFile multipartFile) throws IOException, TikaException, SAXException {
        var tempFile = new File(getResourcePath().concat("/").concat(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(tempFile);
        var tikaParserHelper = new TikaParserHelper();
        try (var inputStream = new FileInputStream(tempFile)) {
            if(isMP3(multipartFile.getOriginalFilename())) {
                tikaParserHelper.parseMP3(inputStream);
            } else {
                tikaParserHelper.parse(inputStream);
            }
        } finally {
            FileUtils.delete(tempFile);
        }

        var content = tikaParserHelper.getContent();
        return createLanguageDetectionResponse(
                languageDetector.detect(content),
                content
        );
    }

    private LanguageDetectionModel createLanguageDetectionResponse(
            LanguageResult languageResult,
            String text
    ) {
        return LanguageDetectionModel.builder()
                .languageCode(languageResult.getLanguage())
                .text(text)
                .confidence(languageResult.getConfidence())
                .rawScore(languageResult.getRawScore())
                .build();
    }
}

