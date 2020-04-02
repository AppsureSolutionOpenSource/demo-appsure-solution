package com.appsuresolutions.utils.common.definition;

import org.jetbrains.annotations.NotNull;

public interface IDataTransformationMethod<TInput, TOutput> {
    TOutput process(@NotNull TInput data) throws Throwable;
}
