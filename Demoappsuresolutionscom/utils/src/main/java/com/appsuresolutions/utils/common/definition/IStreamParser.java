package com.appsuresolutions.utils.common.definition;

public interface IStreamParser<T> {
    void onStarted();
    void onChunkAvailable(byte[] data, int count) throws Throwable;
    void onCompletion() throws Throwable;
    T getResult();
}
