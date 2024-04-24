package com.lingo.craft.domain.detection.util;

import com.lingo.craft.utils.LingoHelper;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.langdetect.optimaize.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TikaParserHelper {
    private final Tika tika;
    private final BodyContentHandler handler;
    private final Metadata metadata;
    private final ParseContext parseContext;
    private final LanguageDetector languageDetector;

    public TikaParserHelper() throws IOException {
        this.tika = new Tika();
        handler = new BodyContentHandler();
        metadata = new Metadata();
        parseContext = new ParseContext();
        languageDetector = OptimaizeLangDetector.getDefaultLanguageDetector();
        languageDetector.loadModels();
    }

    public String translate(String text, String targetLanguage) {
        return tika.translate(text, targetLanguage);
    }

    public LanguageResult detectLanguage(String text) {
        return languageDetector.detect(text);
    }



    public void parse(InputStream inputStream) throws TikaException, IOException, SAXException {
        var parser = new AutoDetectParser();
        parser.parse(inputStream, handler, metadata, parseContext);
    }

    public Map<String, String> readMetadata() {
        var metadataNames = Arrays.stream(metadata.names())
                .filter(LingoHelper::isMetadataKeyAllowed)
                .toList();

        return metadataNames.stream()
                .collect(
                        Collectors.toMap(
                                name -> name, metadata::get
                        )
                );
    }

    public String getContent() {
        return handler.toString();
    }
}
