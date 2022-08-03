package io.paywell.official_theme.extras;

import android.graphics.drawable.Drawable;

public class AppObject {
    private String appName;
    private String packageName;
    private Drawable appImage;

    public AppObject(String appName, String packageName, Drawable appImage) {
        this.appName = appName;
        this.packageName = packageName;
        this.appImage = appImage;
    }

    public AppObject() {
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getAppImage() {
        return appImage;
    }
}
