package com.lingo.craft.adapters.inbound.detection;

import com.lingo.craft.domain.detection.service.LanguageDetectionService;
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
    private final LanguageDetectionModelMapper languageDetectionModelMapper;

    public LanguageDetectionController(
            LanguageDetectionService languageDetectionService,
            LanguageDetectionModelMapper languageDetectionModelMapper
    ) {
        this.languageDetectionService = languageDetectionService;
        this.languageDetectionModelMapper = languageDetectionModelMapper;
    }

    @PostMapping
    public ResponseEntity<LanguageDetectionResponse> detectLanguage(
            @RequestBody LanguageDetectionRequest languageDetectionRequest
    ) {
        return ResponseEntity.ok(
                languageDetectionModelMapper.languageDetectionResponse(
                        languageDetectionService.detectLanguage(
                                languageDetectionModelMapper.languageDetectionModel(
                                        languageDetectionRequest
                                )
                        )
                )
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
