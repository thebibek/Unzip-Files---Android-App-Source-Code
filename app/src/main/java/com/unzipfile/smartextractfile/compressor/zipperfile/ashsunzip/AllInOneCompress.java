package com.unzipfile.smartextractfile.compressor.zipperfile.ashsunzip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Application;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SnackbarUtils;
import com.bumptech.glide.Glide;
import com.compressor.extractor.zipers.fileAdaptersForWork.FileAdapterItemsAllInOne;
import com.compressor.extractor.zipers.fileAdaptersForWork.FileAdapterItemsAllInOneLiner;
import com.compressor.extractor.zipers.fileWork.FileRelatedInformation;
import com.compressor.extractor.zipers.utilitesWorkFile.UtilitiesFileWorker;
import com.compressor.extractor.zipers.command.OrderCommandActivity;
import com.compressor.extractor.zipers.compression.GzipAsynkPerform;
import com.compressor.extractor.zipers.compression.TarGzAsynkPerform;
import com.compressor.extractor.zipers.compression.ZipAsynkPerform;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.hzy.libp7zip.P7ZipApi;
import com.unzipfile.smartextractfile.compressor.zipperfile.R;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
public class AllInOneCompress extends AppCompatActivity implements OnClickListener {
    private static final String[] ARCHIVE_ARRAY_Alldoc = {"ppt", "pptx", "doc", "docx", "msg", "txt", "wpd", "xls", "xlsx", "odf", "odt", "rtf"};
    private static final String[] ARCHIVE_ARRAY_Audio = {"mp3", "acc", "au", "mid", "ra", "snd", "wma", "wav"};
    private static final String[] ARCHIVE_ARRAY_Compress = {"rar", ArchiveStreamFactory.ZIP, ArchiveStreamFactory.SEVEN_Z, "bz2", CompressorStreamFactory.BZIP2, "tbz2", "tbz", CompressorStreamFactory.GZIP, "gzip", "tgz", ArchiveStreamFactory.TAR, CompressorStreamFactory.XZ, "txz"};
    private static final String[] ARCHIVE_ARRAY_Image = {"bmp", "eps", "jpg", "pict", "png", "psd", "gif"};
    private static final String[] ARCHIVE_ARRAY_Pdf = {"pdf"};
    private static final String[] ARCHIVE_ARRAY_Video = {"mp4", "avi", "mpg", "mov", "wmv"};
    private InterstitialAd mInterstitialAd;
    public static int counter = 1;
    public static FloatingActionMenu fab;
    public static FloatingActionButton fab1;
    public static FloatingActionButton fab2;
    public static FloatingActionButton fab3;
    public static Boolean longclickonce = Boolean.valueOf(false);
    public static ArrayList<FileRelatedInformation> selecteditems = new ArrayList<>();
    public static String type;
    File Compress;
    String Edited_File_Name;
    File Extracted;
    String Output_path_save;
    String Path_Name_Remove_gz_From_End;
    SharedPreferences adspref;

    Editor adv_editor;
    SharedPreferences adv_pref;
    Boolean adv_purchase;
    ArrayList<FileRelatedInformation> bufferlist;
    int check = 0;
    boolean check_tar_gz = false;
    Dialog dialog;
    Editor editor;
    String extention_name;
    String file_name;
    ArrayList<FileRelatedInformation> files;
    FrameLayout fl_adplaceholder;
    ImageView grid;
    boolean gridon = true;
    FileRelatedInformation info2;
    FileAdapterItemsAllInOne mFileItemAdapter;
    FileAdapterItemsAllInOneLiner mFileItemAdapterliner;
    RecyclerView mStorageListView;
    SharedPreferences prefs;
    private ProgressDialog progressdialog;
    File root;
    private SearchView searchView;
    ImageView sort;
    File temp;
    RelativeLayout toper1_hight_measure;

    @SuppressLint({"StaticFieldLeak"})
    class FileAsync extends AsyncTask<String, String, String> {
        FileAsync() {
        }

        public void onPreExecute() {
            super.onPreExecute();
        }

        public String doInBackground(String... strArr) {
            AllInOneCompress.this.bufferlist = AllInOneCompress.this.getListFiles(AllInOneCompress.this.root);
            return null;
        }

        public void onPostExecute(String str) {

            AllInOneCompress.this.files = AllInOneCompress.this.bufferlist;
            AllInOneCompress.this.mFileItemAdapter.setDataList(AllInOneCompress.this.files);
            AllInOneCompress.this.mFileItemAdapterliner.setDataList(AllInOneCompress.this.files);
            if (AllInOneCompress.this.gridon) {
                AllInOneCompress.this.mStorageListView.swapAdapter(AllInOneCompress.this.mFileItemAdapter, true);
            } else {
                AllInOneCompress.this.mStorageListView.swapAdapter(AllInOneCompress.this.mFileItemAdapterliner, true);
            }
            if (!AllInOneCompress.this.isFinishing()) {
                AllInOneCompress.this.dismissProgressDialog();
            }
            AllInOneCompress.this.mFileItemAdapter.notifyDataSetChanged();
            AllInOneCompress.this.mFileItemAdapterliner.notifyDataSetChanged();
        }

    }

    public static class FileOpen {
        @SuppressLint("WrongConstant")
        public static void openFile(Context context, File file) throws IOException {
            Uri fromFile = Uri.fromFile(file);
            Intent intent = new Intent("android.intent.action.VIEW");
            if (file.toString().contains(".doc") || file.toString().contains(".docx")) {
                intent.setDataAndType(fromFile, "application/msword");
            } else if (file.toString().contains(".pdf")) {
                intent.setDataAndType(fromFile, "application/pdf");
            } else if (file.toString().contains(".ppt") || file.toString().contains(".pptx")) {
                intent.setDataAndType(fromFile, "application/vnd.ms-powerpoint");
            } else if (file.toString().contains(".xls") || file.toString().contains(".xlsx")) {
                intent.setDataAndType(fromFile, "application/vnd.ms-excel");
            } else if (file.toString().contains(".zip") || file.toString().contains(".rar")) {
                intent.setDataAndType(fromFile, "application/x-wav");
            } else if (file.toString().contains(".rtf")) {
                intent.setDataAndType(fromFile, "application/rtf");
            } else if (file.toString().contains(".wav") || file.toString().contains(".mp3")) {
                intent.setDataAndType(fromFile, "audio/x-wav");
            } else if (file.toString().contains(".gif")) {
                intent.setDataAndType(fromFile, "image/gif");
            } else if (file.toString().contains(".jpg") || file.toString().contains(".jpeg") || file.toString().contains(".png")) {
                intent.setDataAndType(fromFile, "image/jpeg");
            } else if (file.toString().contains(".txt")) {
                intent.setDataAndType(fromFile, "text/plain");
            } else if (file.toString().contains(".3gp") || file.toString().contains(".mpg") || file.toString().contains(".mpeg") || file.toString().contains(".mpe") || file.toString().contains(".mp4") || file.toString().contains(".avi")) {
                intent.setDataAndType(fromFile, "video/*");
            } else {
                intent.setDataAndType(fromFile, "*/*");
            }
            intent.addFlags(268435456);
            try {
                context.startActivity(intent);
            } catch (Exception unused) {
                Toast.makeText(context, "No default Application found!", 0).show();
            }
        }
    }

    private void initAdsFull() {
        mInterstitialAd = new InterstitialAd(this);
        MobileAds.initialize(this,
                getResources().getString(R.string.admob_app_id));
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_full));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });

    }

    @SuppressLint({"CommitPrefEdits"})
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Locale locale = new Locale(getSharedPreferences("enter", 0).getString("lang", "en"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        setContentView((int) R.layout.activity_all_in_one);
        //initAdsFull();
        TextView textView = findViewById(R.id.name);
        type = getIntent().getStringExtra("option");
        if (type.isEmpty()) {
            type = "alldoc";
            textView.setText(R.string.all_document_files);
        } else if (type.equals("videos")) {
            textView.setText(R.string.all_video_file);
        } else if (type.equals("compress")) {
            textView.setText(R.string.all_compressed_files);
        } else if (type.equals("audios")) {
            textView.setText(R.string.all_audio_file);
        } else if (type.equals("pdf")) {
            textView.setText(R.string.all_pdf_nfile);
        } else if (type.equals("alldoc")) {
            textView.setText(R.string.all_document_files);
        } else if (type.equals("images")) {
            textView.setText(R.string.all_image_file);
        }
        this.adv_pref = getSharedPreferences("adspreferance", 0);
        this.adv_editor = getSharedPreferences("adspreferance", 0).edit();

        this.adspref = getSharedPreferences("adspreferance", 0);
        this.fl_adplaceholder = (FrameLayout) findViewById(R.id.fl_adplaceholder);
        this.editor = getSharedPreferences("MyPrefsFile", 0).edit();
        this.root = Environment.getExternalStorageDirectory();
        StringBuilder sb = new StringBuilder();
        sb.append(this.root.getAbsolutePath());
        sb.append("/Extractor/");
        File file = new File(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.root.getAbsolutePath());
        sb2.append("/Extractor/Compressed");
        this.Compress = new File(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.root.getAbsolutePath());
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

        this.mStorageListView =  findViewById(R.id.fragment_storage_list);
        showProgressDialog(this);
        new FileAsync().execute(new String[]{this.root.getAbsolutePath()});
        this.mFileItemAdapter = new FileAdapterItemsAllInOne(this, this);
        this.mFileItemAdapterliner = new FileAdapterItemsAllInOneLiner(this, this);
        final String format = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa").format(Calendar.getInstance().getTime());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.root.getAbsolutePath());
        sb4.append("/Extractor/Multi_Compressed");
        this.temp = new File(sb4.toString());
        fab =  findViewById(R.id.menu);
        fab3 =  findViewById(R.id.share);
        fab3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ArrayList arrayList = new ArrayList();
                Iterator it = AllInOneCompress.selecteditems.iterator();
                while (it.hasNext()) {
                    File file = new File(((FileRelatedInformation) it.next()).getFilePath());
                    if (VERSION.SDK_INT < 19) {
                        arrayList.add(Uri.fromFile(file));
                    } else {
                        Application application = AllInOneCompress.this.getApplication();
                        StringBuilder sb = new StringBuilder();
                        sb.append(AllInOneCompress.this.getApplication().getPackageName());
                        sb.append(".provider");
                        arrayList.add(FileProvider.getUriForFile(application, sb.toString(), file));
                    }
                }
                Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
                intent.setType("*/*");
                intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
                AllInOneCompress.this.startActivity(Intent.createChooser(intent, "Share File"));
            }
        });
        fab1 =  findViewById(R.id.zip);
        fab1.setOnClickListener(new OnClickListener() {
            @SuppressLint("ResourceType")
            public void onClick(View view) {
                if (AllInOneCompress.this.temp.exists()) {
                    AllInOneCompress.this.removeFile(AllInOneCompress.this.temp);
                }
                if (!AllInOneCompress.this.temp.exists()) {
                    AllInOneCompress.this.temp.mkdirs();
                }
                Iterator it = AllInOneCompress.selecteditems.iterator();
                while (it.hasNext()) {
                    FileRelatedInformation file_Related_Information = (FileRelatedInformation) it.next();
                    File file = new File(file_Related_Information.getFilePath());
                    StringBuilder sb = new StringBuilder();
                    sb.append(AllInOneCompress.this.temp);
                    sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                    sb.append(file_Related_Information.getFileName());
                    try {
                        FileUtils.copyFile(file, new File(sb.toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Builder builder = new Builder(AllInOneCompress.this);
                builder.setTitle("Enter Output File Name");
                View inflate = LayoutInflater.from(AllInOneCompress.this).inflate(R.layout.text_input, null, false);
                final EditText editText = (EditText) inflate.findViewById(R.id.input);
                builder.setView(inflate);
                builder.setCancelable(false);
                builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (editText.getText().toString().isEmpty()) {
                            Toast.makeText(AllInOneCompress.this, "Plaese Enter Output Name", 0).show();
                            return;
                        }
                        dialogInterface.dismiss();
                        AllInOneCompress AllInOneCompress = AllInOneCompress.this;
                        String absolutePath = AllInOneCompress.this.temp.getAbsolutePath();
                        String str = ArchiveStreamFactory.SEVEN_Z;
                        String absolutePath2 = AllInOneCompress.this.Compress.getAbsolutePath();
                        StringBuilder sb = new StringBuilder();
                        sb.append(editText.getText().toString());
                        sb.append(" ");
                        sb.append(format);
                        AllInOneCompress.onCompressMultiFile(absolutePath, str, absolutePath2, sb.toString());
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
        fab2 =  findViewById(R.id.delete);
        fab2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Builder builder = new Builder(AllInOneCompress.this);
                builder.setMessage(AllInOneCompress.this.getString(R.string.deletesure));
                builder.setCancelable(true);
                builder.setPositiveButton(AllInOneCompress.this.getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @SuppressLint("WrongConstant")
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int i2 = 0; i2 < 1; i2++) {
                            Iterator it = AllInOneCompress.selecteditems.iterator();
                            while (it.hasNext()) {
                                AllInOneCompress.this.onRemoveFileMulti((FileRelatedInformation) it.next());
                            }
                            AllInOneCompress.this.showProgressDialog(AllInOneCompress.this);
                            new FileAsync().execute(new String[]{AllInOneCompress.this.root.getAbsolutePath()});
                        }
                        if (AllInOneCompress.longclickonce.booleanValue()) {
                            AllInOneCompress.counter = 1;
                            for (int i3 = 0; i3 < AllInOneCompress.this.bufferlist.size(); i3++) {
                                (bufferlist.get(i3)).setSelected(false);
                            }
                            AllInOneCompress.selecteditems.clear();
                            AllInOneCompress.longclickonce = Boolean.valueOf(false);
                            AllInOneCompress.fab.setVisibility(8);
                            AllInOneCompress.fab.close(true);
                        }
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton(AllInOneCompress.this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        this.prefs = getSharedPreferences("enter", 0);
        this.grid =  findViewById(R.id.grid);
        this.gridon = this.prefs.getBoolean("gride", true);
        if (this.gridon) {
            this.grid.setImageResource(R.drawable.grid_on);
            this.mStorageListView.setLayoutManager(new GridLayoutManager(this, 2));
            this.mStorageListView.setItemAnimator(new DefaultItemAnimator());
        } else {
            this.grid.setImageResource(R.drawable.grid_off);
            this.mStorageListView.setLayoutManager(new LinearLayoutManager(this));
            this.mStorageListView.setItemAnimator(new DefaultItemAnimator());
        }
        this.grid.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AllInOneCompress.this.gridon = AllInOneCompress.this.prefs.getBoolean("gride", true);
                if (AllInOneCompress.this.gridon) {
                    AllInOneCompress.this.prefs.edit().putBoolean("gride", false).apply();
                    AllInOneCompress.this.grid.setImageResource(R.drawable.grid_off);
                    AllInOneCompress.this.mStorageListView.setLayoutManager(new LinearLayoutManager(AllInOneCompress.this));
                    AllInOneCompress.this.mStorageListView.setItemAnimator(new DefaultItemAnimator());
                    AllInOneCompress.this.mStorageListView.setAdapter(mFileItemAdapterliner);
                    return;
                }
                AllInOneCompress.this.prefs.edit().putBoolean("gride", true).apply();
                AllInOneCompress.this.grid.setImageResource(R.drawable.grid_on);
                AllInOneCompress.this.mStorageListView.setLayoutManager(new GridLayoutManager(AllInOneCompress.this, 2));
                AllInOneCompress.this.mStorageListView.setItemAnimator(new DefaultItemAnimator());
                AllInOneCompress.this.mStorageListView.setAdapter(AllInOneCompress.this.mFileItemAdapter);
            }
        });
        this.sort = findViewById(R.id.sort);
        this.sort.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

            }
        });
        this.searchView = (SearchView) findViewById(R.id.search);
        this.searchView.setIconified(false);
        this.searchView.clearFocus();
        this.searchView.setOnQueryTextListener(new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String str) {
                AllInOneCompress.this.mFileItemAdapter.getFilter().filter(str);
                AllInOneCompress.this.mFileItemAdapterliner.getFilter().filter(str);
                return false;
            }

            public boolean onQueryTextChange(String str) {
                AllInOneCompress.this.mFileItemAdapter.getFilter().filter(str);
                AllInOneCompress.this.mFileItemAdapterliner.getFilter().filter(str);
                return false;
            }
        });
    }

    public ArrayList<FileRelatedInformation> getListFiles(File file) {
        ArrayList<FileRelatedInformation> arrayList = new ArrayList<>();
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                if (file2 != null) {
                    if (file2.isDirectory()) {
                        arrayList.addAll(getListFiles(file2));
                    } else {
                        if (type.equals("videos")) {
                            if (file2.getName().endsWith(ARCHIVE_ARRAY_Video[0])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Video[1])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Video[2])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Video[3])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Video[4])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            }
                        } else if (type.equals("alldoc")) {
                            if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[0])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[1])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[2])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[3])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[4])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[5])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[6])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[7])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[8])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[9])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[10])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Alldoc[11])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            }
                        } else if (type.equals("pdf")) {
                            if (file2.getName().endsWith(ARCHIVE_ARRAY_Pdf[0])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            }
                        } else if (type.equals("images")) {
                            if (file2.getName().endsWith(ARCHIVE_ARRAY_Image[0])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Image[1])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Image[2])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Image[3])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Image[4])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Image[5])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Image[6])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            }
                        } else if (type.equals("compress")) {
                            if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[0])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[1])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[2])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[3])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[4])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[5])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[6])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[7])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[8])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[9])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[10])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[11])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Compress[12])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            }
                        } else if (type.equals("audios")) {
                            if (file2.getName().endsWith(ARCHIVE_ARRAY_Audio[0])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Audio[1])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Audio[2])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Audio[3])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Audio[4])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Audio[5])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Audio[6])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            } else if (file2.getName().endsWith(ARCHIVE_ARRAY_Audio[7])) {
                                arrayList.add(UtilitiesFileWorker.getFileInfoFromPath(file2.getPath()));
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    @SuppressLint("WrongConstant")
    public void onBackPressed() {
        if (longclickonce.booleanValue()) {
            counter = 1;
            for (int i = 0; i < this.bufferlist.size(); i++) {
                ((FileRelatedInformation) this.bufferlist.get(i)).setSelected(false);
            }
            selecteditems.clear();
            longclickonce = Boolean.valueOf(false);
            fab.setVisibility(8);
            fab.close(true);
            this.files = this.bufferlist;
            this.mFileItemAdapter.setDataList(this.files);
            this.mFileItemAdapterliner.setDataList(this.files);
            this.mFileItemAdapter.notifyDataSetChanged();
            this.mFileItemAdapterliner.notifyDataSetChanged();
            return;
        }
        super.onBackPressed();
    }

    public void onExtractFile(FileRelatedInformation file_Related_Information) {
        getWindow().addFlags(128);
        this.info2 = file_Related_Information;
        StringBuilder sb = new StringBuilder();
        sb.append(this.Extracted);
        sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
        sb.append(this.Edited_File_Name);
        sb.append("-ext");
        this.Output_path_save = sb.toString();
        String extractCmd = OrderCommandActivity.getExtractCmd(file_Related_Information.getFilePath(), this.Output_path_save);
        showProgressDialog(this);
        runCommandextr(extractCmd);
    }

    @SuppressLint({"CheckResult"})
    private void runCommandextr(final String str) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(Integer.valueOf(P7ZipApi.executeCommand(str)));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            public void accept(Integer num) throws Exception {
                if (AllInOneCompress.this.check_tar_gz) {
                    AllInOneCompress.this.Again_Extract_For_Tar();
                    AllInOneCompress.this.check_tar_gz = false;
                    return;
                }
                if (!AllInOneCompress.this.isFinishing()) {
                    AllInOneCompress.this.dismissProgressDialog();
                }
                AllInOneCompress.this.showResult2(num.intValue());
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
            }
        });
    }

    public void Again_Extract_For_Tar() {
        String fileName = this.info2.getFileName();
        this.Path_Name_Remove_gz_From_End = fileName.substring(0, fileName.lastIndexOf(46));
        StringBuilder sb = new StringBuilder();
        sb.append(this.Output_path_save);
        sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
        sb.append(this.Path_Name_Remove_gz_From_End);
        String extractCmd = OrderCommandActivity.getExtractCmd(sb.toString(), this.Output_path_save);
        showProgressDialog(this);
        runCommandextr(extractCmd);
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view) {
        if (type.equals("compress")) {
            if (longclickonce.booleanValue()) {
                int intValue = ((Integer) view.getTag()).intValue();
                if (intValue < this.bufferlist.size()) {
                    fab.setVisibility(0);
                    fab1.setVisibility(8);
                    fab.close(true);
                    if (!((FileRelatedInformation) this.bufferlist.get(intValue)).isSelected()) {
                        ((FileRelatedInformation) this.bufferlist.get(intValue)).setSelected(true);
                        counter++;
                        longclickonce = Boolean.valueOf(true);
                        selecteditems.add(this.bufferlist.get(intValue));
                    } else {
                        selecteditems.remove(this.bufferlist.get(intValue));
                        counter--;
                        ((FileRelatedInformation) this.bufferlist.get(intValue)).setSelected(false);
                        if (counter == 1) {
                            selecteditems.clear();
                            longclickonce = Boolean.valueOf(false);
                            fab.setVisibility(8);
                            fab.close(true);
                            this.files = this.bufferlist;
                        }
                    }
                    this.files = this.bufferlist;
                    this.mFileItemAdapter.notifyDataSetChanged();
                    this.mFileItemAdapterliner.notifyDataSetChanged();
                    return;
                }
                return;
            }
            int intValue2 = ((Integer) view.getTag()).intValue();
            if (intValue2 < this.bufferlist.size()) {
                final FileRelatedInformation file_Related_Information = (FileRelatedInformation) this.bufferlist.get(intValue2);
                final Dialog dialog2 = new Dialog(this);
                dialog2.requestWindowFeature(1);
                if (VERSION.SDK_INT >= 19) {
                    Window window = dialog2.getWindow();
                    window.getClass();
                    window.setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog2.setContentView(R.layout.dialog_for_extraction_file);
                TextView textView = (TextView) dialog2.findViewById(R.id.name_of_selected_item);
                this.toper1_hight_measure = (RelativeLayout) dialog2.findViewById(R.id.toper1_hight_measure);
                TextView textView2 = (TextView) dialog2.findViewById(R.id.size_of_file);
                RelativeLayout relativeLayout = (RelativeLayout) dialog2.findViewById(R.id.extract_file);
                RelativeLayout relativeLayout2 = (RelativeLayout) dialog2.findViewById(R.id.share);
                RelativeLayout relativeLayout3 = (RelativeLayout) dialog2.findViewById(R.id.delete);
                ((ImageView) dialog2.findViewById(R.id.icon_of_item)).setImageResource(R.drawable.icon_file_archive);
                this.file_name = file_Related_Information.getFileName();
                this.extention_name = file_Related_Information.getFileName();
                if (this.file_name.indexOf(".") > 0) {
                    this.file_name = this.file_name.substring(0, this.file_name.lastIndexOf("."));
                    String[] split = this.extention_name.split("\\.");
                    StringBuilder sb = new StringBuilder();
                    sb.append(".");
                    sb.append(split[split.length - 1]);
                    this.extention_name = sb.toString();
                }
                textView.setText(this.file_name);
                float parseInt = (float) Integer.parseInt(String.valueOf(new File(file_Related_Information.getFilePath()).length() / 1024));
                if (parseInt >= 1024.0f) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(new DecimalFormat("##.##").format((double) (parseInt / 1024.0f)));
                    sb2.append(" Mb");
                    textView2.setText(sb2.toString());
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(parseInt);
                    sb3.append(" Kb");
                    textView2.setText(sb3.toString());
                }
                relativeLayout.setOnClickListener(new OnClickListener() {
                    @SuppressLint("ResourceType")
                    public void onClick(View view) {
                        Builder builder = new Builder(AllInOneCompress.this);
                        builder.setTitle("Enter Output File Name");
                        View inflate = LayoutInflater.from(AllInOneCompress.this).inflate(R.layout.text_input, null, false);
                        final EditText editText = (EditText) inflate.findViewById(R.id.input);
                        builder.setView(inflate);
                        builder.setCancelable(false);
                        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (editText.getText().toString().isEmpty()) {
                                    Toast.makeText(AllInOneCompress.this, "Plaese Enter Output Name", 0).show();
                                    return;
                                }
                                try {
                                    AllInOneCompress AllInOneCompress = AllInOneCompress.this;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(editText.getText().toString());
                                    sb.append(AllInOneCompress.this.extention_name);
                                    AllInOneCompress.Edited_File_Name = sb.toString();
                                    String filePath = file_Related_Information.getFilePath();
                                    if (filePath.substring(filePath.lastIndexOf(".")).equals(".gz")) {
                                        AllInOneCompress.this.check_tar_gz = true;
                                        AllInOneCompress.this.onExtractFile(file_Related_Information);
                                    } else {
                                        AllInOneCompress.this.onExtractFile(file_Related_Information);
                                    }
                                } catch (StringIndexOutOfBoundsException unused) {
                                    Toast.makeText(AllInOneCompress.this, "Some Error Occur. Please Try Again Later", 0).show();
                                }
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
                        dialog2.dismiss();
                    }
                });
                relativeLayout2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("image/*");
                        if (VERSION.SDK_INT < 19) {
                            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(file_Related_Information.getFilePath())));
                        } else {
                            Application application = AllInOneCompress.this.getApplication();
                            StringBuilder sb = new StringBuilder();
                            sb.append(AllInOneCompress.this.getApplication().getPackageName());
                            sb.append(".provider");
                            intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(application, sb.toString(), new File(file_Related_Information.getFilePath())));
                        }
                        try {
                            AllInOneCompress.this.startActivity(Intent.createChooser(intent, "Share File "));
                        } catch (Exception unused) {
                        }
                        dialog2.dismiss();
                    }
                });
                relativeLayout3.setOnClickListener(new OnClickListener() {
                    @SuppressLint("ResourceType")
                    public void onClick(View view) {
                        new Builder(AllInOneCompress.this).setTitle(R.string.delete_file).setMessage(R.string.sure).setPositiveButton(17039379, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AllInOneCompress.this.onRemoveFile(file_Related_Information);
                            }
                        }).setNegativeButton(17039369, null).show();
                        dialog2.dismiss();
                    }
                });
                dialog2.show();
            }
        } else if (longclickonce.booleanValue()) {
            int intValue3 = ((Integer) view.getTag()).intValue();
            if (intValue3 < this.bufferlist.size()) {
                fab.setVisibility(0);
                fab.close(true);
                if (!((FileRelatedInformation) this.bufferlist.get(intValue3)).isSelected()) {
                    ((FileRelatedInformation) this.bufferlist.get(intValue3)).setSelected(true);
                    counter++;
                    longclickonce = Boolean.valueOf(true);
                    selecteditems.add(this.bufferlist.get(intValue3));
                } else {
                    selecteditems.remove(this.bufferlist.get(intValue3));
                    counter--;
                    ((FileRelatedInformation) this.bufferlist.get(intValue3)).setSelected(false);
                    if (counter == 1) {
                        selecteditems.clear();
                        longclickonce = Boolean.valueOf(false);
                        fab.setVisibility(8);
                        fab.close(true);
                        this.files = this.bufferlist;
                    }
                }
                this.files = this.bufferlist;
                this.mFileItemAdapter.notifyDataSetChanged();
                this.mFileItemAdapterliner.notifyDataSetChanged();
            }
        } else {
            int intValue4 = ((Integer) view.getTag()).intValue();
            if (intValue4 < this.bufferlist.size()) {
                final FileRelatedInformation file_Related_Information2 = (FileRelatedInformation) this.bufferlist.get(intValue4);
                this.dialog = new Dialog(this);
                this.dialog.requestWindowFeature(1);
                if (VERSION.SDK_INT >= 19) {
                    Window window2 = this.dialog.getWindow();
                    window2.getClass();
                    window2.setBackgroundDrawable(new ColorDrawable(0));
                }
                this.dialog.setContentView(R.layout.dialog_for_compress_file);
                TextView textView3 = (TextView) this.dialog.findViewById(R.id.name_of_selected_item);
                TextView textView4 = (TextView) this.dialog.findViewById(R.id.size_of_file);
                ImageView imageView = (ImageView) this.dialog.findViewById(R.id.icon_of_item);
                RelativeLayout relativeLayout4 = (RelativeLayout) this.dialog.findViewById(R.id.open_file);
                RelativeLayout relativeLayout5 = (RelativeLayout) this.dialog.findViewById(R.id.compress_7z);
                ImageView imageView2 = (ImageView) this.dialog.findViewById(R.id.croun1_back);
                ImageView imageView3 = (ImageView) this.dialog.findViewById(R.id.croun1);
                ImageView imageView4 = (ImageView) this.dialog.findViewById(R.id.croun2_back);
                RelativeLayout relativeLayout6 = (RelativeLayout) this.dialog.findViewById(R.id.delete);
                ImageView imageView5 = (ImageView) this.dialog.findViewById(R.id.croun2);
                RelativeLayout relativeLayout7 = (RelativeLayout) this.dialog.findViewById(R.id.share);
                ImageView imageView6 = (ImageView) this.dialog.findViewById(R.id.croun3_back);
                RelativeLayout relativeLayout8 = (RelativeLayout) this.dialog.findViewById(R.id.compress_tar_gz);
                ImageView imageView7 = (ImageView) this.dialog.findViewById(R.id.croun3);
                RelativeLayout relativeLayout9 = (RelativeLayout) this.dialog.findViewById(R.id.compress_gzip);
                RelativeLayout relativeLayout10 = (RelativeLayout) this.dialog.findViewById(R.id.compress_zip);
                this.adv_purchase = Boolean.valueOf(this.adv_pref.getBoolean("adv_preferance", false));
                if (this.adv_purchase.booleanValue()) {
                    imageView2.setVisibility(8);
                    imageView3.setVisibility(8);
                    imageView4.setVisibility(8);
                    imageView5.setVisibility(8);
                    imageView6.setVisibility(8);
                    imageView7.setVisibility(8);
                }
                this.file_name = file_Related_Information2.getFileName();
                if (this.file_name.indexOf(".") > 0) {
                    this.file_name = this.file_name.substring(0, this.file_name.lastIndexOf("."));
                }
                this.extention_name = file_Related_Information2.getFileName();
                if (this.extention_name.indexOf(".") > 0) {
                    String[] split2 = this.extention_name.split("\\.");
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(".");
                    sb4.append(split2[split2.length - 1]);
                    this.extention_name = sb4.toString();
                }
                textView3.setText(this.file_name);
                float parseInt2 = (float) Integer.parseInt(String.valueOf(new File(file_Related_Information2.getFilePath()).length() / 1024));
                if (parseInt2 >= 1024.0f) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(new DecimalFormat("##.##").format((double) (parseInt2 / 1024.0f)));
                    sb5.append(" Mb");
                    textView4.setText(sb5.toString());
                } else {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(parseInt2);
                    sb6.append(" Kb");
                    textView4.setText(sb6.toString());
                }
                String filePath = file_Related_Information2.getFilePath();
                if (file_Related_Information2.getFileName().endsWith("mp4") || file_Related_Information2.getFileName().endsWith("avi") || file_Related_Information2.getFileName().endsWith("mpg") || file_Related_Information2.getFileName().endsWith("mov") || file_Related_Information2.getFileName().endsWith("wmv")) {
                    Glide.with((FragmentActivity) this).asBitmap().load(Uri.fromFile(new File(filePath))).into(imageView);
                } else if (file_Related_Information2.getFileName().endsWith("ppt")) {
                    imageView.setImageResource(R.drawable.ppt);
                } else if (file_Related_Information2.getFileName().endsWith("pptx")) {
                    imageView.setImageResource(R.drawable.pptx);
                } else if (file_Related_Information2.getFileName().endsWith("doc")) {
                    imageView.setImageResource(R.drawable.doc);
                } else if (file_Related_Information2.getFileName().endsWith("docx")) {
                    imageView.setImageResource(R.drawable.docx);
                } else if (file_Related_Information2.getFileName().endsWith("msg")) {
                    imageView.setImageResource(R.drawable.msg);
                } else if (file_Related_Information2.getFileName().endsWith("txt")) {
                    imageView.setImageResource(R.drawable.txt);
                } else if (file_Related_Information2.getFileName().endsWith("wpd")) {
                    imageView.setImageResource(R.drawable.wpd);
                } else if (file_Related_Information2.getFileName().endsWith("xls")) {
                    imageView.setImageResource(R.drawable.xls);
                } else if (file_Related_Information2.getFileName().endsWith("xlsx")) {
                    imageView.setImageResource(R.drawable.xlsx);
                } else if (file_Related_Information2.getFileName().endsWith("odf")) {
                    imageView.setImageResource(R.drawable.odf);
                } else if (file_Related_Information2.getFileName().endsWith("odt")) {
                    imageView.setImageResource(R.drawable.odt);
                } else if (file_Related_Information2.getFileName().endsWith("rtf")) {
                    imageView.setImageResource(R.drawable.rtf);
                }
                relativeLayout4.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (VERSION.SDK_INT >= 24) {
                            try {
                                StrictMode.class.getMethod("disableDeathOnFileUriExposure", new Class[0]).invoke(null, new Object[0]);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            FileOpen.openFile(AllInOneCompress.this, new File(file_Related_Information2.getFilePath()));
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        AllInOneCompress.this.dialog.cancel();
                    }
                });
                relativeLayout5.setOnClickListener(new OnClickListener() {
                    @SuppressLint("ResourceType")
                    public void onClick(View view) {
                        Builder builder = new Builder(AllInOneCompress.this);
                        builder.setTitle("Enter Output File Name");
                        View inflate = LayoutInflater.from(AllInOneCompress.this).inflate(R.layout.text_input, null, false);
                        final EditText editText = (EditText) inflate.findViewById(R.id.input);
                        builder.setView(inflate);
                        builder.setCancelable(false);
                        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (editText.getText().toString().isEmpty()) {
                                    Toast.makeText(AllInOneCompress.this, "Plaese Enter Output Name", 0).show();
                                    return;
                                }
                                AllInOneCompress AllInOneCompress = AllInOneCompress.this;
                                StringBuilder sb = new StringBuilder();
                                sb.append(editText.getText().toString());
                                sb.append(AllInOneCompress.this.extention_name);
                                AllInOneCompress.Edited_File_Name = sb.toString();
                                AllInOneCompress.this.onCompressSingleFile(file_Related_Information2);
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
                        AllInOneCompress.this.dialog.dismiss();
                    }
                });
                relativeLayout10.setOnClickListener(new OnClickListener() {
                    @SuppressLint("ResourceType")
                    public void onClick(View view) {
                        String str = "";
                        Builder builder = new Builder(AllInOneCompress.this);
                        builder.setTitle("Enter Output File Name");
                        View inflate = LayoutInflater.from(AllInOneCompress.this).inflate(R.layout.text_input, null, true);
                        final EditText editText = (EditText) inflate.findViewById(R.id.input);
                        builder.setView(inflate);
                        builder.setCancelable(true);
                        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (editText.getText().toString().isEmpty()) {
                                    Toast.makeText(AllInOneCompress.this, "Plaese Enter Output Name", 0).show();
                                    return;
                                }
                                AllInOneCompress AllInOneCompress = AllInOneCompress.this;
                                StringBuilder sb = new StringBuilder();
                                sb.append(editText.getText().toString());
                                sb.append(AllInOneCompress.this.extention_name);
                                AllInOneCompress.Edited_File_Name = sb.toString();
                                AllInOneCompress allInOneCompress2 = AllInOneCompress.this;
                                String filePath = file_Related_Information2.getFilePath();
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(AllInOneCompress.this.Compress);
                                sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                sb2.append(AllInOneCompress.this.Edited_File_Name);
                                sb2.append(".zip");
                                new ZipAsynkPerform(allInOneCompress2, filePath, sb2.toString(), "").execute(new Void[0]);
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
                        AllInOneCompress.this.dialog.dismiss();
                    }
                });
                relativeLayout9.setOnClickListener(new OnClickListener() {
                    @SuppressLint("ResourceType")
                    public void onClick(View view) {
                        AllInOneCompress.this.adv_purchase = Boolean.valueOf(AllInOneCompress.this.adv_pref.getBoolean("adv_preferance", true));
                        if (AllInOneCompress.this.adv_purchase.booleanValue()) {
                            Builder builder = new Builder(AllInOneCompress.this);
                            builder.setTitle("Enter Output File Name");
                            View inflate = LayoutInflater.from(AllInOneCompress.this).inflate(R.layout.text_input, null, true);
                            final EditText editText = (EditText) inflate.findViewById(R.id.input);
                            builder.setView(inflate);
                            builder.setCancelable(true);
                            builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (editText.getText().toString().isEmpty()) {
                                        Toast.makeText(AllInOneCompress.this, "Plaese Enter Output Name", 0).show();
                                        return;
                                    }
                                    AllInOneCompress AllInOneCompress = AllInOneCompress.this;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(editText.getText().toString());
                                    sb.append(AllInOneCompress.this.extention_name);
                                    AllInOneCompress.Edited_File_Name = sb.toString();
                                    AllInOneCompress allInOneCompress2 = AllInOneCompress.this;
                                    String filePath = file_Related_Information2.getFilePath();
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(AllInOneCompress.this.Compress);
                                    sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                    sb2.append(AllInOneCompress.this.Edited_File_Name);
                                    sb2.append(".gzip");
                                    new GzipAsynkPerform(allInOneCompress2, filePath, sb2.toString()).execute(new Void[0]);
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builder.show();
                            AllInOneCompress.this.dialog.dismiss();
                            return;
                        }
                        AllInOneCompress.this.Show_Advance_Feature_Unlock_Dialog();
                    }
                });
                relativeLayout8.setOnClickListener(new OnClickListener() {
                    @SuppressLint("ResourceType")
                    public void onClick(View view) {
                        AllInOneCompress.this.adv_purchase = Boolean.valueOf(AllInOneCompress.this.adv_pref.getBoolean("adv_preferance", true));
                        if (AllInOneCompress.this.adv_purchase.booleanValue()) {
                            Builder builder = new Builder(AllInOneCompress.this);
                            builder.setTitle("Enter Output File Name");
                            View inflate = LayoutInflater.from(AllInOneCompress.this).inflate(R.layout.text_input, null, true);
                            final EditText editText = (EditText) inflate.findViewById(R.id.input);
                            builder.setView(inflate);
                            builder.setCancelable(true);
                            builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (editText.getText().toString().isEmpty()) {
                                        Toast.makeText(AllInOneCompress.this, "Plaese Enter Output Name", 0).show();
                                        return;
                                    }
                                    AllInOneCompress AllInOneCompress = AllInOneCompress.this;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(editText.getText().toString());
                                    sb.append(extention_name);
                                    AllInOneCompress.Edited_File_Name = sb.toString();
                                    AllInOneCompress allInOneCompress2 = AllInOneCompress.this;
                                    String filePath = file_Related_Information2.getFilePath();
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(AllInOneCompress.this.Compress);
                                    sb2.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
                                    sb2.append(AllInOneCompress.this.Edited_File_Name);
                                    sb2.append(".tar.gz");
                                    new TarGzAsynkPerform(allInOneCompress2, filePath, sb2.toString()).execute(new Void[0]);
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builder.show();
                            AllInOneCompress.this.dialog.dismiss();
                            return;
                        }
                        AllInOneCompress.this.Show_Advance_Feature_Unlock_Dialog();
                    }
                });
                relativeLayout7.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("image/*");
                        if (VERSION.SDK_INT < 19) {
                            intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(file_Related_Information2.getFilePath())));
                        } else {
                            Application application = AllInOneCompress.this.getApplication();
                            StringBuilder sb = new StringBuilder();
                            sb.append(AllInOneCompress.this.getApplication().getPackageName());
                            sb.append(".provider");
                            intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(application, sb.toString(), new File(file_Related_Information2.getFilePath())));
                        }
                        try {
                            AllInOneCompress.this.startActivity(Intent.createChooser(intent, "Share File "));
                        } catch (Exception unused) {
                        }
                        AllInOneCompress.this.dialog.dismiss();
                    }
                });
                relativeLayout6.setOnClickListener(new OnClickListener() {
                    @SuppressLint("ResourceType")
                    public void onClick(View view) {
                        new Builder(AllInOneCompress.this).setTitle(R.string.delete_file).setMessage(R.string.sure).setPositiveButton(17039379, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AllInOneCompress.this.onRemoveFile(file_Related_Information2);
                            }
                        }).setNegativeButton(17039369, null).setIcon(17301543).show();
                        AllInOneCompress.this.dialog.cancel();
                    }
                });
                this.dialog.show();
            }
        }
    }

    public void Show_Advance_Feature_Unlock_Dialog() {
    }

    @SuppressLint({"CheckResult"})
    public void onRemoveFile(final FileRelatedInformation file_Related_Information) {
        Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> observableEmitter) {
                if (file_Related_Information != null) {
                    AllInOneCompress.this.removeFile(new File(file_Related_Information.getFilePath()));
                    StringBuilder sb = new StringBuilder();
                    sb.append("Deleted: ");
                    sb.append(file_Related_Information.getFileName());
                    observableEmitter.onNext(sb.toString());
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            public void accept(String str) {
                AllInOneCompress.this.showProgressDialog(AllInOneCompress.this);
                new FileAsync().execute(new String[]{AllInOneCompress.this.root.getAbsolutePath()});
            }
        });
    }

    @SuppressLint({"CheckResult"})
    public void onRemoveFileMulti(final FileRelatedInformation file_Related_Information) {
        Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> observableEmitter) {
                if (file_Related_Information != null) {
                    AllInOneCompress.this.removeFile(new File(file_Related_Information.getFilePath()));
                    StringBuilder sb = new StringBuilder();
                    sb.append("Deleted: ");
                    sb.append(file_Related_Information.getFileName());
                    observableEmitter.onNext(sb.toString());
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            public void accept(String str) {
            }
        });
    }

    public void removeFile(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    if (file2 != null) {
                        removeFile(file2);
                    }
                }
            }
            file.delete();
        }
    }

    public void onCompressSingleFile(FileRelatedInformation file_Related_Information) {
        getWindow().addFlags(128);
        this.check = 1;
        String filePath = file_Related_Information.getFilePath();
        StringBuilder sb = new StringBuilder();
        sb.append(this.Compress);
        sb.append(InternalZipConstants.ZIP_FILE_SEPARATOR);
        sb.append(this.Edited_File_Name);
        sb.append(".7z");
        String compressCmd = OrderCommandActivity.getCompressCmd(filePath, sb.toString(), ArchiveStreamFactory.SEVEN_Z);
        showProgressDialog(this);
        runCommand(compressCmd);
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
        String compressCmd = OrderCommandActivity.getCompressCmd(str, sb.toString(), str2);
        showProgressDialog(this);
        runCommand(compressCmd);
    }

    @SuppressLint({"CheckResult"})
    private void runCommand(final String str) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                observableEmitter.onNext(Integer.valueOf(P7ZipApi.executeCommand(str)));

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            public void accept(Integer num) throws Exception {
                if (!AllInOneCompress.this.isFinishing()) {
                    AllInOneCompress.this.dismissProgressDialog();
                }
                AllInOneCompress.this.showResult(num.intValue());
            }
        });
    }

    public void showResult(int i) {
        getWindow().clearFlags(128);
        int i2 = R.string.msg_ret_success;
        if (i != 255) {
            switch (i) {
                case 0:
                    Builder builder = new Builder(this);
                    builder.setMessage(R.string.all_files_compressed);
                    builder.setCancelable(true);
                    builder.setPositiveButton(R.string.viewfile, new DialogInterface.OnClickListener() {
                        @SuppressLint("WrongConstant")
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (AllInOneCompress.longclickonce.booleanValue()) {
                                AllInOneCompress.counter = 1;
                                for (int i2 = 0; i2 < AllInOneCompress.this.bufferlist.size(); i2++) {
                                    ((FileRelatedInformation) AllInOneCompress.this.bufferlist.get(i2)).setSelected(false);
                                }
                                AllInOneCompress.selecteditems.clear();
                                AllInOneCompress.longclickonce = Boolean.valueOf(false);
                                AllInOneCompress.fab.setVisibility(8);
                                AllInOneCompress.fab.close(true);
                            }
                            AllInOneCompress.this.editor.clear();
                            AllInOneCompress.this.editor.putInt("idName", 1);
                            AllInOneCompress.this.editor.apply();
                            dialogInterface.cancel();
                            AllInOneCompress.this.finish();
                            AllInOneCompress.this.startActivity(new Intent(AllInOneCompress.this, AllInOneCompress.class).putExtra("option", "compress"));
                        }
                    });
                    builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                    break;
                case 1:
                    i2 = R.string.msg_ret_warning;
                    break;
                case 2:
                    i2 = R.string.msg_ret_fault;
                    break;
                default:
                    switch (i) {
                        case 7:
                            i2 = R.string.msg_ret_command;
                            break;
                        case 8:
                            i2 = R.string.msg_ret_memmory;
                            break;
                    }
            }
        } else {
            i2 = R.string.msg_ret_user_stop;
        }
        SnackbarUtils.with(this.mStorageListView).setMessage(getString(i2)).show();
    }

    public void showResult2(int i) {
        getWindow().clearFlags(128);
        int i2 = R.string.msg_ret_success;
        if (i != 255) {
            switch (i) {
                case 0:
                    Builder builder = new Builder(this);
                    builder.setMessage(R.string.file_extracted);
                    builder.setCancelable(true);
                    builder.setPositiveButton(R.string.viewfile, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AllInOneCompress.this.editor.clear();
                            AllInOneCompress.this.editor.putInt("idName", 21);
                            AllInOneCompress.this.editor.apply();
                            AllInOneCompress.this.startActivity(new Intent(AllInOneCompress.this, SaveFileActivity.class).putExtra("choice", 4));
                            dialogInterface.cancel();
                            AllInOneCompress.this.finish();
                        }
                    });
                    builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.create().show();
                    break;
                case 1:
                    i2 = R.string.msg_ret_warning;
                    break;
                case 2:
                    i2 = R.string.msg_ret_fault;
                    break;
                default:
                    switch (i) {
                        case 7:
                            i2 = R.string.msg_ret_command;
                            break;
                        case 8:
                            i2 = R.string.msg_ret_memmory;
                            break;
                    }
            }
        } else {
            i2 = R.string.msg_ret_user_stop;
        }
        SnackbarUtils.with(this.mStorageListView).setMessage(getString(i2)).show();
    }

    public void showProgressDialog(Activity activity) {
        this.progressdialog = new ProgressDialog(activity);
        this.progressdialog.setMessage(getText(R.string.progress_message));
        this.progressdialog.setCancelable(false);
        this.progressdialog.show();
    }

    public void dismissProgressDialog() {
        if (this.progressdialog != null && this.progressdialog.isShowing()) {
            this.progressdialog.dismiss();
        }
    }

    public void onDestroy() {

        super.onDestroy();
    }

    public void onActivityResult(int i, int i2, Intent intent) {

        super.onActivityResult(i, i2, intent);
    }
}
