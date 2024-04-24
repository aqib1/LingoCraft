package com.lingo.craft.adapters.inbound.detection;

import com.lingo.craft.domain.detection.model.LanguageDetectionModel;
import com.diabolocom.release.openapi.model.LanguageDetectionRequest;
import com.diabolocom.release.openapi.model.LanguageDetectionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageDetectionModelMapper {
    LanguageDetectionModel languageDetectionModel(LanguageDetectionRequest request);
    LanguageDetectionResponse languageDetectionResponse(LanguageDetectionModel model);
}
