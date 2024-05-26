package com.lingo.craft.adapters.inbound.analysis;

import com.lingo.craft.domain.analysis.service.LanguageAnalysisService;
import org.apache.tika.exception.TikaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.diabolocom.release.openapi.model.LanguageAnalysisRequest;
import com.diabolocom.release.openapi.model.LanguageAnalysisResponse;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;

@RestController
@RequestMapping("/analysis")
public class LanguageAnalysisController {
    private final LanguageAnalysisService languageAnalysisService;
    private final LanguageAnalysisModelMapper languageAnalysisModelMapper;

    public LanguageAnalysisController(
            LanguageAnalysisService languageAnalysisService,
            LanguageAnalysisModelMapper languageAnalysisModelMapper
    ) {
        this.languageAnalysisService = languageAnalysisService;
        this.languageAnalysisModelMapper = languageAnalysisModelMapper;
    }

    @PostMapping
    public ResponseEntity<LanguageAnalysisResponse> languageAnalysis(
            @RequestBody LanguageAnalysisRequest languageDetectionRequest
    ) throws IOException {
        var languageDetectionResponse = languageAnalysisModelMapper.toLanguageAnalysisResponse(
                languageAnalysisService.detectLanguage(
                        languageAnalysisModelMapper.toLanguageAnalysisModel(
                                languageDetectionRequest
                        )
                )
        );

        return ResponseEntity.ok(
                languageDetectionResponse
        );
    }

    @PostMapping("/upload")
    public ResponseEntity<LanguageAnalysisResponse> languageAnalysisFromFile(
            @RequestParam("userId") String userId,
            @RequestPart("file") MultipartFile multipartFile
    ) throws IOException, TikaException, SAXException {
        return ResponseEntity.ok(
                languageAnalysisModelMapper.toLanguageAnalysisResponse(
                        languageAnalysisService.detectLanguageFromFile(
                                userId,
                                multipartFile
                        )
                )
        );
    }
}
