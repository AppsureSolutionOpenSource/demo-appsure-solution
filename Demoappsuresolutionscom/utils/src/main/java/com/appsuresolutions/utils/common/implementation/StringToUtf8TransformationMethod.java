package com.appsuresolutions.utils.common.implementation;

import com.appsuresolutions.utils.common.definition.IDataTransformationMethod;

import org.jetbrains.annotations.NotNull;

public class StringToUtf8TransformationMethod implements IDataTransformationMethod<String, byte[]> {
    @Override
    public byte[] process(@NotNull String data) throws Throwable {
        return data.getBytes("UTF-8");
    }
}
