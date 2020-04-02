package com.appsuresolutions.utils.entrypoint;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class RootCheckerResults {
    private final List<String> packageNames;
    private boolean googlePlayServicesInstalled;
    private boolean suBinaryDetected;
    private boolean busyBoxDetected;
    private String rootAppName;
    private boolean rootAppDetected;

    public RootCheckerResults(){
        packageNames = new LinkedList<>();
        googlePlayServicesInstalled = false;
        rootAppDetected = false;
        busyBoxDetected = false;
        suBinaryDetected = false;
    }

    public List<String> getPackageNames() {
        return packageNames;
    }

    public void addPackageName(@NotNull final String packageName){
        packageNames.add(packageName);
    }

    public boolean isGooglePlayServicesInstalled() {
        return googlePlayServicesInstalled;
    }

    public void setGooglePlayServicesInstalled(boolean googlePlayServicesInstalled) {
        this.googlePlayServicesInstalled = googlePlayServicesInstalled;
    }

    public boolean isSuBinaryDetected() {
        return suBinaryDetected;
    }

    public void setSuBinaryDetected(boolean suBinaryDetected) {
        this.suBinaryDetected = suBinaryDetected;
    }

    public boolean isBusyBoxDetected() {
        return busyBoxDetected;
    }

    public void setBusyBoxDetected(boolean busyBoxDetected) {
        this.busyBoxDetected = busyBoxDetected;
    }

    public String getRootAppName() {
        return rootAppName;
    }

    public void setRootAppName(String rootAppName) {
        this.rootAppName = rootAppName;
    }

    public boolean isRootAppDetected() {
        return rootAppDetected;
    }

    public void setRootAppDetected(boolean rootAppDetected) {
        this.rootAppDetected = rootAppDetected;
    }
}
