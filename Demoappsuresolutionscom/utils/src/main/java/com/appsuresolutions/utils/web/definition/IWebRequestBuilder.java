package com.appsuresolutions.utils.web.definition;

import com.appsuresolutions.utils.common.definition.IStreamParser;

import java.nio.charset.StandardCharsets;

public interface IWebRequestBuilder<T> {
    IWebRequestBuilder<T> addHeader(String argHeaderName, String argHeaderValue);
    IWebRequestBuilder<T> addParameter(String argParameterName, String argParameterValue);
    IWebRequestBuilder<T> setMethodGet();
    IWebRequestBuilder<T> setMethodPost();
    IWebRequestBuilder<T> setDomain(String domain);
    IWebRequestBuilder<T> addPath(String path);
    IWebRequestBuilder<T> setPort(int port);
    IWebRequestBuilder<T> setHttp();
    IWebRequestBuilder<T> setHttps();
    IWebRequestBuilder<T> setData(String data);
    IWebRequestBuilder<T> setEncoding(String encoding);
    IWebRequestBuilder<T> setTimeout(int millis);
    IWebRequestBuilder<T> setStreamParser(IStreamParser<T> streamParser);
    IWebRequest<T> build();
}
