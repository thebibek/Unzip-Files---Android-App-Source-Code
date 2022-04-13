package com.unzipfile.smartextractfile.compressor.zipperfile.ashsunzip;

import androidx.annotation.NonNull;

public class Permission {
    private PermissionCallback mPermissionCallback;
    private String rationalDialogMessage;
    private String rationalDialogTitle;
    private int requestCode;
    private String[] requestedPermissions;
    private String settingDialogMessage;
    private String settingDialogTitle;
    private boolean showCustomRationalDialog;
    private boolean showCustomSettingDialog;

    public interface PermissionCallback {
        void onPermissionAccessRemoved(int i);

        void onPermissionDenied(int i);

        void onPermissionGranted(int i);
    }

    private Permission(PermissionBuilder permissionBuilder) {
        this.mPermissionCallback = null;
        this.showCustomRationalDialog = true;
        this.showCustomSettingDialog = true;
        this.requestedPermissions = permissionBuilder.requestedPermissions;
        this.requestCode = permissionBuilder.requestCode;
        this.mPermissionCallback = permissionBuilder.mPermissionCallback;
        this.showCustomRationalDialog = permissionBuilder.showCustomRationDialog;
        if (!this.showCustomRationalDialog) {
            this.rationalDialogMessage = permissionBuilder.rationalDialogMessage;
            this.rationalDialogTitle = permissionBuilder.rationalDialogTitle;
        }
        this.showCustomSettingDialog = permissionBuilder.showCustomSettingDialog;
        if (!this.showCustomSettingDialog) {
            this.settingDialogMessage = permissionBuilder.settingDialogMessage;
            this.settingDialogTitle = permissionBuilder.settingDialogTitle;
        }
    }

    public String[] getRequestedPermissions() {
        return this.requestedPermissions;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public PermissionCallback getPermissionCallback() {
        return this.mPermissionCallback;
    }

    public boolean isShowCustomRationalDialog() {
        return this.showCustomRationalDialog;
    }

    public String getRationalDialogTitle() {
        return this.rationalDialogTitle;
    }

    public String getRationalDialogMessage() {
        return this.rationalDialogMessage;
    }

    public boolean isShowCustomSettingDialog() {
        return this.showCustomSettingDialog;
    }

    public String getSettingDialogTitle() {
        return this.settingDialogTitle;
    }

    public String getSettingDialogMessage() {
        return this.settingDialogMessage;
    }

    public static class PermissionBuilder {
        public PermissionCallback mPermissionCallback = null;
        public String rationalDialogMessage;
        public String rationalDialogTitle;
        public int requestCode;
        public String[] requestedPermissions;
        public String settingDialogMessage;
        public String settingDialogTitle;
        public boolean showCustomRationDialog = true;
        public boolean showCustomSettingDialog = true;
        public PermissionBuilder(String[] strArr, int i, @NonNull PermissionCallback permissionCallback) {
            this.requestedPermissions = strArr;
            this.requestCode = i;
            this.mPermissionCallback = permissionCallback;
        }
        public Permission build() {
            return new Permission(this);
        }
    }
}
