package com.lingo.craft.domain.detection.util;

import lombok.Getter;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

@Getter
public class TikaParserHelper {
    private final BodyContentHandler handler;
    private final Metadata metadata;
    private final ParseContext parseContext;
    private LyricsHandler lyricsHandler;

    public TikaParserHelper() {
        handler = new BodyContentHandler();
        metadata = new Metadata();
        parseContext = new ParseContext();
    }

    public void parse(InputStream inputStream) throws TikaException, IOException, SAXException {
        var parser = new AutoDetectParser();
        parser.parse(inputStream, handler, metadata, parseContext);
    }

    public void parseMP3(InputStream inputStream) throws TikaException, IOException, SAXException {
        var mp3Parser = new Mp3Parser();
        mp3Parser.parse(inputStream, handler, metadata, parseContext);
        lyricsHandler = new LyricsHandler(inputStream, handler);
    }

    public String getLyrics() {
        return lyricsHandler.hasLyrics() ? lyricsHandler.toString() : "";
    }

    public String getContent() {
        return handler.toString();
    }

}
