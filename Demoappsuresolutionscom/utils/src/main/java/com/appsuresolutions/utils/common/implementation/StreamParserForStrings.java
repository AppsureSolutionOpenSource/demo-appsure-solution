package com.appsuresolutions.utils.common.implementation;

import com.appsuresolutions.utils.common.definition.IStreamParser;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

public class StreamParserForStrings implements IStreamParser<String> {
    private final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
    private final String encoding;
    private String result;

    public StreamParserForStrings(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public void onStarted() {
        dataStream.reset();
    }

    @Override
    public void onChunkAvailable(byte[] data, int count) throws Throwable {
        dataStream.write(data,0, count);
    }

    @Override
    public void onCompletion() throws Throwable{
        result = new String(dataStream.toByteArray(), Charset.forName(encoding));
    }

    @Override
    public String getResult() {
        return result;
    }
}
