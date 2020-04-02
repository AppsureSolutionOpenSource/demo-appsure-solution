package com.appsuresolutions.utils.common.implementation;

import android.util.Base64;

import com.appsuresolutions.utils.common.definition.IDataTransformationMethod;

import org.jetbrains.annotations.NotNull;

public class Base64ToStringTransformationMethod implements IDataTransformationMethod<String,String> {
    @Override
    public String process(@NotNull String data) throws Throwable {
        return new String(Base64.decode(data, Base64.NO_WRAP));
    }
}
