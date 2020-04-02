package com.appsuresolutions.utils.files.implementation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.appsuresolutions.utils.common.definition.IDataTransformationMethod;
import com.appsuresolutions.utils.files.definition.IFileManager;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager implements IFileManager {
    private final Context context;
    public FileManager(@NotNull final Context context){
        this.context = context;
    }
    @Override
    public File getExternalDataDirectory() throws Throwable {
        checkReadWriteStorageAllowed(context);
        return context.getExternalFilesDir(null);

    }

    @Override
    public File createTimestampedFile(@NotNull File sourceDir, @NotNull String prefix, @NotNull String extension) throws Throwable {
        checkReadWriteStorageAllowed(context);
        File file = new File(sourceDir, prefix + "_" + System.currentTimeMillis() + "." + extension);
        if (file.exists()) {
            throw new RuntimeException("File " + file.getAbsolutePath() + " already exists.");
        }
        boolean created = file.createNewFile();
        if (!created) {
            throw new RuntimeException("File " + file.getAbsolutePath() + " could not be created.");
        }
        return file;
    }

    @Override
    public List<File> listDirectory(@NotNull final File dir) throws Throwable {
        checkReadWriteStorageAllowed(context);
        final File[] files = dir.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(files);
    }

    private static void checkReadWriteStorageAllowed(@NotNull final Context context) {
        int resultRead   = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int resultWrite  = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (resultRead != PackageManager.PERMISSION_GRANTED || resultWrite != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Missing permissions. Please add " + Manifest.permission.WRITE_EXTERNAL_STORAGE + " and " + Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
     }

    @Override
    public void saveStringToFile(@NotNull final File file, @NotNull final String content, @NotNull final IDataTransformationMethod<String,byte[]> dataTransformationPolicy) throws Throwable {
        checkReadWriteStorageAllowed(context);
        byte[] processed = dataTransformationPolicy.process(content);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(processed);
        } finally {
            noThrowClose(fileOutputStream);
        }
    }


    @Override
    public String readStringFromFile(@NotNull final File path, @NotNull final IDataTransformationMethod<byte[], String> dataTransformationPolicy) throws Throwable {
        checkReadWriteStorageAllowed(context);
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        try {
            fileInputStream = new FileInputStream(path);
            while (true) {
                int readCount = fileInputStream.read(buff);
                if (readCount == -1) {
                    break;
                }
                content.write(buff, 0, readCount);
            }
        } finally {
            noThrowClose(fileInputStream);
        }

        return dataTransformationPolicy.process(content.toByteArray());


    }

    private void noThrowClose(FileOutputStream fileOutputStream) {
        try {
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Throwable t) {
        }
    }

    private void noThrowClose(FileInputStream fileInputStream) {
        try {
            fileInputStream.close();
        } catch (Throwable t) {
        }
    }


}
