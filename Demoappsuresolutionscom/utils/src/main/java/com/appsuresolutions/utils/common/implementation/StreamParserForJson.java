package com.appsuresolutions.utils.common.implementation;

import com.appsuresolutions.utils.common.definition.IStreamParser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class StreamParserForJson implements IStreamParser<JSONObject> {
    private final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
    private final String encoding;
    private JSONObject result;

    public StreamParserForJson(final String encoding) {
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
        result = new JSONObject(new String(dataStream.toByteArray(),encoding));
        dataStream.reset();
    }

    @Override
    public JSONObject getResult() {
        return result;
    }
}
