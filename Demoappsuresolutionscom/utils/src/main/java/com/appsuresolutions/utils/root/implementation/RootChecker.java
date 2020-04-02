package com.appsuresolutions.utils.root.implementation;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.appsuresolutions.utils.entrypoint.RootCheckerResults;
import com.appsuresolutions.utils.root.definition.IRootChecker;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class RootChecker implements IRootChecker {
    private final Context context;

    public RootChecker(@NotNull final Context context) {
        this.context = context;
    }

    @Override
    public RootCheckerResults Scan() {
        final RootCheckerResults results = new RootCheckerResults();
        results.setGooglePlayServicesInstalled(CheckDeviceHasGooglePlayServices(context));
        results.setBusyBoxDetected(CheckDeviceHasBusyBox());
        results.setSuBinaryDetected(CheckDeviceHasSu());
        final String rootApp = GetRootAppName(context);
        results.setRootAppName(rootApp);
        results.setRootAppDetected(rootApp != null);
        getAppList(results);
        return results;
    }

    private static boolean CheckDeviceHasGooglePlayServices(final @NotNull Context context) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            final PackageInfo info = pm.getPackageInfo("com.android.vending", PackageManager.GET_ACTIVITIES);
            final int isSystemApp = info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM;
            app_installed = (isSystemApp != 0);
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static boolean CheckDeviceHasBusyBox(){
        return CheckHasBinary("busybox");
    }

    private static boolean CheckHasBinary(@NotNull final String theBinary) {
        try {
            final Process p = Runtime.getRuntime().exec(theBinary);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean CheckDeviceHasSu(){
        return CheckHasBinary("su");
    }

    public static String GetRootAppName(final @NotNull Context context){
        final String[] packages = { "eu.chainfire.supersu",
                "eu.chainfire.supersu.pro", "com.koushikdutta.superuser",
                "com.noshufou.android.su", "com.dianxinos.superuser", "com.kingouser.com",
                "com.mueskor.superuser.su" , "org.masteraxe.superuser", "com.yellowes.su" ,
                "com.kingroot.kinguser"};
        PackageManager pm = context.getPackageManager();
        int i, l = packages.length;
        String superuser = null;

        for (i = 0; i < l; i++) {
            try {
                ApplicationInfo info = pm.getApplicationInfo(packages[i], 0);
                PackageInfo info2 = pm.getPackageInfo(packages[i], 0);
                superuser = pm.getApplicationLabel(info).toString() + " "
                        + info2.versionName;
                break;
            } catch (PackageManager.NameNotFoundException e) {
                continue;
            }
        }
        return superuser;
    }

    private void getAppList(@NotNull final RootCheckerResults pendingResults){
        final PackageManager pm = context.getPackageManager();
        final List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        for (int i = 0; i < apps.size(); i++) {
            pendingResults.addPackageName(apps.get(i).packageName);
        }
    }

}
