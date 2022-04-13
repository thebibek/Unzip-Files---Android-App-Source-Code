package com.unzipfile.smartextractfile.compressor.zipperfile.ashsunzip;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.BillingProcessor.IBillingHandler;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.compressor.extractor.zipers.fileAdaptersForWork.FileAdapterItemsOneLiner;
import com.compressor.extractor.zipers.fileAdaptersForWork.ItemAdapterPath;
import com.compressor.extractor.zipers.fileWork.FileRelatedInformation;
import com.compressor.extractor.zipers.utilitesWorkFile.UtilitiesFileWorker;
import com.compressor.extractor.zipers.command.OrderCommandActivity;
import com.compressor.extractor.zipers.compression.GzipAsynkPerform;
import com.compressor.extractor.zipers.compression.TarGzAsynkPerform;
import com.compressor.extractor.zipers.compression.ZipAsynkPerform;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hzy.libp7zip.P7ZipApi;
import com.unzipfile.smartextractfile.compressor.zipperfile.R;

import net.lingala.zip4j.util.InternalZipConstants;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class SaveFileActivity extends AppCompatActivity implements OnClickListener, IBillingHandler {
    public static int counter = 1;
    public static FloatingActionMenu fab;
    public static Boolean longclickonce = Boolean.valueOf(false);
    public static ArrayList<FileRelatedInformation> selecteditems = new ArrayList<>();
    public ArrayList<FileRelatedInformation> BufferInfoList;
    File Compress;
    String Edited_File_Name;
    File Extracted;
    BillingProcessor adv_billingProcessor;
    Editor adv_editor;
    SharedPreferences adv_pref;
    Boolean adv_purchase;
    int check = 0;
    Dialog dialog;
    String extention_name;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    String file_name;
    int j;
    public ArrayList<FileRelatedInformation> mCurFileInfoList;
    public String mCurPath;
    public FileAdapterItemsOneLiner mFileItemAdapter;
    public ItemAdapterPath mFilePathAdapter;
    RecyclerView mPathListView;
    RecyclerView mStorageListView;
    private ProgressDialog progressDialog;
    File temp;

    @SuppressLint({"StaticFieldLeak"})
    class FileAsync extends AsyncTask<String, String, String> {
        FileAsync() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public String doInBackground(String... strArr) {
            SaveFileActivity.this.loadPathInfo(SaveFileActivity.this.mCurPath);
            return null;
        }

        public void onPostExecute(String str) {
            SaveFileActivity.this.mPathListView.setAdapter(SaveFileActivity.this.mFilePathAdapter);
            SaveFileActivity.this.mStorageListView.setAdapter(SaveFileActivity.this.mFileItemAdapter);
            SaveFileActivity.this.mFileItemAdapter.notifyDataSetChanged();
            SaveFileActivity.this.mFilePathAdapter.notifyDataSetChanged();
        }
    }

    public static class FileOpen {
        @SuppressLint("WrongConstant")
        public static void openFile(Context context, File file) throws IOException {
            Uri fromFile = Uri.fromFile(file);
            Intent intent = new Intent("android.intent.action.VIEW");
            if (file.toString().contains(".doc") || file.toString().contains(".docx")) {
                intent.setDataAndType(fromFile, "application/msword");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else if (file.toString().contains(".pdf")) {
                intent.setDataAndType(fromFile, "application/pdf");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused2) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else if (file.toString().contains(".ppt") || file.toString().contains(".pptx")) {
                intent.setDataAndType(fromFile, "application/vnd.ms-powerpoint");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused3) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else if (file.toString().contains(".xls") || file.toString().contains(".xlsx")) {
                intent.setDataAndType(fromFile, "application/vnd.ms-excel");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused4) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else if (file.toString().contains(".zip") || file.toString().contains(".rar")) {
                intent.setDataAndType(fromFile, "application/x-wav");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused5) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else if (file.toString().contains(".rtf")) {
                intent.setDataAndType(fromFile, "application/rtf");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused6) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else if (file.toString().contains(".wav") || file.toString().contains(".mp3")) {
                intent.setDataAndType(fromFile, "audio/x-wav");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused7) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else if (file.toString().contains(".gif")) {
                intent.setDataAndType(fromFile, "image/gif");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused8) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else if (file.toString().contains(".jpg") || file.toString().contains(".jpeg") || file.toString().contains(".png")) {
                intent.setDataAndType(fromFile, "image/jpeg");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused9) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else if (file.toString().contains(".txt")) {
                intent.setDataAndType(fromFile, "text/plain");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused10) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else if (file.toString().contains(".3gp") || file.toString().contains(".mpg") || file.toString().contains(".mpeg") || file.toString().contains(".mpe") || file.toString().contains(".mp4") || file.toString().contains(".avi")) {
                intent.setDataAndType(fromFile, "video/*");
                intent.addFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (Exception unused11) {
                    Toast.makeText(context, "No default Application found!", 0).show();
                }
            } else {
                Toast.makeText(context, "No default Application found!", 0).show();
            }
        }
    }

    public void onBillingError(int i, @Nullable Throwable th) {
    }

    public void onBillingInitialized() {
    }

    public void onPurchaseHistoryRestored() {
    }

    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Locale locale = new Locale(getSharedPreferences("enter", 0).getString("lang", "en"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        setContentView((int) R.layout.activity_save_file);
        this.adv_pref = getSharedPreferences("adspreferance", 0);
        this.adv_editor = getSharedPreferences("adspreferance", 0).edit();
        this.adv_billingProcessor = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgDxEWoPNWAaKViI+VualyxnZMgUNR5qlkuWj4IIlDrlfOktFbewhXnRA3y2vuKyP3RRzC9+41VHKdqLJ44KBqeWxzMm0csLLT4Hn1vfmF1B4xaal1DgofGbKM9y5MsbFFF+KEmxQNAzVXFYq50ZUHB1KD8OGg0S8Z7tW8OhHdsXt7xttGKkRI8SgHDJWJhFd2NslW2/UqXLAf0itJktWf4CKI93s7hr0Ea4pW8bQBZVJgk/RCi3HcaJGCQ+DvpRtP6rcBpXhKsaikfnMEH4pJ/bFVXfQ1Tvw0lY0V+8HIUrlFiumQb+KBlO+EqIoc4bBkpZaqby5gJ5xIaWkIziKqQIDAQAB", this);
        this.adv_billingProcessor.initialize();
        this.mPathListView = (RecyclerView) findViewById(R.id.fragment_storage_path);
        this.mStorageListView = (RecyclerView) findViewById(R.id.fragment_storage_list);
        Intent intent = getIntent();
        this.mFileItemAdapter = new FileAdapterItemsOneLiner(this, this, this);
        this.mFilePathAdapter = new ItemAdapterPath(this);
        this.j = intent.getIntExtra("choice", 0);
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        StringBuilder sb = new StringBuilder();
        sb.append(externalStorageDirectory.getAbsolutePath());
        sb.append("/Extractor/");
        File file = new File(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(externalStorageDirectory.getAbsolutePath());
        sb2.append("/Extractor/Compressed");
        this.Compress = new File(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(externalStorageDirectory.getAbsolutePath());
        sb3.append("/Extractor/Extract");
        this.Extracted = new File(sb3.toString());
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!this.Compress.exists()) {
                this.Compress.mkdirs();
            }
            if (!this.Extracted.exists()) {
                this.Extracted.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String externalStorageState = Environment.getExternalStorageState();
        if ("mounted".equals(externalStorageState) || "mounted_ro".equals(externalStorageState)) {
            File externalStorageDirectory2 = Environment.getExternalStorageDirectory();
            String str = "hi";
            String parent = externalStorageDirectory2.getParent();
            if (parent == null) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("External Storage: ");
                sb4.append(externalStorageDirectory2);
                sb4.append("\n");
                Log.e(str, sb4.toString());
            } else {
                File file2 = new File(parent);
                File[] listFiles = file2.listFiles();
                this.mCurPath = file2.getAbsolutePath();
                String[] split = this.mCurPath.split(InternalZipConstants.ZIP_FILE_SEPARATOR);
                if (listFiles != null) {
                    if (this.j == 1 && listFiles.length == 4) {
                        this.mCurPath = listFiles[1].getAbsolutePath();
                    } else if (this.j == 2 && listFiles.length == 4) {
                        this.mCurPath = listFiles[2].getAbsolutePath();
                    } else if (this.j == 3) {
                        this.mCurPath = this.Compress.getAbsolutePath();
                    } else if (this.j == 4) {
                        this.mCurPath = this.Extracted.getAbsolutePath();
                    } else if (split.length > 2) {
                        this.mCurPath = this.mCurPath.substring(0, this.mCurPath.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR));
                    }
                } else if (this.j == 4) {
                    this.mCurPath = this.Extracted.getAbsolutePath();
                } else if (this.j == 3) {
                    this.mCurPath = this.Compress.getAbsolutePath();
                } else {
                    this.j = 2;
                    this.mCurPath = externalStorageDirectory2.getAbsolutePath();
                }
            }
        }
        this.mPathListView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.mStorageListView.setLayoutManager(new LinearLayoutManager(this));
        new FileAsync().execute(new String[]{"start"});
        final String format = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa").format(Calendar.getInstance().getTime());
        StringBuilder sb5 = new StringBuilder();
        sb5.append(externalStorageDirectory.getAbsolutePath());
        sb5.append("/Extractor/.temp_Compressed");
        this.temp = new File(sb5.toString());
        fab = findViewById(R.id.menu);
        this.fab1 = findViewById(R.id.zip);
        this.fab1.setOnClickListener(new OnClickListener() {
            @SuppressLint("ResourceType")
            public void onClick(View view) {
                if (SaveFileActivity.this.temp.exists()) {
                    SaveFileActivity.this.temp.delete();
                }
                SaveFileActivity.this.temp.mkdirs();
                Iterator it = SaveFileActivity.selecteditems.iterator();
                while (it.hasNext()) {
                    FileRelatedInformation file_Related_Information = (FileRelatedInformation) it.next();
                    File file = new File(file_Related_Information.getFilePath());
                    StringBuilder sb = new StringBuilder();
                    sb.append(SaveFileActivity.this.temp);
                    sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                    sb.append(file_Related_Information.getFileName());
                }
                Builder builder = new Builder(SaveFileActivity.this);
                builder.setTitle("Enter Output File Name");
                View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.input);
                builder.setView(inflate);
                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        SaveFileActivity.this.showProgressDialog();
                        SaveFileActivity save_File_Activity = SaveFileActivity.this;
                        String absolutePath = SaveFileActivity.this.temp.getAbsolutePath();
                        String str = ArchiveStreamFactory.SEVEN_Z;
                        String absolutePath2 = SaveFileActivity.this.Compress.getAbsolutePath();
                        StringBuilder sb = new StringBuilder();
                        sb.append(editText.getText().toString());
                        sb.append(" ");
                        sb.append(format);
                        save_File_Activity.onCompressMultiFile(absolutePath, str, absolutePath2, sb.toString());
                    }
                });
                builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });
        this.fab2 = findViewById(R.id.delete);
        this.fab2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Builder builder = new Builder(SaveFileActivity.this);
                builder.setMessage(SaveFileActivity.this.getString(R.string.deletesure));
                builder.setCancelable(true);
                builder.setPositiveButton(SaveFileActivity.this.getString(R.string.delete), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Iterator it = SaveFileActivity.selecteditems.iterator();
                        while (it.hasNext()) {
                            SaveFileActivity.this.onRemoveFileMulti((FileRelatedInformation) it.next());
                        }
                        if (SaveFileActivity.longclickonce.booleanValue()) {
                            SaveFileActivity.counter = 1;
                            for (int i2 = 0; i2 < SaveFileActivity.this.BufferInfoList.size(); i2++) {
                                ((FileRelatedInformation) SaveFileActivity.this.BufferInfoList.get(i2)).setSelected(false);
                            }
                            SaveFileActivity.selecteditems.clear();
                            SaveFileActivity.longclickonce = Boolean.valueOf(false);
                            SaveFileActivity.fab.setVisibility(8);
                            SaveFileActivity.fab.close(true);
                        }
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton(SaveFileActivity.this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    public void onCompressMultiFile(String str, String str2, String str3, String str4) {
        getWindow().addFlags(128);
        this.check = 1;
        StringBuilder sb = new StringBuilder();
        sb.append(str3);
        sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
        sb.append(str4);
        sb.append(".");
        sb.append(str2);
        runCommand2(OrderCommandActivity.getCompressCmd(str, sb.toString(), str2));
    }

    @SuppressLint({"CheckResult"})
    private void runCommand2(final String str) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(Integer.valueOf(P7ZipApi.executeCommand(str)));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            public void accept(Integer num) throws Exception {
                if (!SaveFileActivity.this.isFinishing()) {
                    SaveFileActivity.this.dismissProgressDialog();
                }
                SaveFileActivity.this.showResult(num.intValue());
            }
        });
    }

    @SuppressLint({"CheckResult"})
    public void loadPathInfo(final String str) {
        Observable.create(new ObservableOnSubscribe<List<FileRelatedInformation>>() {
            public void subscribe(ObservableEmitter<List<FileRelatedInformation>> observableEmitter) throws Exception {
                SaveFileActivity.this.BufferInfoList = UtilitiesFileWorker.getInfoListFromPath(str);
                observableEmitter.onNext(SaveFileActivity.this.BufferInfoList);
                observableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<FileRelatedInformation>>() {
            public void accept(List<FileRelatedInformation> list) throws Exception {
                SaveFileActivity.this.mCurFileInfoList = SaveFileActivity.this.BufferInfoList;
                SaveFileActivity.this.mFileItemAdapter.setDataList(SaveFileActivity.this.mCurFileInfoList);
                SaveFileActivity.this.mCurPath = str;
                SaveFileActivity.this.mFilePathAdapter.setPathView(SaveFileActivity.this.mCurPath);
                SaveFileActivity.this.mFileItemAdapter.notifyDataSetChanged();
                SaveFileActivity.this.mFilePathAdapter.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("WrongConstant")
    public void onBackPressed() {
        if (longclickonce.booleanValue()) {
            counter = 1;
            for (int i = 0; i < this.BufferInfoList.size(); i++) {
                (this.BufferInfoList.get(i)).setSelected(false);
            }
            selecteditems.clear();
            longclickonce = Boolean.valueOf(false);
            fab.setVisibility(8);
            fab.close(true);
            this.mCurFileInfoList = this.BufferInfoList;
            this.mFileItemAdapter.setDataList(this.mCurFileInfoList);
            this.mFileItemAdapter.notifyDataSetChanged();
            return;
        }
        String[] split = this.mCurPath.split(InternalZipConstants.ZIP_FILE_SEPARATOR);
        if (this.j <= 0) {
            return;
        }
        if (split.length > 6) {
            loadPathInfo(this.mCurPath.substring(0, this.mCurPath.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR)));
        } else if (split.length <= 4 || this.j != 2) {
            super.onBackPressed();
        } else {
            loadPathInfo(this.mCurPath.substring(0, this.mCurPath.lastIndexOf(InternalZipConstants.ZIP_FILE_SEPARATOR)));
        }
    }

    public void onRefresh() {
        loadPathInfo(this.mCurPath);
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        if (longclickonce.booleanValue()) {
            int intValue = ((Integer) view.getTag()).intValue();
            if (intValue < this.BufferInfoList.size()) {
                fab.close(true);
                fab.setVisibility(0);
                if (!((FileRelatedInformation) this.BufferInfoList.get(intValue)).isSelected()) {
                    ((FileRelatedInformation) this.BufferInfoList.get(intValue)).setSelected(true);
                    counter++;
                    longclickonce = Boolean.valueOf(true);
                    selecteditems.add(this.BufferInfoList.get(intValue));
                } else {
                    selecteditems.remove(this.BufferInfoList.get(intValue));
                    counter--;
                    ((FileRelatedInformation) this.BufferInfoList.get(intValue)).setSelected(false);
                    if (counter == 1) {
                        selecteditems.clear();
                        longclickonce = Boolean.valueOf(false);
                        fab.setVisibility(8);
                        fab.close(true);
                        this.mCurFileInfoList = this.BufferInfoList;
                        this.mFileItemAdapter.setDataList(this.mCurFileInfoList);
                    }
                }
                this.mCurFileInfoList = this.BufferInfoList;
                this.mFileItemAdapter.setDataList(this.mCurFileInfoList);
                this.mFileItemAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        int intValue2 = ((Integer) view.getTag()).intValue();
        if (intValue2 < this.BufferInfoList.size()) {
            final FileRelatedInformation file_Related_Information = (FileRelatedInformation) this.BufferInfoList.get(intValue2);
            if (view.getId() == R.id.moremenu) {
                if (this.j == 4) {
                    if (file_Related_Information.isFolder()) {
                        this.dialog = new Dialog(this);
                        this.dialog.requestWindowFeature(1);
                        if (VERSION.SDK_INT >= 19) {
                            Window window = this.dialog.getWindow();
                            window.getClass();
                            window.setBackgroundDrawable(new ColorDrawable(0));
                        }
                        this.dialog.setContentView(R.layout.dialog_for_compress_file);
                        TextView textView = (TextView) this.dialog.findViewById(R.id.name_of_selected_item);
                        ImageView imageView = (ImageView) this.dialog.findViewById(R.id.icon_of_item);
                        RelativeLayout relativeLayout = (RelativeLayout) this.dialog.findViewById(R.id.open_file);
                        ((RelativeLayout) this.dialog.findViewById(R.id.share)).setVisibility(8);
                        this.dialog.findViewById(R.id.v4).setVisibility(8);
                        TextView textView2 = (TextView) this.dialog.findViewById(R.id.size_of_file);
                        RelativeLayout relativeLayout2 = (RelativeLayout) this.dialog.findViewById(R.id.compress_7z);
                        ImageView imageView2 = (ImageView) this.dialog.findViewById(R.id.croun1_back);
                        ImageView imageView3 = (ImageView) this.dialog.findViewById(R.id.croun1);
                        ImageView imageView4 = (ImageView) this.dialog.findViewById(R.id.croun2_back);
                        ImageView imageView5 = (ImageView) this.dialog.findViewById(R.id.croun2);
                        RelativeLayout relativeLayout3 = (RelativeLayout) this.dialog.findViewById(R.id.delete);
                        ImageView imageView6 = (ImageView) this.dialog.findViewById(R.id.croun3_back);
                        RelativeLayout relativeLayout4 = (RelativeLayout) this.dialog.findViewById(R.id.compress_tar_gz);
                        ImageView imageView7 = (ImageView) this.dialog.findViewById(R.id.croun3);
                        RelativeLayout relativeLayout5 = (RelativeLayout) this.dialog.findViewById(R.id.compress_gzip);
                        RelativeLayout relativeLayout6 = (RelativeLayout) this.dialog.findViewById(R.id.compress_zip);
                        this.adv_purchase = Boolean.valueOf(this.adv_pref.getBoolean("adv_preferance", false));
                        if (this.adv_purchase.booleanValue()) {
                            imageView2.setVisibility(8);
                            imageView3.setVisibility(8);
                            imageView4.setVisibility(8);
                            imageView5.setVisibility(8);
                            imageView6.setVisibility(8);
                            imageView7.setVisibility(8);
                        }
                        this.file_name = file_Related_Information.getFileName();
                        this.extention_name = file_Related_Information.getFileName();
                        if (this.file_name.indexOf(".") > 0) {
                            this.file_name = this.file_name.substring(0, this.file_name.lastIndexOf("."));
                            this.extention_name = this.extention_name.substring(this.extention_name.lastIndexOf("."));
                        }
                        textView.setText(this.file_name);
                        float parseInt = (float) Integer.parseInt(String.valueOf(new File(file_Related_Information.getFilePath()).length() / 1024));
                        if (parseInt >= 1024.0f) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(new DecimalFormat("##.##").format((double) (parseInt / 1024.0f)));
                            sb.append(" Mb");
                            textView2.setText(sb.toString());
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(parseInt);
                            sb2.append(" Kb");
                            textView2.setText(sb2.toString());
                        }
                        switch (file_Related_Information.getFileType()) {
                            case folderFull:
                                imageView.setImageResource(R.drawable.icon_folder_full);
                                break;
                            case folderEmpty:
                                imageView.setImageResource(R.drawable.icon_folder_empty);
                                break;
                        }
                        relativeLayout.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                SaveFileActivity.this.loadPathInfo(file_Related_Information.getFilePath());
                                SaveFileActivity.this.dialog.dismiss();
                            }
                        });
                        relativeLayout2.setOnClickListener(new OnClickListener() {
                            @SuppressLint("ResourceType")
                            public void onClick(View view) {
                                Builder builder = new Builder(SaveFileActivity.this);
                                builder.setTitle("Enter Output File Name");
                                View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                builder.setView(inflate);
                                builder.setCancelable(false);
                                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (editText.getText().toString().isEmpty()) {
                                            Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                            return;
                                        }
                                        SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(editText.getText().toString());
                                        sb.append(SaveFileActivity.this.extention_name);
                                        save_File_Activity.Edited_File_Name = sb.toString();
                                        SaveFileActivity.this.onCompressFile(file_Related_Information);
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.show();
                                SaveFileActivity.this.dialog.dismiss();
                            }
                        });
                        relativeLayout6.setOnClickListener(new OnClickListener() {
                            @SuppressLint("ResourceType")
                            public void onClick(View view) {
                                SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                                if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                    Builder builder = new Builder(SaveFileActivity.this);
                                    builder.setTitle("Enter Output File Name");
                                    View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                    final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                    builder.setView(inflate);
                                    builder.setCancelable(false);
                                    builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (editText.getText().toString().isEmpty()) {
                                                Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                                return;
                                            }
                                            SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(editText.getText().toString());
                                            sb.append(SaveFileActivity.this.extention_name);
                                            save_File_Activity.Edited_File_Name = sb.toString();
                                            SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                            String filePath = file_Related_Information.getFilePath();
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(SaveFileActivity.this.Compress);
                                            sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                            sb2.append(SaveFileActivity.this.Edited_File_Name);
                                            sb2.append(".zip");
                                            new ZipAsynkPerform(save_File_Activity2, filePath, sb2.toString(), "").execute(new Void[0]);
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    builder.show();
                                    SaveFileActivity.this.dialog.dismiss();
                                    return;
                                }
                                SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                            }
                        });
                        relativeLayout5.setOnClickListener(new OnClickListener() {
                            @SuppressLint("ResourceType")
                            public void onClick(View view) {
                                SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                                if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                    Builder builder = new Builder(SaveFileActivity.this);
                                    builder.setTitle("Enter Output File Name");
                                    View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                    final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                    builder.setView(inflate);
                                    builder.setCancelable(false);
                                    builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (editText.getText().toString().isEmpty()) {
                                                Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                                return;
                                            }
                                            SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(editText.getText().toString());
                                            sb.append(SaveFileActivity.this.extention_name);
                                            save_File_Activity.Edited_File_Name = sb.toString();
                                            SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                            String filePath = file_Related_Information.getFilePath();
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(SaveFileActivity.this.Compress);
                                            sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                            sb2.append(SaveFileActivity.this.Edited_File_Name);
                                            sb2.append(".gzip");
                                            new GzipAsynkPerform(save_File_Activity2, filePath, sb2.toString()).execute(new Void[0]);
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    builder.show();
                                    SaveFileActivity.this.dialog.dismiss();
                                    return;
                                }
                                SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                            }
                        });
                        relativeLayout4.setOnClickListener(new OnClickListener() {
                            @SuppressLint("ResourceType")
                            public void onClick(View view) {
                                SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                                if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                    Builder builder = new Builder(SaveFileActivity.this);
                                    builder.setTitle("Enter Output File Name");
                                    View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                    final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                    builder.setView(inflate);
                                    builder.setCancelable(false);
                                    builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (editText.getText().toString().isEmpty()) {
                                                Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                                return;
                                            }
                                            SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(editText.getText().toString());
                                            sb.append(SaveFileActivity.this.extention_name);
                                            save_File_Activity.Edited_File_Name = sb.toString();
                                            SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                            String filePath = file_Related_Information.getFilePath();
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(SaveFileActivity.this.Compress);
                                            sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                            sb2.append(SaveFileActivity.this.Edited_File_Name);
                                            sb2.append(".tar.gz");
                                            new TarGzAsynkPerform(save_File_Activity2, filePath, sb2.toString()).execute(new Void[0]);
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    builder.show();
                                    SaveFileActivity.this.dialog.dismiss();
                                    return;
                                }
                                SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                            }
                        });
                        relativeLayout3.setOnClickListener(new OnClickListener() {
                            @SuppressLint("ResourceType")
                            public void onClick(View view) {
                                new Builder(SaveFileActivity.this).setTitle(R.string.delete_file).setMessage(R.string.sure).setPositiveButton(17039379, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SaveFileActivity.this.onRemoveFile(file_Related_Information);
                                    }
                                }).setNegativeButton(17039369, null).show();
                                SaveFileActivity.this.dialog.cancel();
                            }
                        });
                        this.dialog.show();
                        return;
                    }
                    this.dialog = new Dialog(this);
                    this.dialog.requestWindowFeature(1);
                    if (VERSION.SDK_INT >= 19) {
                        Window window2 = this.dialog.getWindow();
                        window2.getClass();
                        window2.setBackgroundDrawable(new ColorDrawable(0));
                    }
                    this.dialog.setContentView(R.layout.dialog_for_compress_file);
                    TextView textView3 = (TextView) this.dialog.findViewById(R.id.name_of_selected_item);
                    ImageView imageView8 = (ImageView) this.dialog.findViewById(R.id.icon_of_item);
                    TextView textView4 = (TextView) this.dialog.findViewById(R.id.size_of_file);
                    RelativeLayout relativeLayout7 = (RelativeLayout) this.dialog.findViewById(R.id.open_file);
                    RelativeLayout relativeLayout8 = (RelativeLayout) this.dialog.findViewById(R.id.compress_7z);
                    ImageView imageView9 = (ImageView) this.dialog.findViewById(R.id.croun1_back);
                    ImageView imageView10 = (ImageView) this.dialog.findViewById(R.id.croun1);
                    ImageView imageView11 = (ImageView) this.dialog.findViewById(R.id.croun2_back);
                    RelativeLayout relativeLayout9 = (RelativeLayout) this.dialog.findViewById(R.id.delete);
                    ImageView imageView12 = (ImageView) this.dialog.findViewById(R.id.croun2);
                    RelativeLayout relativeLayout10 = (RelativeLayout) this.dialog.findViewById(R.id.share);
                    ImageView imageView13 = (ImageView) this.dialog.findViewById(R.id.croun3_back);
                    RelativeLayout relativeLayout11 = (RelativeLayout) this.dialog.findViewById(R.id.compress_tar_gz);
                    ImageView imageView14 = (ImageView) this.dialog.findViewById(R.id.croun3);
                    RelativeLayout relativeLayout12 = (RelativeLayout) this.dialog.findViewById(R.id.compress_gzip);
                    RelativeLayout relativeLayout13 = (RelativeLayout) this.dialog.findViewById(R.id.compress_zip);
                    this.adv_purchase = Boolean.valueOf(this.adv_pref.getBoolean("adv_preferance", false));
                    if (this.adv_purchase.booleanValue()) {
                        imageView9.setVisibility(8);
                        imageView10.setVisibility(8);
                        imageView11.setVisibility(8);
                        imageView12.setVisibility(8);
                        imageView13.setVisibility(8);
                        imageView14.setVisibility(8);
                    }
                    this.file_name = file_Related_Information.getFileName();
                    if (this.file_name.indexOf(".") > 0) {
                        this.file_name = this.file_name.substring(0, this.file_name.lastIndexOf("."));
                    }
                    this.extention_name = file_Related_Information.getFileName();
                    try {
                        this.extention_name = this.extention_name.substring(this.extention_name.lastIndexOf("."));
                    } catch (StringIndexOutOfBoundsException unused) {
                        this.extention_name = "";
                    }
                    textView3.setText(this.file_name);
                    float parseInt2 = (float) Integer.parseInt(String.valueOf(new File(file_Related_Information.getFilePath()).length() / 1024));
                    if (parseInt2 >= 1024.0f) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(new DecimalFormat("##.##").format((double) (parseInt2 / 1024.0f)));
                        sb3.append(" Mb");
                        textView4.setText(sb3.toString());
                    } else {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(parseInt2);
                        sb4.append(" Kb");
                        textView4.setText(sb4.toString());
                    }
                    String filePath = file_Related_Information.getFilePath();
                    if (file_Related_Information.getFileName().endsWith("pdf")) {
                        imageView8.setImageResource(R.drawable.pdf);
                    } else if (file_Related_Information.getFileName().endsWith("ppt")) {
                        imageView8.setImageResource(R.drawable.ppt);
                    } else if (file_Related_Information.getFileName().endsWith("doc")) {
                        imageView8.setImageResource(R.drawable.doc);
                    } else if (file_Related_Information.getFileName().endsWith("docx")) {
                        imageView8.setImageResource(R.drawable.docx);
                    } else if (file_Related_Information.getFileName().endsWith("msg")) {
                        imageView8.setImageResource(R.drawable.msg);
                    } else if (file_Related_Information.getFileName().endsWith("txt")) {
                        imageView8.setImageResource(R.drawable.txt);
                    } else if (file_Related_Information.getFileName().endsWith("mp4") || file_Related_Information.getFileName().endsWith("avi") || file_Related_Information.getFileName().endsWith("mpg") || file_Related_Information.getFileName().endsWith("mov") || file_Related_Information.getFileName().endsWith("wmv")) {
                        ((RequestBuilder) Glide.with((FragmentActivity) this).asBitmap()).load(Uri.fromFile(new File(filePath))).into(imageView8);
                    } else if (file_Related_Information.getFileName().endsWith("jpg") || file_Related_Information.getFileName().endsWith("png")) {
                        ((RequestBuilder) ((RequestBuilder) Glide.with((FragmentActivity) this).load(filePath))).into(imageView8);
                    } else if (file_Related_Information.getFileName().endsWith("wpd")) {
                        imageView8.setImageResource(R.drawable.wpd);
                    } else if (file_Related_Information.getFileName().endsWith("xls")) {
                        imageView8.setImageResource(R.drawable.xls);
                    } else if (file_Related_Information.getFileName().endsWith("xlsx")) {
                        imageView8.setImageResource(R.drawable.xlsx);
                    } else if (file_Related_Information.getFileName().endsWith("odf")) {
                        imageView8.setImageResource(R.drawable.odf);
                    } else if (file_Related_Information.getFileName().endsWith("odt")) {
                        imageView8.setImageResource(R.drawable.odt);
                    } else if (file_Related_Information.getFileName().endsWith("rtf")) {
                        imageView8.setImageResource(R.drawable.rtf);
                    } else if (file_Related_Information.getFileName().endsWith("apk")) {
                        imageView8.setImageResource(R.drawable.apk);
                    } else if (file_Related_Information.getFileName().endsWith("pptx")) {
                        imageView8.setImageResource(R.drawable.pptx);
                    } else {
                        imageView8.setImageResource(R.drawable.icon_unknown);
                    }
                    switch (file_Related_Information.getFileType()) {
                        case folderFull:
                            imageView8.setImageResource(R.drawable.icon_folder_full);
                            break;
                        case folderEmpty:
                            imageView8.setImageResource(R.drawable.icon_folder_empty);
                            break;
                    }
                    relativeLayout7.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (VERSION.SDK_INT >= 24) {
                                try {
                                    StrictMode.class.getMethod("disableDeathOnFileUriExposure", new Class[0]).invoke(null, new Object[0]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            String filePath = file_Related_Information.getFilePath();
                            SaveFileActivity save_File_Activity = SaveFileActivity.this;
                            StringBuilder sb = new StringBuilder();
                            sb.append("");
                            sb.append(file_Related_Information.getFilePath());
                            Toast.makeText(save_File_Activity, sb.toString(), 1).show();
                            try {
                                FileOpen.openFile(SaveFileActivity.this, new File(filePath));
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                            SaveFileActivity.this.dialog.dismiss();
                        }
                    });
                    relativeLayout8.setOnClickListener(new OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View view) {
                            Builder builder = new Builder(SaveFileActivity.this);
                            builder.setTitle("Enter Output File Name");
                            View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                            final EditText editText = (EditText) inflate.findViewById(R.id.input);
                            builder.setView(inflate);
                            builder.setCancelable(false);
                            builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (editText.getText().toString().isEmpty()) {
                                        Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                        return;
                                    }
                                    SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(editText.getText().toString());
                                    sb.append(SaveFileActivity.this.extention_name);
                                    save_File_Activity.Edited_File_Name = sb.toString();
                                    SaveFileActivity.this.onCompressFile(file_Related_Information);
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builder.show();
                            SaveFileActivity.this.dialog.dismiss();
                        }
                    });
                    relativeLayout13.setOnClickListener(new OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View view) {
                            SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                            if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                Builder builder = new Builder(SaveFileActivity.this);
                                builder.setTitle("Enter Output File Name");
                                View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                builder.setView(inflate);
                                builder.setCancelable(false);
                                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (editText.getText().toString().isEmpty()) {
                                            Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(editText.getText().toString());
                                        sb.append(SaveFileActivity.this.extention_name);
                                        save_File_Activity.Edited_File_Name = sb.toString();
                                        SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                        String filePath = file_Related_Information.getFilePath();
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(SaveFileActivity.this.Compress);
                                        sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                        sb2.append(SaveFileActivity.this.Edited_File_Name);
                                        sb2.append(".zip");
                                        new ZipAsynkPerform(save_File_Activity2, filePath, sb2.toString(), "").execute(new Void[0]);
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.show();
                                SaveFileActivity.this.dialog.dismiss();
                                return;
                            }
                            SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                        }
                    });
                    relativeLayout12.setOnClickListener(new OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View view) {
                            SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                            if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                Builder builder = new Builder(SaveFileActivity.this);
                                builder.setTitle("Enter Output File Name");
                                View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                builder.setView(inflate);
                                builder.setCancelable(false);
                                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (editText.getText().toString().isEmpty()) {
                                            Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                            return;
                                        }
                                        SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(editText.getText().toString());
                                        sb.append(SaveFileActivity.this.extention_name);
                                        save_File_Activity.Edited_File_Name = sb.toString();
                                        SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                        String filePath = file_Related_Information.getFilePath();
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(SaveFileActivity.this.Compress);
                                        sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                        sb2.append(SaveFileActivity.this.Edited_File_Name);
                                        sb2.append(".gzip");
                                        new GzipAsynkPerform(save_File_Activity2, filePath, sb2.toString()).execute(new Void[0]);
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.show();
                                SaveFileActivity.this.dialog.dismiss();
                                return;
                            }
                            SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                        }
                    });
                    relativeLayout11.setOnClickListener(new OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View view) {
                            SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                            if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                Builder builder = new Builder(SaveFileActivity.this);
                                builder.setTitle("Enter Output File Name");
                                View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                builder.setView(inflate);
                                builder.setCancelable(false);
                                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (editText.getText().toString().isEmpty()) {
                                            Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                            return;
                                        }
                                        SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(editText.getText().toString());
                                        sb.append(SaveFileActivity.this.extention_name);
                                        save_File_Activity.Edited_File_Name = sb.toString();
                                        SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                        String filePath = file_Related_Information.getFilePath();
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(SaveFileActivity.this.Compress);
                                        sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                        sb2.append(SaveFileActivity.this.Edited_File_Name);
                                        sb2.append(".tar.gz");
                                        new TarGzAsynkPerform(save_File_Activity2, filePath, sb2.toString()).execute(new Void[0]);
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.show();
                                SaveFileActivity.this.dialog.dismiss();
                                return;
                            }
                            SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                        }
                    });
                    relativeLayout10.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("image/*");
                            intent.putExtra("android.intent.extra.STREAM", Uri.parse(file_Related_Information.getFilePath()));
                            try {
                                SaveFileActivity.this.startActivity(Intent.createChooser(intent, "Share ZIP File "));
                            } catch (Exception unused) {
                            }
                            SaveFileActivity.this.dialog.dismiss();
                        }
                    });
                    relativeLayout9.setOnClickListener(new OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View view) {
                            new Builder(SaveFileActivity.this).setTitle(R.string.delete_file).setMessage(R.string.sure).setPositiveButton(17039379, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SaveFileActivity.this.onRemoveFile(file_Related_Information);
                                }
                            }).setNegativeButton(17039369, null).show();
                            SaveFileActivity.this.dialog.dismiss();
                        }
                    });
                    this.dialog.show();
                } else if (this.j != 2) {
                } else {
                    if (file_Related_Information.isFolder()) {
                        this.dialog = new Dialog(this);
                        this.dialog.requestWindowFeature(1);
                        if (VERSION.SDK_INT >= 19) {
                            Window window3 = this.dialog.getWindow();
                            window3.getClass();
                            window3.setBackgroundDrawable(new ColorDrawable(0));
                        }
                        this.dialog.setContentView(R.layout.dialog_for_compress_file);
                        TextView textView5 = (TextView) this.dialog.findViewById(R.id.name_of_selected_item);
                        ImageView imageView15 = (ImageView) this.dialog.findViewById(R.id.icon_of_item);
                        RelativeLayout relativeLayout14 = (RelativeLayout) this.dialog.findViewById(R.id.open_file);
                        TextView textView6 = (TextView) this.dialog.findViewById(R.id.size_of_file);
                        RelativeLayout relativeLayout15 = (RelativeLayout) this.dialog.findViewById(R.id.compress_7z);
                        RelativeLayout relativeLayout16 = (RelativeLayout) this.dialog.findViewById(R.id.compress_zip);
                        RelativeLayout relativeLayout17 = (RelativeLayout) this.dialog.findViewById(R.id.compress_gzip);
                        RelativeLayout relativeLayout18 = (RelativeLayout) this.dialog.findViewById(R.id.compress_tar_gz);
                        RelativeLayout relativeLayout19 = (RelativeLayout) this.dialog.findViewById(R.id.delete);
                        ((RelativeLayout) this.dialog.findViewById(R.id.share)).setVisibility(8);
                        this.dialog.findViewById(R.id.v4).setVisibility(8);
                        ImageView imageView16 = (ImageView) this.dialog.findViewById(R.id.croun1_back);
                        ImageView imageView17 = (ImageView) this.dialog.findViewById(R.id.croun1);
                        ImageView imageView18 = (ImageView) this.dialog.findViewById(R.id.croun2_back);
                        ImageView imageView19 = (ImageView) this.dialog.findViewById(R.id.croun2);
                        RelativeLayout relativeLayout20 = relativeLayout19;
                        ImageView imageView20 = (ImageView) this.dialog.findViewById(R.id.croun3_back);
                        RelativeLayout relativeLayout21 = relativeLayout18;
                        ImageView imageView21 = (ImageView) this.dialog.findViewById(R.id.croun3);
                        RelativeLayout relativeLayout22 = relativeLayout17;
                        RelativeLayout relativeLayout23 = relativeLayout16;
                        this.adv_purchase = Boolean.valueOf(this.adv_pref.getBoolean("adv_preferance", false));
                        if (this.adv_purchase.booleanValue()) {
                            imageView16.setVisibility(8);
                            imageView17.setVisibility(8);
                            imageView18.setVisibility(8);
                            imageView19.setVisibility(8);
                            imageView20.setVisibility(8);
                            imageView21.setVisibility(8);
                        }
                        this.file_name = file_Related_Information.getFileName();
                        this.extention_name = file_Related_Information.getFileName();
                        if (this.file_name.indexOf(".") > 0) {
                            this.file_name = this.file_name.substring(0, this.file_name.lastIndexOf("."));
                            this.extention_name = this.extention_name.substring(this.extention_name.lastIndexOf("."));
                        }
                        textView5.setText(this.file_name);
                        float parseInt3 = (float) Integer.parseInt(String.valueOf(new File(file_Related_Information.getFilePath()).length() / 1024));
                        if (parseInt3 >= 1024.0f) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(new DecimalFormat("##.##").format((double) (parseInt3 / 1024.0f)));
                            sb5.append(" Mb");
                            textView6.setText(sb5.toString());
                        } else {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(parseInt3);
                            sb6.append(" Kb");
                            textView6.setText(sb6.toString());
                        }
                        switch (file_Related_Information.getFileType()) {
                            case folderFull:
                                imageView15.setImageResource(R.drawable.icon_folder_full);
                                break;
                            case folderEmpty:
                                imageView15.setImageResource(R.drawable.icon_folder_empty);
                                break;
                        }
                        relativeLayout14.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                SaveFileActivity.this.loadPathInfo(file_Related_Information.getFilePath());
                                SaveFileActivity.this.dialog.dismiss();
                            }
                        });
                        relativeLayout15.setOnClickListener(new OnClickListener() {
                            @SuppressLint("ResourceType")
                            public void onClick(View view) {
                                Builder builder = new Builder(SaveFileActivity.this);
                                builder.setTitle("Enter Output File Name");
                                View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                builder.setView(inflate);
                                builder.setCancelable(false);
                                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (editText.getText().toString().isEmpty()) {
                                            Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                            return;
                                        }
                                        SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(editText.getText().toString());
                                        sb.append(SaveFileActivity.this.extention_name);
                                        save_File_Activity.Edited_File_Name = sb.toString();
                                        SaveFileActivity.this.onCompressFile(file_Related_Information);
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.show();
                                SaveFileActivity.this.dialog.dismiss();
                            }
                        });
                        relativeLayout23.setOnClickListener(new OnClickListener() {
                            @SuppressLint("ResourceType")
                            public void onClick(View view) {
                                SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                                if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                    Builder builder = new Builder(SaveFileActivity.this);
                                    builder.setTitle("Enter Output File Name");
                                    View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                    final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                    builder.setView(inflate);
                                    builder.setCancelable(false);
                                    builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (editText.getText().toString().isEmpty()) {
                                                Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                                return;
                                            }
                                            SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(editText.getText().toString());
                                            sb.append(SaveFileActivity.this.extention_name);
                                            save_File_Activity.Edited_File_Name = sb.toString();
                                            SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                            String filePath = file_Related_Information.getFilePath();
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(SaveFileActivity.this.Compress);
                                            sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                            sb2.append(SaveFileActivity.this.Edited_File_Name);
                                            sb2.append(".zip");
                                            new ZipAsynkPerform(save_File_Activity2, filePath, sb2.toString(), "").execute(new Void[0]);
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    builder.show();
                                    SaveFileActivity.this.dialog.dismiss();
                                    return;
                                }
                                SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                            }
                        });
                        relativeLayout22.setOnClickListener(new OnClickListener() {
                            @SuppressLint("ResourceType")
                            public void onClick(View view) {
                                SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                                if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                    Builder builder = new Builder(SaveFileActivity.this);
                                    builder.setTitle("Enter Output File Name");
                                    View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                    final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                    builder.setView(inflate);
                                    builder.setCancelable(false);
                                    builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (editText.getText().toString().isEmpty()) {
                                                Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                                return;
                                            }
                                            SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(editText.getText().toString());
                                            sb.append(SaveFileActivity.this.extention_name);
                                            save_File_Activity.Edited_File_Name = sb.toString();
                                            SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                            String filePath = file_Related_Information.getFilePath();
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(SaveFileActivity.this.Compress);
                                            sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                            sb2.append(SaveFileActivity.this.Edited_File_Name);
                                            sb2.append(".gzip");
                                            new GzipAsynkPerform(save_File_Activity2, filePath, sb2.toString()).execute(new Void[0]);
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    builder.show();
                                    SaveFileActivity.this.dialog.dismiss();
                                    return;
                                }
                                SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                            }
                        });
                        relativeLayout21.setOnClickListener(new OnClickListener() {
                            @SuppressLint("ResourceType")
                            public void onClick(View view) {
                                SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                                if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                    Builder builder = new Builder(SaveFileActivity.this);
                                    builder.setTitle("Enter Output File Name");
                                    View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                    final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                    builder.setView(inflate);
                                    builder.setCancelable(false);
                                    builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (editText.getText().toString().isEmpty()) {
                                                Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                                return;
                                            }
                                            SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                            StringBuilder sb = new StringBuilder();
                                            sb.append(editText.getText().toString());
                                            sb.append(SaveFileActivity.this.extention_name);
                                            save_File_Activity.Edited_File_Name = sb.toString();
                                            SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                            String filePath = file_Related_Information.getFilePath();
                                            StringBuilder sb2 = new StringBuilder();
                                            sb2.append(SaveFileActivity.this.Compress);
                                            sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                            sb2.append(SaveFileActivity.this.Edited_File_Name);
                                            sb2.append(".tar.gz");
                                            new TarGzAsynkPerform(save_File_Activity2, filePath, sb2.toString()).execute(new Void[0]);
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    builder.show();
                                    SaveFileActivity.this.dialog.dismiss();
                                    return;
                                }
                                SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                            }
                        });
                        relativeLayout20.setOnClickListener(new OnClickListener() {
                            @SuppressLint("ResourceType")
                            public void onClick(View view) {
                                new Builder(SaveFileActivity.this).setTitle(R.string.delete_file).setMessage(R.string.sure).setPositiveButton(17039379, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SaveFileActivity.this.onRemoveFile(file_Related_Information);
                                    }
                                }).setNegativeButton(17039369, null).show();
                                SaveFileActivity.this.dialog.cancel();
                            }
                        });
                        this.dialog.show();
                        return;
                    }
                    this.dialog = new Dialog(this);
                    this.dialog.requestWindowFeature(1);
                    if (VERSION.SDK_INT >= 19) {
                        Window window4 = this.dialog.getWindow();
                        window4.getClass();
                        window4.setBackgroundDrawable(new ColorDrawable(0));
                    }
                    this.dialog.setContentView(R.layout.dialog_for_compress_file);
                    TextView textView7 = (TextView) this.dialog.findViewById(R.id.name_of_selected_item);
                    ImageView imageView22 = (ImageView) this.dialog.findViewById(R.id.icon_of_item);
                    TextView textView8 = (TextView) this.dialog.findViewById(R.id.size_of_file);
                    RelativeLayout relativeLayout24 = (RelativeLayout) this.dialog.findViewById(R.id.open_file);
                    RelativeLayout relativeLayout25 = (RelativeLayout) this.dialog.findViewById(R.id.compress_7z);
                    ImageView imageView23 = (ImageView) this.dialog.findViewById(R.id.croun1_back);
                    ImageView imageView24 = (ImageView) this.dialog.findViewById(R.id.croun1);
                    ImageView imageView25 = (ImageView) this.dialog.findViewById(R.id.croun2_back);
                    RelativeLayout relativeLayout26 = (RelativeLayout) this.dialog.findViewById(R.id.delete);
                    ImageView imageView26 = (ImageView) this.dialog.findViewById(R.id.croun2);
                    RelativeLayout relativeLayout27 = (RelativeLayout) this.dialog.findViewById(R.id.share);
                    ImageView imageView27 = (ImageView) this.dialog.findViewById(R.id.croun3_back);
                    RelativeLayout relativeLayout28 = (RelativeLayout) this.dialog.findViewById(R.id.compress_tar_gz);
                    ImageView imageView28 = (ImageView) this.dialog.findViewById(R.id.croun3);
                    RelativeLayout relativeLayout29 = (RelativeLayout) this.dialog.findViewById(R.id.compress_gzip);
                    RelativeLayout relativeLayout30 = (RelativeLayout) this.dialog.findViewById(R.id.compress_zip);
                    this.adv_purchase = Boolean.valueOf(this.adv_pref.getBoolean("adv_preferance", false));
                    if (this.adv_purchase.booleanValue()) {
                        imageView23.setVisibility(8);
                        imageView24.setVisibility(8);
                        imageView25.setVisibility(8);
                        imageView26.setVisibility(8);
                        imageView27.setVisibility(8);
                        imageView28.setVisibility(8);
                    }
                    this.file_name = file_Related_Information.getFileName();
                    if (this.file_name.indexOf(".") > 0) {
                        this.file_name = this.file_name.substring(0, this.file_name.lastIndexOf("."));
                    }
                    this.extention_name = file_Related_Information.getFileName();
                    try {
                        this.extention_name = this.extention_name.substring(this.extention_name.lastIndexOf("."));
                    } catch (StringIndexOutOfBoundsException unused2) {
                        this.extention_name = "";
                    }
                    textView7.setText(this.file_name);
                    float parseInt4 = (float) Integer.parseInt(String.valueOf(new File(file_Related_Information.getFilePath()).length() / 1024));
                    if (parseInt4 >= 1024.0f) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(new DecimalFormat("##.##").format((double) (parseInt4 / 1024.0f)));
                        sb7.append(" Mb");
                        textView8.setText(sb7.toString());
                    } else {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append(parseInt4);
                        sb8.append(" Kb");
                        textView8.setText(sb8.toString());
                    }
                    String filePath2 = file_Related_Information.getFilePath();
                    if (file_Related_Information.getFileName().endsWith("pdf")) {
                        imageView22.setImageResource(R.drawable.pdf);
                    } else if (file_Related_Information.getFileName().endsWith("ppt")) {
                        imageView22.setImageResource(R.drawable.ppt);
                    } else if (file_Related_Information.getFileName().endsWith("doc")) {
                        imageView22.setImageResource(R.drawable.doc);
                    } else if (file_Related_Information.getFileName().endsWith("docx")) {
                        imageView22.setImageResource(R.drawable.docx);
                    } else if (file_Related_Information.getFileName().endsWith("msg")) {
                        imageView22.setImageResource(R.drawable.msg);
                    } else if (file_Related_Information.getFileName().endsWith("txt")) {
                        imageView22.setImageResource(R.drawable.txt);
                    } else if (file_Related_Information.getFileName().endsWith("mp4") || file_Related_Information.getFileName().endsWith("avi") || file_Related_Information.getFileName().endsWith("mpg") || file_Related_Information.getFileName().endsWith("mov") || file_Related_Information.getFileName().endsWith("wmv")) {
                        ((RequestBuilder) Glide.with((FragmentActivity) this).asBitmap()).load(Uri.fromFile(new File(filePath2))).into(imageView22);
                    } else if (file_Related_Information.getFileName().endsWith("jpg") || file_Related_Information.getFileName().endsWith("png")) {
                        ((RequestBuilder) ((RequestBuilder) Glide.with((FragmentActivity) this).load(filePath2))).into(imageView22);
                    } else if (file_Related_Information.getFileName().endsWith("wpd")) {
                        imageView22.setImageResource(R.drawable.wpd);
                    } else if (file_Related_Information.getFileName().endsWith("xls")) {
                        imageView22.setImageResource(R.drawable.xls);
                    } else if (file_Related_Information.getFileName().endsWith("xlsx")) {
                        imageView22.setImageResource(R.drawable.xlsx);
                    } else if (file_Related_Information.getFileName().endsWith("odf")) {
                        imageView22.setImageResource(R.drawable.odf);
                    } else if (file_Related_Information.getFileName().endsWith("odt")) {
                        imageView22.setImageResource(R.drawable.odt);
                    } else if (file_Related_Information.getFileName().endsWith("rtf")) {
                        imageView22.setImageResource(R.drawable.rtf);
                    } else if (file_Related_Information.getFileName().endsWith("apk")) {
                        imageView22.setImageResource(R.drawable.apk);
                    } else if (file_Related_Information.getFileName().endsWith("pptx")) {
                        imageView22.setImageResource(R.drawable.pptx);
                    } else {
                        imageView22.setImageResource(R.drawable.icon_unknown);
                    }
                    switch (file_Related_Information.getFileType()) {
                        case folderFull:
                            imageView22.setImageResource(R.drawable.icon_folder_full);
                            break;
                        case folderEmpty:
                            imageView22.setImageResource(R.drawable.icon_folder_empty);
                            break;
                    }
                    relativeLayout24.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            if (VERSION.SDK_INT >= 24) {
                                try {
                                    StrictMode.class.getMethod("disableDeathOnFileUriExposure", new Class[0]).invoke(null, new Object[0]);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            String filePath = file_Related_Information.getFilePath();
                            SaveFileActivity save_File_Activity = SaveFileActivity.this;
                            StringBuilder sb = new StringBuilder();
                            sb.append("");
                            sb.append(file_Related_Information.getFilePath());
                            Toast.makeText(save_File_Activity, sb.toString(), 1).show();
                            try {
                                FileOpen.openFile(SaveFileActivity.this, new File(filePath));
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                            SaveFileActivity.this.dialog.dismiss();
                        }
                    });
                    relativeLayout25.setOnClickListener(new OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View view) {
                            Builder builder = new Builder(SaveFileActivity.this);
                            builder.setTitle("Enter Output File Name");
                            View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                            final EditText editText = (EditText) inflate.findViewById(R.id.input);
                            builder.setView(inflate);
                            builder.setCancelable(false);
                            builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (editText.getText().toString().isEmpty()) {
                                        Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                        return;
                                    }
                                    SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(editText.getText().toString());
                                    sb.append(SaveFileActivity.this.extention_name);
                                    save_File_Activity.Edited_File_Name = sb.toString();
                                    SaveFileActivity.this.onCompressFile(file_Related_Information);
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builder.show();
                            SaveFileActivity.this.dialog.dismiss();
                        }
                    });
                    relativeLayout30.setOnClickListener(new OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View view) {
                            SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                            if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                Builder builder = new Builder(SaveFileActivity.this);
                                builder.setTitle("Enter Output File Name");
                                View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                builder.setView(inflate);
                                builder.setCancelable(false);
                                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (editText.getText().toString().isEmpty()) {
                                            Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                            return;
                                        }
                                        SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(editText.getText().toString());
                                        sb.append(SaveFileActivity.this.extention_name);
                                        save_File_Activity.Edited_File_Name = sb.toString();
                                        SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                        String filePath = file_Related_Information.getFilePath();
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(SaveFileActivity.this.Compress);
                                        sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                        sb2.append(SaveFileActivity.this.Edited_File_Name);
                                        sb2.append(".zip");
                                        new ZipAsynkPerform(save_File_Activity2, filePath, sb2.toString(), "").execute(new Void[0]);
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.show();
                                SaveFileActivity.this.dialog.dismiss();
                                return;
                            }
                            SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                        }
                    });
                    relativeLayout29.setOnClickListener(new OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View view) {
                            SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                            if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                Builder builder = new Builder(SaveFileActivity.this);
                                builder.setTitle("Enter Output File Name");
                                View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                builder.setView(inflate);
                                builder.setCancelable(false);
                                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (editText.getText().toString().isEmpty()) {
                                            Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                            return;
                                        }
                                        SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(editText.getText().toString());
                                        sb.append(SaveFileActivity.this.extention_name);
                                        save_File_Activity.Edited_File_Name = sb.toString();
                                        SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                        String filePath = file_Related_Information.getFilePath();
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(SaveFileActivity.this.Compress);
                                        sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                        sb2.append(SaveFileActivity.this.Edited_File_Name);
                                        sb2.append(".gzip");
                                        new GzipAsynkPerform(save_File_Activity2, filePath, sb2.toString()).execute(new Void[0]);
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.show();
                                SaveFileActivity.this.dialog.dismiss();
                                return;
                            }
                            SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                        }
                    });
                    relativeLayout28.setOnClickListener(new OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View view) {
                            SaveFileActivity.this.adv_purchase = Boolean.valueOf(SaveFileActivity.this.adv_pref.getBoolean("adv_preferance", false));
                            if (SaveFileActivity.this.adv_purchase.booleanValue()) {
                                Builder builder = new Builder(SaveFileActivity.this);
                                builder.setTitle("Enter Output File Name");
                                View inflate = LayoutInflater.from(SaveFileActivity.this).inflate(R.layout.text_input, null, false);
                                final EditText editText = (EditText) inflate.findViewById(R.id.input);
                                builder.setView(inflate);
                                builder.setCancelable(false);
                                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (editText.getText().toString().isEmpty()) {
                                            Toast.makeText(SaveFileActivity.this, "Plaese Enter Output Name", 0).show();
                                            return;
                                        }
                                        SaveFileActivity save_File_Activity = SaveFileActivity.this;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(editText.getText().toString());
                                        sb.append(SaveFileActivity.this.extention_name);
                                        save_File_Activity.Edited_File_Name = sb.toString();
                                        SaveFileActivity save_File_Activity2 = SaveFileActivity.this;
                                        String filePath = file_Related_Information.getFilePath();
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append(SaveFileActivity.this.Compress);
                                        sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                        sb2.append(SaveFileActivity.this.Edited_File_Name);
                                        sb2.append(".tar.gz");
                                        new TarGzAsynkPerform(save_File_Activity2, filePath, sb2.toString()).execute(new Void[0]);
                                        dialogInterface.dismiss();
                                    }
                                });
                                builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                builder.show();
                                SaveFileActivity.this.dialog.dismiss();
                                return;
                            }
                            SaveFileActivity.this.Show_Advance_Feature_Unlock_Dialog();
                        }
                    });
                    relativeLayout27.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("image/*");
                            intent.putExtra("android.intent.extra.STREAM", Uri.parse(file_Related_Information.getFilePath()));
                            try {
                                SaveFileActivity.this.startActivity(Intent.createChooser(intent, "Share ZIP File "));
                            } catch (Exception unused) {
                            }
                            SaveFileActivity.this.dialog.dismiss();
                        }
                    });
                    relativeLayout26.setOnClickListener(new OnClickListener() {
                        @SuppressLint("ResourceType")
                        public void onClick(View view) {
                            new Builder(SaveFileActivity.this).setTitle(R.string.delete_file).setMessage(R.string.sure).setPositiveButton(17039379, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SaveFileActivity.this.onRemoveFile(file_Related_Information);
                                }
                            }).setNegativeButton(17039369, null).show();
                            SaveFileActivity.this.dialog.dismiss();
                        }
                    });
                    this.dialog.show();
                }
            } else if (file_Related_Information.isFolder()) {
                loadPathInfo(file_Related_Information.getFilePath());
            } else {
                if (VERSION.SDK_INT >= 24) {
                    try {
                        StrictMode.class.getMethod("disableDeathOnFileUriExposure", new Class[0]).invoke(null, new Object[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    FileOpen.openFile(this, new File(file_Related_Information.getFilePath()));
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void Show_Advance_Feature_Unlock_Dialog() {
        Log.e("un", "un");
    }

    public void onCompressFile(FileRelatedInformation file_Related_Information) {
        getWindow().addFlags(128);
        this.check = 1;
        String filePath = file_Related_Information.getFilePath();
        StringBuilder sb = new StringBuilder();
        sb.append(this.Compress);
        sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
        sb.append(this.Edited_File_Name);
        sb.append(".7z");
        runCommand(OrderCommandActivity.getCompressCmd(filePath, sb.toString(), ArchiveStreamFactory.SEVEN_Z));
    }

    @SuppressLint({"CheckResult"})
    public void onRemoveFile(final FileRelatedInformation file_Related_Information) {
        showProgressDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                String str;
                try {
                    SaveFileActivity.this.removeFile(new File(file_Related_Information.getFilePath()));
                    StringBuilder sb = new StringBuilder();
                    sb.append("Deleted: ");
                    sb.append(file_Related_Information.getFileName());
                    str = sb.toString();
                } catch (Exception e) {
                    str = e.getMessage();
                }
                observableEmitter.onNext(str);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            public void accept(String str) throws Exception {
                SaveFileActivity.this.dismissProgressDialog();
                SaveFileActivity.this.onRefresh();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
            }
        });
    }

    @SuppressLint({"CheckResult"})
    public void onRemoveFileMulti(final FileRelatedInformation file_Related_Information) {
        Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                String str;
                try {
                    SaveFileActivity.this.removeFile(new File(file_Related_Information.getFilePath()));
                    StringBuilder sb = new StringBuilder();
                    sb.append("Deleted: ");
                    sb.append(file_Related_Information.getFileName());
                    str = sb.toString();
                } catch (Exception e) {
                    str = e.getMessage();
                }
                observableEmitter.onNext(str);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            public void accept(String str) throws Exception {
                SaveFileActivity.this.onRefresh();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
            }
        });
    }

    public void removeFile(File file) throws IOException {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                for (File removeFile : file.listFiles()) {
                    removeFile(removeFile);
                }
            }
            file.delete();
        }
    }

    @SuppressLint({"CheckResult"})
    private void runCommand(final String str) {
        showProgressDialog();
        Observable.create(new ObservableOnSubscribe<Integer>() {
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(Integer.valueOf(P7ZipApi.executeCommand(str)));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            public void accept(Integer num) throws Exception {
                SaveFileActivity.this.dismissProgressDialog();
                SaveFileActivity.this.showResult(num.intValue());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
            }
        });
    }

    public void showProgressDialog() {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage(getText(R.string.progress_message));
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }

    @SuppressLint("WrongConstant")
    public void showResult(int i) {
        Editor edit = getSharedPreferences("MyPrefsFile", 0).edit();
        getWindow().clearFlags(128);
        if (i != 255) {
            switch (i) {
                case 0:
                    if (this.check == 1) {
                        edit.clear();
                        edit.putInt("idName", 1);
                        edit.apply();
                        if (longclickonce.booleanValue()) {
                            counter = 1;
                            for (int i2 = 0; i2 < this.BufferInfoList.size(); i2++) {
                                ((FileRelatedInformation) this.BufferInfoList.get(i2)).setSelected(false);
                            }
                            selecteditems.clear();
                            longclickonce = Boolean.valueOf(false);
                            fab.setVisibility(8);
                            fab.close(true);
                        }
                        Builder builder = new Builder(this);
                        builder.setMessage("File is Successfully Compressed.");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Open Folder", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SaveFileActivity.this.startActivity(new Intent(SaveFileActivity.this, AllInOneCompress.class).putExtra("option", "compress"));
                                dialogInterface.cancel();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.create().show();
                        return;
                    } else if (this.check == 2) {
                        Builder builder2 = new Builder(this);
                        builder2.setMessage("File is Successfully Extracted.");
                        builder2.setCancelable(true);
                        builder2.setPositiveButton("Open Folder", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SaveFileActivity.this.startActivity(new Intent(SaveFileActivity.this, AllInOneCompress.class).putExtra("option", "compress"));
                                dialogInterface.cancel();
                                dialogInterface.cancel();
                            }
                        });
                        builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder2.create().show();
                        return;
                    } else {
                        return;
                    }
                case 1:
                case 2:
                    return;
                default:
                    switch (i) {
                    }
                    return;
            }
        }
    }

    public void onProductPurchased(@NonNull String str, @Nullable TransactionDetails transactionDetails) {
    }

    public void onDestroy() {
        if (this.adv_billingProcessor != null) {
            this.adv_billingProcessor.release();
        }
        super.onDestroy();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (!this.adv_billingProcessor.handleActivityResult(i, i2, intent)) {
            super.onActivityResult(i, i2, intent);
        }
        super.onActivityResult(i, i2, intent);
    }
}
