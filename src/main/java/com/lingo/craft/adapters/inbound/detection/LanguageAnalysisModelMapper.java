package com.lingo.craft.adapters.inbound.detection;

import com.lingo.craft.domain.detection.model.LanguageAnalysisModel;
import com.diabolocom.release.openapi.model.LanguageAnalysisRequest;
import com.diabolocom.release.openapi.model.LanguageAnalysisResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageAnalysisModelMapper {
    LanguageAnalysisModel toLanguageAnalysisModel(LanguageAnalysisRequest request);
    LanguageAnalysisResponse toLanguageAnalysisResponse(LanguageAnalysisModel model);
}
