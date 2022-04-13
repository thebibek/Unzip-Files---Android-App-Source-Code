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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class TarGzAsynkPerform extends AsyncTask<Void, Void, String> {
    @SuppressLint({"StaticFieldLeak"})
    public Context cont;
    private ProgressDialog dialog;
    private String distinaton_path;

    public SharedPreferences.Editor editor;
    private String source_path;

    public TarGzAsynkPerform(Context context, String str, String str2) {
        this.cont = context;
        this.source_path = str;
        this.distinaton_path = str2;
    }
    public void onPreExecute() {
        showProgressDialog();
    }
    public String doInBackground(Void... voidArr) {
        Throwable th;
        TarArchiveOutputStream tarArchiveOutputStream;
        TarArchiveOutputStream tarArchiveOutputStream2 = null;
        try {
            tarArchiveOutputStream = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream(this.distinaton_path)));
            try {
                addFilesToTarGZ(this.source_path, "", tarArchiveOutputStream);
            } catch (IOException e) {
                e = e;
                try {
                    e.printStackTrace();
                    tarArchiveOutputStream.close();
                    return null;
                } catch (Throwable th2) {
                    TarArchiveOutputStream tarArchiveOutputStream3 = tarArchiveOutputStream;
                    th = th2;
                    tarArchiveOutputStream2 = tarArchiveOutputStream3;
                    try {
                        tarArchiveOutputStream2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    throw th;
                }
            }
            try {
                tarArchiveOutputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (Exception e4) {

            tarArchiveOutputStream = null;

            try {
                tarArchiveOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            try {
                tarArchiveOutputStream2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public void addFilesToTarGZ(String str, String str2, TarArchiveOutputStream tarArchiveOutputStream) throws IOException {
        File file = new File(str);
        String str3 = str2 + file.getName();
        tarArchiveOutputStream.putArchiveEntry(new TarArchiveEntry(file, str3));
        if (file.isFile()) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            IOUtils.copy(bufferedInputStream, tarArchiveOutputStream);
            tarArchiveOutputStream.closeArchiveEntry();
            bufferedInputStream.close();
        } else if (file.isDirectory()) {
            tarArchiveOutputStream.closeArchiveEntry();
            for (File absolutePath : file.listFiles()) {
                addFilesToTarGZ(absolutePath.getAbsolutePath(), str3 + File.separator, tarArchiveOutputStream);
            }
        }
    }
    public void onPostExecute(String str) {
        dismissProgressDialog();
        showDialogForOpenAllCompressedFiles();
    }

    private void showProgressDialog() {
        if (this.dialog == null) {
            this.dialog = new ProgressDialog(this.cont);
            this.dialog.setMessage(this.cont.getText(R.string.progress_message));
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
    private void showDialogForOpenAllCompressedFiles() {
        this.editor = this.cont.getSharedPreferences("MyPrefsFile", 0).edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.cont);
        builder.setMessage("File is Successfully Extracted.");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                TarGzAsynkPerform.this.editor.clear();
                TarGzAsynkPerform.this.editor.putInt("idName", 1);
                TarGzAsynkPerform.this.editor.apply();
                ActivityUtils.startActivity(new Intent(TarGzAsynkPerform.this.cont, AllInOneCompress.class).putExtra("option", "compress"));
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
