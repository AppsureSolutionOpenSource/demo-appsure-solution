package com.appsuresolutions.utils.web.implementation;

import android.net.Uri;

import com.appsuresolutions.utils.common.definition.IStreamParser;
import com.appsuresolutions.utils.web.definition.IWebRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

class WebRequest<T> implements IWebRequest<T> {
    private final HashMap<String, String> parameters;
    private final HashMap<String, String> headers;
    private final String method;
    private final String data;
    private final String encoding;
    private final int timeout;
    private final boolean isHttps;
    private final String domain;
    private final List<String> paths;
    private final int port;
    private final IStreamParser<T> streamParser;

    WebRequest(HashMap<String, String> parameters, HashMap<String, String> headers, String method, String domain, String data, String encoding, int timeout, boolean isHttps, List<String> path, int port, IStreamParser<T> streamParser) {
        this.parameters = parameters;
        this.headers = headers;
        this.method = method;
        this.domain = domain;
        this.data = data;
        this.encoding = encoding;
        this.timeout = timeout;
        this.isHttps = isHttps;
        this.paths = path;
        this.port = port;
        this.streamParser = streamParser;
    }


    private void writeBody(HttpURLConnection urlConnection) throws IOException {
        if (!method.equalsIgnoreCase("POST")) {
            return;
        }
        urlConnection.setDoOutput(true);
        urlConnection.getOutputStream().write(data.getBytes(encoding));
        urlConnection.getOutputStream().flush();
        urlConnection.getOutputStream().close();
    }

    private void addHeaders(HttpURLConnection urlConnection) {
        for (final String headerName : headers.keySet()) {
            urlConnection.addRequestProperty(headerName, headers.get(headerName));
        }
    }

    private URL getUrl() throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(isHttps ? "https" : "http");
        builder.encodedAuthority(domain + ":" + port);
        if (!paths.isEmpty()) {
            for (final String path : paths) {
                builder.appendPath(path);
            }
        }
        if (method.equalsIgnoreCase("GET") && !parameters.isEmpty()) {
            for (final String parameter : parameters.keySet()) {
                builder.appendQueryParameter(parameter, parameters.get(parameter));
            }
        }
        return new URL(builder.build().toString());
    }


    public static void safeClose(HttpURLConnection urlConnection) {
        if (urlConnection == null) {
            return;
        }
        urlConnection.disconnect();
    }


    @Override
    public T execute() throws Throwable {
        HttpURLConnection urlConnection = null;
        try {
            streamParser.onStarted();
            URL url = getUrl();
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(timeout);
            urlConnection.setReadTimeout(timeout);
            urlConnection.setUseCaches(true);
            urlConnection.addRequestProperty("Accept-Charset", encoding);
            urlConnection.setRequestProperty("User-Agent", "Powered by appsure-solution.com.");
            addHeaders(urlConnection);
            writeBody(urlConnection);
            byte[] buffer = new byte[1024];
            final InputStream inputStream = urlConnection.getInputStream();
            while (true) {
                int iRead = inputStream.read(buffer);
                if (iRead == -1) {
                    break;
                }
                streamParser.onChunkAvailable(buffer, iRead);
            }
            streamParser.onCompletion();
            return streamParser.getResult();
        } finally {
            safeClose(urlConnection);
        }
    }


}
