package com.appsuresolutions.utils.biometrics.implementation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.appsuresolutions.utils.biometrics.definition.IBiometricAuthenticator;
import com.appsuresolutions.utils.entrypoint.ICompletionListener;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BiometricAuthenticator implements IBiometricAuthenticator {
    private final Activity context;
    private final String title;
    private final String cancelText;

    public BiometricAuthenticator(@NotNull final Activity context, @NotNull final String title, String cancelText) {
        this.context = context;
        this.title = title;
        this.cancelText = cancelText;
    }


    @Override
    public void execute(@NotNull final ICompletionListener<Boolean> iCompletionListener) {
        try {
            checkPreRequisites(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                executeApi16(context, title, iCompletionListener);
            }

        } catch (Throwable t) {
            iCompletionListener.onError(t);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void executeApi16(@NotNull final Activity context, @NotNull final String title, final ICompletionListener<Boolean> iCompletionListener) {
        Executor executor = Executors.newSingleThreadExecutor();
        final BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(context)
                .setTitle(title)
                .setNegativeButton(cancelText, executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iCompletionListener.onSuccess(false);
                            }
                        });
                    }
                })
                .build();


        biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(final int errorCode, final CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iCompletionListener.onError(new RuntimeException("Failed with error code " + errorCode + ". Reason is " + errString + "."));
                    }
                });

            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iCompletionListener.onSuccess(true);
                    }
                });

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iCompletionListener.onSuccess(false);
                    }
                });
            }
        });
    }

    private static void checkPreRequisites(@NotNull final Context context) {

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.P) {
            throw new RuntimeException("This authentication method requires android P.");
        }
        int resultUseBiometrics = ContextCompat.checkSelfPermission(context, Manifest.permission.USE_BIOMETRIC);

        if (resultUseBiometrics != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Missing permissions. Please add " + Manifest.permission.USE_BIOMETRIC);
        }
    }


}
