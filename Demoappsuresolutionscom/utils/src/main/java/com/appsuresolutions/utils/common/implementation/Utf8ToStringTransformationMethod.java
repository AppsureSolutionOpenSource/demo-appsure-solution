package com.appsuresolutions.utils.common.implementation;

import com.appsuresolutions.utils.common.definition.IDataTransformationMethod;

import org.jetbrains.annotations.NotNull;

public class Utf8ToStringTransformationMethod implements IDataTransformationMethod<byte[], String> {
    @Override
    public String process(@NotNull byte[] data) throws Throwable {
        return new String(data,"UTF-8");
    }
}
