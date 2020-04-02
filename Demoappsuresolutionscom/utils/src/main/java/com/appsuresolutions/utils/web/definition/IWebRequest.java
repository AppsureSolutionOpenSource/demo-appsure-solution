package com.appsuresolutions.utils.web.definition;

public interface IWebRequest<T> {
    T execute() throws Throwable;
}
