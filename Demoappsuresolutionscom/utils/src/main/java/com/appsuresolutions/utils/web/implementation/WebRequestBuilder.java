package com.appsuresolutions.utils.web.implementation;

import com.appsuresolutions.utils.common.definition.IStreamParser;
import com.appsuresolutions.utils.web.definition.IWebRequest;
import com.appsuresolutions.utils.web.definition.IWebRequestBuilder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class WebRequestBuilder<T> implements IWebRequestBuilder<T> {
    private final HashMap<String, String> parameters;
    private final HashMap<String, String> headers;
    private String method;
    private String data;
    private String encoding;
    private int timeout;
    private boolean isHttps;
    private String domain;
    private List<String> paths;
    private int port;
    private IStreamParser<T> streamParser;

    public WebRequestBuilder() {
        headers = new LinkedHashMap<>();
        parameters = new LinkedHashMap<>();
        method = "GET";
        isHttps = true;
        data = null;
        encoding = "UTF-8";
        timeout = 15000;
        domain = null;
        paths = new LinkedList<>();
        port = 443;
    }


    @Override
    public IWebRequestBuilder<T> addHeader(String argHeaderName, String argHeaderValue) {
        headers.put(argHeaderName, argHeaderValue);
        return this;
    }

    @Override
    public IWebRequestBuilder<T> addParameter(String argHeaderName, String argHeaderValue) {
        parameters.put(argHeaderName, argHeaderValue);
        return this;
    }

    @Override
    public IWebRequestBuilder<T> setMethodGet() {
        method = "GET";
        return this;
    }

    @Override
    public IWebRequestBuilder<T> setMethodPost() {
        method = "POST";
        return this;
    }

    @Override
    public IWebRequestBuilder<T> setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    @Override
    public IWebRequestBuilder<T> addPath(String path) {
        this.paths.add(path);
        return this;
    }

    @Override
    public IWebRequestBuilder<T> setPort(int port) {
        this.port = port;
        return this;
    }

    @Override
    public IWebRequestBuilder<T> setHttp() {
        this.isHttps = false;
        return this;
    }

    @Override
    public IWebRequestBuilder<T> setHttps() {
        this.isHttps = true;
        return this;
    }


    @Override
    public IWebRequestBuilder<T> setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public IWebRequestBuilder<T> setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    @Override
    public IWebRequestBuilder<T> setTimeout(int millis) {
        this.timeout = millis;
        return this;
    }

    @Override
    public IWebRequestBuilder<T> setStreamParser(IStreamParser<T> streamParser) {
        this.streamParser = streamParser;
        return this;
    }


    @Override
    public IWebRequest<T> build() {
        return new WebRequest<T>(parameters, headers, method, domain, data, encoding, timeout, isHttps, paths, port, streamParser);
    }
}
