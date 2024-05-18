package com.lingo.craft.adapters.inbound.detection;

import com.lingo.craft.domain.detection.service.LanguageDetectionService;
import com.lingo.craft.domain.temporal.events.ContentSentimentAnalysisEvent;
import com.lingo.craft.domain.temporal.service.ContentProcessingService;
import org.apache.tika.exception.TikaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.diabolocom.release.openapi.model.LanguageDetectionResponse;
import com.diabolocom.release.openapi.model.LanguageDetectionRequest;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;

@RestController
@RequestMapping("/detect")
public class LanguageDetectionController {
    private final LanguageDetectionService languageDetectionService;
    private final ContentProcessingService contentProcessingService;
    private final LanguageDetectionModelMapper languageDetectionModelMapper;

    public LanguageDetectionController(
            LanguageDetectionService languageDetectionService,
            ContentProcessingService contentProcessingService,
            LanguageDetectionModelMapper languageDetectionModelMapper
    ) {
        this.languageDetectionService = languageDetectionService;
        this.contentProcessingService = contentProcessingService;
        this.languageDetectionModelMapper = languageDetectionModelMapper;
    }

    @PostMapping
    public ResponseEntity<LanguageDetectionResponse> detectLanguage(
            @RequestBody LanguageDetectionRequest languageDetectionRequest
    ) throws IOException {
        var languageDetectionResponse = languageDetectionModelMapper.languageDetectionResponse(
                languageDetectionService.detectLanguage(
                        languageDetectionModelMapper.languageDetectionModel(
                                languageDetectionRequest
                        )
                )
        );
        contentProcessingService.publishContentAnalysisEvents(
                ContentSentimentAnalysisEvent.builder()
                        .languageCode(languageDetectionResponse.getLanguageCode())
                        .text(languageDetectionResponse.getText())
                        .build()
        );
        return ResponseEntity.ok(
                languageDetectionResponse
        );
    }

    @PostMapping("/upload")
    public ResponseEntity<LanguageDetectionResponse> detectLanguageFromFile(
            @RequestPart("file") MultipartFile multipartFile
    ) throws IOException, TikaException, SAXException {
        return ResponseEntity.ok(
                languageDetectionModelMapper.languageDetectionResponse(
                        languageDetectionService.detectLanguageFromFile(multipartFile)
                )
        );
    }
}
