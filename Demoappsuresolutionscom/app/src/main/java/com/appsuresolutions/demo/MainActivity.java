package com.appsuresolutions.demo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.appsuresolutions.utils.entrypoint.*;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView description;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        description = findViewById(R.id.textView);
        button = findViewById(R.id.buttonRunTest);
        button.setOnClickListener(this);

    }

    private void showTests() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle(getString(R.string.i18n_run_test));

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, getResources().getStringArray(R.array.tests));

        builderSingle.setNegativeButton(getString(R.string.i18n_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                setUiMessage("User selected option " + strName + ".");
                appendUiMessage("==============");
                runTest(which);
            }
        });
        builderSingle.show();
    }

    private void runTest(int which) {
        if (!LibraryFacade.HasPermissions(this)) {
            LibraryFacade.AskPermissions(this, 9999);
            return;
        }
        lockUserInput();
        if (which == 0) {
            runGetTest();
        }
        if (which == 1) {
            runPostTest();
        }
        if (which == 2) {
            runListFiles();
        }
        if (which == 3) {
            runSaveStringToFile();
        }
        if (which == 4) {
            runLoadStringFromFile();
        }
        if (which == 5) {
            runBase64Encode();
        }
        if (which == 6) {
            runBase64Decode();
        }
        if (which == 7) {
            runAesEncrypt();
        }
        if (which == 8) {
            runAesDecrypt();
        }
        if (which == 9) {
            runRootChecker();
        }
        if(which == 10){
            runColorChanger();
        }
        if(which == 11){
            runBiometricAuthentication();
        }
    }




    private void lockUserInput() {
        button.setClickable(false);
    }

    private void unlockUserInput() {
        button.setClickable(true);
    }

    private void runGetTest() {
        LibraryFacade.GetEmployeesAsync(new ICompletionListener<List<Employee>>() {
            @Override
            public void onError(Throwable t) {
                appendUiMessage("An error occurred: " + t.toString());
                unlockUserInput();
            }

            @Override
            public void onSuccess(List<Employee> data) {
                appendUiMessage("Webservice returned " + data.size() + " employees using a get request.");
                for (final Employee employee : data
                ) {
                    appendUiMessage("===================");
                    appendUiMessage("name: " + employee.getName());
                    appendUiMessage("age: " + employee.getAge());
                    appendUiMessage("salary: " + employee.getSalary());
                    appendUiMessage("id: " + employee.getId());
                }
                unlockUserInput();
            }
        });
    }

    private void runPostTest() {
        Employee employee = new Employee();
        employee.setSalary(3000);
        employee.setId(1);
        employee.setAge(30);
        employee.setName("appsure ceo");
        LibraryFacade.UpdateEmployeeSalary(employee, 4000, new ICompletionListener<Employee>() {
            @Override
            public void onError(Throwable t) {
                appendUiMessage("An error occurred: " + t.toString());
                unlockUserInput();
            }

            @Override
            public void onSuccess(Employee data) {
                appendUiMessage("Employee salary was updated using a post request.");
                appendUiMessage("===================");
                appendUiMessage("name: " + data.getName());
                appendUiMessage("age: " + data.getAge());
                appendUiMessage("salary: " + data.getSalary());
                appendUiMessage("id: " + data.getId());
                unlockUserInput();
            }
        });
    }

    private void runListFiles() {
        LibraryFacade.ListFiles(this, new ICompletionListener<List<File>>() {
            @Override
            public void onError(Throwable t) {
                appendUiMessage("An error occurred: " + t.toString());
                unlockUserInput();
            }

            @Override
            public void onSuccess(List<File> data) {
                appendUiMessage("Folder " + LibraryFacade.GetWorkingDirectory(MainActivity.this) + " contains " + data.size() + " files.");
                for (final File file : data
                ) {
                    appendUiMessage("===================");
                    appendUiMessage("name: " + file.getName());
                    appendUiMessage("isDir: " + file.isDirectory());
                    if (file.isFile()) {
                        appendUiMessage("size (bytes):" + file.length());
                    }
                }
                unlockUserInput();
            }
        });
    }

    private void runSaveStringToFile() {
        LibraryFacade.ShowUserInputDialog(this, "Enter string to save", new ISelectedOptionListener<String>() {
            @Override
            public void onAgreed(final String result) {
                LibraryFacade.SaveStringToFileAsUtf8Async(MainActivity.this, result, new ICompletionListener<String>() {
                    @Override
                    public void onError(Throwable t) {
                        appendUiMessage("An error occurred: " + t.toString());
                        unlockUserInput();
                    }

                    @Override
                    public void onSuccess(String data) {
                        appendUiMessage("String " + result + " was saved to file " + data + " .");
                        unlockUserInput();
                    }
                });
            }

            @Override
            public void onCanceled() {
                unlockUserInput();
            }
        });
    }

    private void runBase64Decode() {
        LibraryFacade.ShowUserInputDialog(this, "Enter string for base64 decoding test", new ISelectedOptionListener<String>() {
            @Override
            public void onAgreed(final String result) {
                LibraryFacade.DecodeFromBase64Async(result, new ICompletionListener<String>() {
                    @Override
                    public void onError(Throwable t) {
                        appendUiMessage("An error occurred: " + t.toString());
                        unlockUserInput();
                    }

                    @Override
                    public void onSuccess(String data) {
                        appendUiMessage("String value for " + result + " is " + data + " .");
                        unlockUserInput();
                    }
                });
            }

            @Override
            public void onCanceled() {
                unlockUserInput();
            }
        });
    }

    private void runBase64Encode() {
        LibraryFacade.ShowUserInputDialog(this, "Enter string for base64 encoding test", new ISelectedOptionListener<String>() {
            @Override
            public void onAgreed(final String result) {
                LibraryFacade.EncodeToBase64Async(result, new ICompletionListener<String>() {
                    @Override
                    public void onError(Throwable t) {
                        appendUiMessage("An error occurred: " + t.toString());
                        unlockUserInput();
                    }

                    @Override
                    public void onSuccess(String data) {
                        appendUiMessage("Base64 value for " + result + " is " + data + " . Result was copied to clipboard.");
                        LibraryFacade.SaveStringToClipboard(MainActivity.this, data);
                        unlockUserInput();
                    }
                });
            }

            @Override
            public void onCanceled() {
                unlockUserInput();
            }
        });
    }

    private void runAesEncrypt() {
        final char pin[] = new char[]{'p', 'i', 'n'};
        LibraryFacade.ShowUserInputDialog(this, "Enter string for aes encryption test", new ISelectedOptionListener<String>() {
            @Override
            public void onAgreed(final String result) {
                LibraryFacade.EncryptAesAsync(result, pin, new ICompletionListener<String>() {
                    @Override
                    public void onError(Throwable t) {
                        appendUiMessage("An error occurred: " + t.toString());
                        unlockUserInput();
                    }

                    @Override
                    public void onSuccess(String data) {
                        appendUiMessage("Encrypted value for " + result + " is " + data + " . Result was copied to clipboard.");
                        LibraryFacade.SaveStringToClipboard(MainActivity.this, data);
                        unlockUserInput();
                    }
                });
            }

            @Override
            public void onCanceled() {
                unlockUserInput();
            }
        });
    }

    private void runAesDecrypt() {
        final char pin[] = new char[]{'p', 'i', 'n'};
        LibraryFacade.ShowUserInputDialog(this, "Enter string for aes decryption test", new ISelectedOptionListener<String>() {
            @Override
            public void onAgreed(final String result) {
                LibraryFacade.DecryptAesAsync(result, pin, new ICompletionListener<String>() {
                    @Override
                    public void onError(Throwable t) {
                        appendUiMessage("An error occurred: " + t.toString());
                        unlockUserInput();
                    }

                    @Override
                    public void onSuccess(String data) {
                        appendUiMessage("Decrypted value for " + result + " is " + data + " . Result was copied to clipboard.");
                        LibraryFacade.SaveStringToClipboard(MainActivity.this, data);
                        unlockUserInput();
                    }
                });
            }

            @Override
            public void onCanceled() {
                unlockUserInput();
            }
        });
    }


    private void runLoadStringFromFile() {
        LibraryFacade.LoadStringFromFileUsingUtf8Async(this, new ICompletionListener<String>() {
            @Override
            public void onError(Throwable t) {
                appendUiMessage("An error occurred: " + t.toString());
                unlockUserInput();
            }

            @Override
            public void onSuccess(String data) {
                appendUiMessage("Saved string was " + data + " .");
                unlockUserInput();
            }
        });
    }

    private void runRootChecker() {
        LibraryFacade.PerformRootCheckAsync(this, new ICompletionListener<RootCheckerResults>() {
            @Override
            public void onError(Throwable t) {
                appendUiMessage("An error occurred: " + t.toString());
                unlockUserInput();
            }

            @Override
            public void onSuccess(RootCheckerResults data) {
                appendUiMessage("Google Play Services available: " + data.isGooglePlayServicesInstalled() + " .");
                appendUiMessage("Su binary detected: " + data.isSuBinaryDetected() + " .");
                appendUiMessage("BusyBox binary detected: " + data.isBusyBoxDetected() + " .");
                appendUiMessage("Root app detected: "+ data.isRootAppDetected());
                appendUiMessage("Root app name: "+ data.getRootAppName());
                appendUiMessage("Installed packages: ");
                for(final String packageName: data.getPackageNames()){
                    appendUiMessage(" [-] "+packageName);
                }
                unlockUserInput();
            }
        });
    }

    private void runColorChanger() {
        LibraryFacade.ChangeButtonColor(button, "blue");
        unlockUserInput();
    }

    private void runBiometricAuthentication() {
        LibraryFacade.PerformBiometricAuthentication(this, "Biometric auth", "Cancel", new ICompletionListener<Boolean>() {
            @Override
            public void onError(Throwable t) {
                appendUiMessage("An error occurred: " + t.toString());
                unlockUserInput();
            }

            @Override
            public void onSuccess(Boolean data) {
                appendUiMessage("User authentication status: " + (data?"Success .":"Failed ."));
                unlockUserInput();
            }
        });
    }



    private void appendUiMessage(final String message) {
        description.append("\n");
        description.append(message);
    }

    private void setUiMessage(final String message) {
        description.setText(message);
    }


    @Override
    public void onClick(View v) {
        showTests();
    }
}
