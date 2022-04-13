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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GzipAsynkPerform extends AsyncTask<Void, Void, String> {
    @SuppressLint({"StaticFieldLeak"})
    public Context cont;
    private ProgressDialog dialog;
    private String distinaton_path;

    public SharedPreferences.Editor editor;
    private String source_path;

    public GzipAsynkPerform(Context context, String str, String str2) {
        this.cont = context;
        this.source_path = str;
        this.distinaton_path = str2;
    }

    public void onPreExecute() {
        showProgressDialog();
    }

    public String doInBackground(Void... voidArr) {
        try {
            FileInputStream fileInputStream = new FileInputStream(this.source_path);
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(new FileOutputStream(this.distinaton_path));
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    gZIPOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    gZIPOutputStream.close();
                    return null;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }
    public void onPostExecute(String str) {
        dismissProgressDialog();
        Show_Dialog_For_Open_All_Compressed_Files();
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

    private void Show_Dialog_For_Open_All_Compressed_Files() {
        this.editor = this.cont.getSharedPreferences("MyPrefsFile", 0).edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.cont);
        builder.setMessage("File is Successfully Extracted.");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                GzipAsynkPerform.this.editor.clear();
                GzipAsynkPerform.this.editor.putInt("idName", 1);
                GzipAsynkPerform.this.editor.apply();
                ActivityUtils.startActivity(new Intent(GzipAsynkPerform.this.cont, AllInOneCompress.class).putExtra("option", "compress"));
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
