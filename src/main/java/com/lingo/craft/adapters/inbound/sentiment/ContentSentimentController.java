package com.lingo.craft.adapters.inbound.sentiment;

import com.lingo.craft.domain.sentiment.service.ContentSentimentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.diabolocom.release.openapi.model.ContentSentimentResponse;

import java.util.UUID;

@RestController
@RequestMapping("/sentiment")
public class ContentSentimentController {
    private final ContentSentimentService contentSentimentService;
    private final AccumulatedContentSentimentModelMapper accumulatedContentSentimentModelMapper;

    public ContentSentimentController(
            ContentSentimentService contentSentimentService,
            AccumulatedContentSentimentModelMapper accumulatedContentSentimentModelMapper
    ) {
        this.contentSentimentService = contentSentimentService;
        this.accumulatedContentSentimentModelMapper = accumulatedContentSentimentModelMapper;
    }

    @GetMapping
    public ResponseEntity<ContentSentimentResponse> getSentimentStats(
            @RequestParam("userId") UUID userId,
            @RequestParam("workflowId") UUID workflowId
    ) {
        return ResponseEntity.ok(
                accumulatedContentSentimentModelMapper.toContentSentimentResponse(
                        contentSentimentService.getByIdAndUserId(userId, workflowId)
                )
        );
    }
}
