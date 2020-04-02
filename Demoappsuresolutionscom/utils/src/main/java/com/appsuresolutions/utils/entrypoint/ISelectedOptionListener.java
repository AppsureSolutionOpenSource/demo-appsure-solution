package com.appsuresolutions.utils.entrypoint;

public interface ISelectedOptionListener<T> {
    void onAgreed(T result);
    void onCanceled();
}
