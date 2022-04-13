package com.compressor.extractor.zipers.compression;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.blankj.utilcode.util.ActivityUtils;
import com.unzipfile.smartextractfile.compressor.zipperfile.R;
import com.unzipfile.smartextractfile.compressor.zipperfile.ashsunzip.AllInOneCompress;

import java.io.File;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

public class ZipAsynkPerform extends AsyncTask<Void, Void, String> {
    @SuppressLint({"StaticFieldLeak"})
    public Context context;
    private ProgressDialog dialog;
    private String distination_path;
    public SharedPreferences.Editor editor;
    private String password;
    private String source_path;

    public ZipAsynkPerform(Context context2, String str, String str2, String str3) {
        this.context = context2;
        this.source_path = str;
        this.distination_path = str2;
        this.password = str3;
    }
    public void onPreExecute() {
        showProgressDialog();
    }

    public String doInBackground(Void... voidArr) {
        try {
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setCompressionMethod(8);
            zipParameters.setCompressionLevel(5);
            if (this.password.length() > 0) {
                zipParameters.setEncryptFiles(true);
                zipParameters.setEncryptionMethod(99);
                zipParameters.setAesKeyStrength(3);
                zipParameters.setPassword(this.password);
            }
            ZipFile zipFile = new ZipFile(this.distination_path);
            zipFile.getProgressMonitor().getPercentDone();
            zipFile.getProgressMonitor().endProgressMonitorSuccess();
            File file = new File(this.source_path);
            if (file.isFile()) {
                zipFile.addFile(file, zipParameters);
                return null;
            } else if (!file.isDirectory()) {
                return null;
            } else {
                zipFile.addFolder(file, zipParameters);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onPostExecute(String str) {
        dismissProgressDialog();
        showDialogForOpenAllCompressed_Files();
    }

    private void showProgressDialog() {
        if (this.dialog == null) {
            this.dialog = new ProgressDialog(this.context);
            this.dialog.setMessage(this.context.getText(R.string.progress_message));
            this.dialog.setCancelable(false);
        }
        this.dialog.show();
    }

    private void dismissProgressDialog() {
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    @SuppressLint({"CommitPrefEdits"})
    private void showDialogForOpenAllCompressed_Files() {
        this.editor = this.context.getSharedPreferences("MyPrefsFile", 0).edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMessage("File is Successfully Extracted.");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ZipAsynkPerform.this.editor.clear();
                ZipAsynkPerform.this.editor.putInt("idName", 1);
                ZipAsynkPerform.this.editor.apply();
                ActivityUtils.startActivity(new Intent(ZipAsynkPerform.this.context, AllInOneCompress.class).putExtra("option", "compress"));
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }
}
