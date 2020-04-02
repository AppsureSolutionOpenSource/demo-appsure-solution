package com.appsuresolutions.utils.files.definition;

import com.appsuresolutions.utils.common.definition.IDataTransformationMethod;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public interface IFileManager {
    File getExternalDataDirectory() throws Throwable;
    File createTimestampedFile(@NotNull final File sourceDir, @NotNull final String prefix, @NotNull String extension) throws Throwable;
    List<File> listDirectory(@NotNull final File directory) throws Throwable;
    void saveStringToFile(@NotNull final File file, @NotNull final String content, @NotNull final IDataTransformationMethod<String,byte[]> dataTransformationPolicy) throws Throwable;
    String readStringFromFile(@NotNull final File file, final IDataTransformationMethod<byte[],String> dataTransformationPolicy) throws Throwable;
}
