package com.appsuresolutions.utils.common.implementation;

import android.util.Base64;

import com.appsuresolutions.utils.common.definition.IDataTransformationMethod;

import org.jetbrains.annotations.NotNull;

public class StringToBase64TransformationMethod implements IDataTransformationMethod<String,String> {
    @Override
    public String process(@NotNull String data) throws Throwable {
        return Base64.encodeToString(data.getBytes("UTF-8"),Base64.NO_WRAP);
    }
}
