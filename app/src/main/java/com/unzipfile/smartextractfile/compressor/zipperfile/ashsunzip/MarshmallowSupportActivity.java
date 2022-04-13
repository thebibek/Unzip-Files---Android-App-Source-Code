package com.unzipfile.smartextractfile.compressor.zipperfile.ashsunzip;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.constant.MemoryConstants;
import com.unzipfile.smartextractfile.compressor.zipperfile.R;


public abstract class MarshmallowSupportActivity extends AppCompatActivity {
    public Permission mPermission = null;
    private Permission.PermissionCallback mPermissionCallback = null;

    public void requestAppPermissions(@NonNull Permission permission) {
        this.mPermission = permission;
        this.mPermissionCallback = this.mPermission.getPermissionCallback();
        requestAppPermissions(this.mPermission.getRequestedPermissions(), this.mPermission.getRequestCode(), this.mPermission.getPermissionCallback());
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (verifyPermissions(iArr)) {
            this.mPermissionCallback.onPermissionGranted(i);
        } else if (!shouldShowRequestPermissionRationale(strArr)) {
            doNotAskedEnable(i);
        } else {
            showRationalMessage(i);
        }
    }

    private void requestAppPermissions(String[] strArr, int i, @Nullable Permission.PermissionCallback permissionCallback) {
        if (hasPermissions(strArr)) {
            this.mPermissionCallback.onPermissionGranted(i);
        } else if (shouldShowRequestPermissionRationale(strArr)) {
            showRationalMessage(i);
        } else {
            ActivityCompat.requestPermissions(this, strArr, i);
        }
    }

    public boolean hasPermissions(String[] strArr) {
        for (String checkSelfPermission : strArr) {
            if (ContextCompat.checkSelfPermission(this, checkSelfPermission) != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldShowRequestPermissionRationale(String[] strArr) {
        for (String shouldShowRequestPermissionRationale : strArr) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, shouldShowRequestPermissionRationale)) {
                return true;
            }
        }
        return false;
    }

    private boolean verifyPermissions(int[] iArr) {
        if (iArr.length < 1) {
            return false;
        }
        for (int i : iArr) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    private void showRequestPermissionDialog() {
        String rationalDialogMessage = this.mPermission.getRationalDialogMessage();
        String string = getString(R.string.rational_permission_proceed);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage((CharSequence) rationalDialogMessage);
        if (!TextUtils.isEmpty(this.mPermission.getRationalDialogTitle())) {
            builder.setTitle((CharSequence) this.mPermission.getRationalDialogTitle());
        }
        builder.setPositiveButton((CharSequence) string, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCompat.requestPermissions(MarshmallowSupportActivity.this, MarshmallowSupportActivity.this.mPermission.getRequestedPermissions(), MarshmallowSupportActivity.this.mPermission.getRequestCode());
            }
        });
        AlertDialog create = builder.create();
        create.show();
        create.setCancelable(false);
        create.setCanceledOnTouchOutside(false);
    }

    private void showRationalMessage(int i) {
        if (this.mPermission.isShowCustomRationalDialog()) {
            this.mPermissionCallback.onPermissionDenied(i);
        } else {
            showRequestPermissionDialog();
        }
    }

    private void doNotAskedEnable(int i) {
        if (this.mPermission.isShowCustomSettingDialog()) {
            this.mPermissionCallback.onPermissionAccessRemoved(i);
        } else {
            showSettingsPermissionDialog();
        }
    }

    private void showSettingsPermissionDialog() {
        String settingDialogMessage = this.mPermission.getSettingDialogMessage();
        String string = getString(R.string.permission_string_btn);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage((CharSequence) settingDialogMessage);
        if (!TextUtils.isEmpty(this.mPermission.getSettingDialogTitle())) {
            builder.setTitle((CharSequence) this.mPermission.getSettingDialogTitle());
        }
        builder.setPositiveButton((CharSequence) string, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                MarshmallowSupportActivity.this.startSettingActivity();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @SuppressLint("WrongConstant")
    public void startSettingActivity() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(268435456);
        intent.addFlags(MemoryConstants.GB);
        intent.addFlags(8388608);
        startActivity(intent);
    }
}
