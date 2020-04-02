package com.appsuresolutions.utils.entrypoint;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.widget.Button;

import androidx.core.app.ActivityCompat;

import com.appsuresolutions.utils.async.AsyncRunner;
import com.appsuresolutions.utils.biometrics.definition.IBiometricAuthenticator;
import com.appsuresolutions.utils.biometrics.implementation.BiometricAuthenticator;
import com.appsuresolutions.utils.common.definition.IStreamParser;
import com.appsuresolutions.utils.common.definition.IUserInputPrompt;
import com.appsuresolutions.utils.common.implementation.AesCiphertextToPlainStringTransformationMethod;
import com.appsuresolutions.utils.common.implementation.Base64ToStringTransformationMethod;
import com.appsuresolutions.utils.common.implementation.StreamParserForJson;
import com.appsuresolutions.utils.common.implementation.StringToAesCiphertextTransformationMethod;
import com.appsuresolutions.utils.common.implementation.StringToBase64TransformationMethod;
import com.appsuresolutions.utils.common.implementation.StringToUtf8TransformationMethod;
import com.appsuresolutions.utils.common.implementation.UserInputPrompt;
import com.appsuresolutions.utils.common.definition.IDataTransformationMethod;
import com.appsuresolutions.utils.common.implementation.Utf8ToStringTransformationMethod;
import com.appsuresolutions.utils.files.definition.IFileManager;
import com.appsuresolutions.utils.files.implementation.FileManager;
import com.appsuresolutions.utils.root.definition.IRootChecker;
import com.appsuresolutions.utils.root.implementation.RootChecker;
import com.appsuresolutions.utils.web.implementation.WebRequestBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LibraryFacade {


    public static List<Employee> GetEmployees() throws Throwable {
        final String encoding = "UTF-8";
        final IStreamParser<JSONObject> parser = new StreamParserForJson(encoding);
        final JSONObject employeesJson = new WebRequestBuilder<JSONObject>()
                .setStreamParser(parser)
                .setEncoding(encoding)
                .setHttps()
                .setDomain("dummy.restapiexample.com")
                .addPath("api")
                .addPath("v1")
                .addPath("employees")
                .setMethodGet()
                .setEncoding(encoding)
                .build()
                .execute();
        final String status = employeesJson.getString("status");
        if (!status.equalsIgnoreCase("success")) {
            throw new RuntimeException("Failed to get employee data.");
        }
        final List<Employee> employeeList = new LinkedList<>();
        final JSONArray employeesJSONArray = employeesJson.getJSONArray("data");
        int employeeDataLength = employeesJSONArray.length();
        for (int i = 0; i < employeeDataLength; i++) {
            JSONObject employeeJson = (JSONObject) employeesJSONArray.get(i);
            Employee employee = new Employee();
            employee.setId(Long.parseLong(employeeJson.getString("id")));
            employee.setName(employeeJson.getString("employee_name"));
            employee.setAge(Integer.parseInt(employeeJson.getString("employee_age")));
            employee.setSalary(Long.parseLong(employeeJson.getString("employee_salary")));
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static Employee UpdateSalary(@NotNull final Employee employee, final int newSalary) throws Throwable {
        final HashMap<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("id", employee.getId() + "");
        parametersMap.put("salary", newSalary + "");
        parametersMap.put("name", employee.getName());
        parametersMap.put("age", employee.getAge() + "");
        final String encoding = "UTF-8";
        final IStreamParser<JSONObject> parser = new StreamParserForJson(encoding);
        final JSONObject updateResult = new WebRequestBuilder<JSONObject>()
                .setStreamParser(parser)
                .setEncoding(encoding)
                .setHttps()
                .setDomain("dummy.restapiexample.com")
                .addPath("api")
                .addPath("v1")
                .addPath("create")
                .setMethodPost()
                .setEncoding(encoding)
                .setData(new JSONObject(parametersMap).toString(2))
                .build()
                .execute();
        final String status = updateResult.getString("status");
        if (!status.equalsIgnoreCase("success")) {
            throw new RuntimeException("Failed to get employee data.");
        }
        final JSONObject employeeJson = updateResult.getJSONObject("data");
        Employee toReturn = new Employee();
        toReturn.setId(Long.parseLong(employeeJson.getString("id")));
        toReturn.setName(employeeJson.getString("name"));
        toReturn.setAge(Integer.parseInt(employeeJson.getString("age")));
        toReturn.setSalary(Long.parseLong(employeeJson.getString("salary")));
        return toReturn;
    }

    public static void GetEmployeesAsync(@NotNull final ICompletionListener<List<Employee>> completionListener) {
        new AsyncRunner<List<Employee>>(completionListener) {
            @Override
            public List<Employee> async() throws Throwable {
                return GetEmployees();
            }
        }.execute();
    }

    public static void UpdateEmployeeSalary(@NotNull final Employee employee, final int newSalary, @NotNull final ICompletionListener<Employee> completionListener) {
        new AsyncRunner<Employee>(completionListener) {
            @Override
            public Employee async() throws Throwable {
                return UpdateSalary(employee, newSalary);
            }
        }.execute();
    }

    public static String GetWorkingDirectory(@NotNull final Context context) {
        final IFileManager fileManager = new FileManager(context);
        try {
            return fileManager.getExternalDataDirectory().getAbsolutePath();
        } catch (Throwable throwable) {
            return "";
        }
    }

    public static List<File> ListFiles(@NotNull final Context context) throws Throwable {
        final IFileManager fileManager = new FileManager(context);
        return fileManager.listDirectory(fileManager.getExternalDataDirectory());
    }

    public static void ListFiles(@NotNull final Context context, @NotNull final ICompletionListener<List<File>> completionListener) {
        new AsyncRunner<List<File>>(completionListener) {
            @Override
            public List<File> async() throws Throwable {
                return ListFiles(context);
            }
        }.execute();
    }

    private static String SaveStringToFile(@NotNull final Context context, @NotNull final String data, @NotNull IDataTransformationMethod<String, byte[]> transformationPolicy) throws Throwable {
        final IFileManager fileManager = new FileManager(context);
        final File externalDir = fileManager.getExternalDataDirectory();
        final File destination = new File(externalDir, "appsure_saved_string.txt");
        fileManager.saveStringToFile(destination, data, transformationPolicy);
        return destination.getAbsolutePath();
    }

    private static void SaveStringToFileAsync(@NotNull final Context context, @NotNull final String data, @NotNull final IDataTransformationMethod<String, byte[]> transformationPolicy, @NotNull final ICompletionListener<String> completionListener) {
        new AsyncRunner<String>(completionListener) {
            @Override
            public String async() throws Throwable {
                return SaveStringToFile(context, data, transformationPolicy);
            }
        }.execute();
    }

    private static String LoadStringFromFile(@NotNull final Context context, @NotNull IDataTransformationMethod<byte[], String> transformationPolicy) throws Throwable {
        final IFileManager fileManager = new FileManager(context);
        final File externalDir = fileManager.getExternalDataDirectory();
        final File source = new File(externalDir, "appsure_saved_string.txt");
        return fileManager.readStringFromFile(source, transformationPolicy);
    }

    private static void LoadStringFromFileAsync(@NotNull final Context context, @NotNull final IDataTransformationMethod<byte[], String> transformationPolicy, @NotNull final ICompletionListener<String> completionListener) {
        new AsyncRunner<String>(completionListener) {
            @Override
            public String async() throws Throwable {
                return LoadStringFromFile(context, transformationPolicy);
            }
        }.execute();
    }

    public static void LoadStringFromFileUsingUtf8Async(@NotNull final Context context, @NotNull final ICompletionListener<String> completionListener) {
        LoadStringFromFileAsync(context, new Utf8ToStringTransformationMethod(), completionListener);
    }

    public static String SaveStringToFileAsUtf8(@NotNull final Context context, @NotNull final String data) throws Throwable {
        return SaveStringToFile(context, data, new StringToUtf8TransformationMethod());
    }

    public static void SaveStringToFileAsUtf8Async(@NotNull final Context context, @NotNull final String data, @NotNull final ICompletionListener<String> completionListener) {
        SaveStringToFileAsync(context, data, new StringToUtf8TransformationMethod(), completionListener);
    }

    public static String EncodeToBase64(@NotNull final String argInput) throws Throwable {
        IDataTransformationMethod<String, String> transformationMethod = new StringToBase64TransformationMethod();
        return transformationMethod.process(argInput);
    }

    public static void EncodeToBase64Async(@NotNull final String argInput, @NotNull final ICompletionListener<String> completionListener) {
        new AsyncRunner<String>(completionListener) {
            @Override
            public String async() throws Throwable {
                return EncodeToBase64(argInput);
            }
        }.execute();
    }

    public static String EncryptAes(@NotNull final String argInput, @NotNull final char[] pin) throws Throwable {
        IDataTransformationMethod<String, String> transformationMethod = new StringToAesCiphertextTransformationMethod(pin);
        return transformationMethod.process(argInput);
    }

    public static void EncryptAesAsync(@NotNull final String argInput, @NotNull final char[] pin, @NotNull final ICompletionListener<String> completionListener) {
        new AsyncRunner<String>(completionListener) {
            @Override
            public String async() throws Throwable {
                return EncryptAes(argInput, pin);
            }
        }.execute();
    }

    public static String DecryptAes(@NotNull final String argInput, @NotNull final char[] pin) throws Throwable {
        IDataTransformationMethod<String, String> transformationMethod = new AesCiphertextToPlainStringTransformationMethod(pin);
        return transformationMethod.process(argInput);
    }

    public static void DecryptAesAsync(@NotNull final String argInput, @NotNull final char[] pin, @NotNull final ICompletionListener<String> completionListener) {
        new AsyncRunner<String>(completionListener) {
            @Override
            public String async() throws Throwable {
                return DecryptAes(argInput, pin);
            }
        }.execute();
    }

    public static RootCheckerResults PerformRootCheck(@NotNull final Context context) throws Throwable {
        final IRootChecker rootChecker = new RootChecker(context);
        return rootChecker.Scan();

    }

    public static void PerformRootCheckAsync(@NotNull final Context context, @NotNull final ICompletionListener<RootCheckerResults> completionListener) {
        new AsyncRunner<RootCheckerResults>(completionListener) {
            @Override
            public RootCheckerResults async() throws Throwable {
                return PerformRootCheck(context);
            }
        }.execute();
    }

    public static void PerformBiometricAuthentication(@NotNull final Activity activity, @NotNull final String title, @NotNull final String cancelText, @NotNull final ICompletionListener<Boolean> completionListener){
        final IBiometricAuthenticator authenticator = new BiometricAuthenticator(activity, title, cancelText);
        authenticator.execute(completionListener);
    }

    public static String DecodeFromBase64(@NotNull final String argInput) throws Throwable {
        IDataTransformationMethod<String, String> transformationMethod = new Base64ToStringTransformationMethod();
        return transformationMethod.process(argInput);
    }

    public static void ChangeButtonColor(@NotNull final Button button, @NotNull String color){
        final int[] colors = {Color.parseColor(color), Color.parseColor(color)};
        final GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(5);
        gd.setStroke(2,colors[0]);
        button.setBackground(gd);
    }

    public static void DecodeFromBase64Async(@NotNull final String argInput, @NotNull final ICompletionListener<String> completionListener) {
        new AsyncRunner<String>(completionListener) {
            @Override
            public String async() throws Throwable {
                return DecodeFromBase64(argInput);
            }
        }.execute();
    }

    public static boolean SaveStringToClipboard(@NotNull final Context context, @NotNull final String argString) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard == null) return false;

        ClipData clip = ClipData.newPlainText(
                "clipboard_label",
                argString);
        clipboard.setPrimaryClip(clip);
        return true;
    }


    public static void ShowUserInputDialog(@NotNull final Context context, @NotNull final String title, @NotNull final ISelectedOptionListener<String> onOptionSelected) {
        IUserInputPrompt prompt = new UserInputPrompt(context);
        prompt.showInputDialog(title, onOptionSelected);
    }



    private static List<String> GetPermissionsList(){
        final LinkedList<String> perms = new LinkedList<>();
        perms.add(Manifest.permission.INTERNET);
        perms.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        perms.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            perms.add(Manifest.permission.USE_BIOMETRIC);
        }
        return perms;
    }

    public static boolean HasPermissions(@NotNull Context context) {
        for (final String permission : GetPermissionsList()) {
            if (context.getApplicationContext().checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void AskPermissions(@NotNull Activity activity, int resultCode) {
        final List<String> permissions = GetPermissionsList();
        String[] perm = new String[permissions.size()];
        perm = permissions.toArray(perm);
        ActivityCompat.requestPermissions(activity, perm, resultCode);
    }


}
