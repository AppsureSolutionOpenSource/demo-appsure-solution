package com.appsuresolutions.utils.entrypoint;

public interface ICompletionListener<T> {
    void onError(final Throwable t);
    void onSuccess(final T data);
}
