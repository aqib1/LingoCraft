package com.lingo.craft.adapters.inbound.detection;

import com.lingo.craft.domain.detection.service.LanguageDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.diabolocom.release.openapi.model.LanguageDetectionResponse;
import com.diabolocom.release.openapi.model.LanguageDetectionRequest;

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
}
