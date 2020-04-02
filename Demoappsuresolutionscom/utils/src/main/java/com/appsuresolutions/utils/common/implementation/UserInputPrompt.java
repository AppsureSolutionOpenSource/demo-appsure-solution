package com.appsuresolutions.utils.common.implementation;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.appsuresolutions.utils.entrypoint.ISelectedOptionListener;
import com.appsuresolutions.utils.common.definition.IUserInputPrompt;

import org.jetbrains.annotations.NotNull;

public class UserInputPrompt implements IUserInputPrompt {
    private final Context context;

    public UserInputPrompt(@NotNull final Context context) {
        this.context = context;
    }

    @Override
    public void showInputDialog(@NotNull String title, @NotNull final ISelectedOptionListener<String> optionListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String option = input.getText().toString();
                optionListener.onAgreed(option);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                optionListener.onCanceled();
            }
        });

        builder.show();
    }
}
