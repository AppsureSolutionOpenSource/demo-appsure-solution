package com.appsuresolutions.utils.common.definition;

import com.appsuresolutions.utils.entrypoint.ISelectedOptionListener;

import org.jetbrains.annotations.NotNull;

public interface IUserInputPrompt {
    void showInputDialog(@NotNull String title, @NotNull ISelectedOptionListener<String> optionListener);
}
