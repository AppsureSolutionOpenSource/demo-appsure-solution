package com.appsuresolutions.utils.biometrics.definition;

import android.content.Context;

import com.appsuresolutions.utils.entrypoint.ICompletionListener;

import org.jetbrains.annotations.NotNull;

public interface IBiometricAuthenticator {
    void execute(@NotNull final ICompletionListener<Boolean> onCompletion);
}
