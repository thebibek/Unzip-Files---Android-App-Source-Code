package com.compressor.extractor.zipers.fileAdaptersForWork;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.compressor.extractor.zipers.fileWork.FileRelatedInformation;
import com.unzipfile.smartextractfile.compressor.zipperfile.R;
import com.unzipfile.smartextractfile.compressor.zipperfile.ashsunzip.AllInOneCompress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FileAdapterItemsAllInOne extends RecyclerView.Adapter<FileAdapterItemsAllInOne.ViewHolder> implements Filterable {
    private static final String[] ARCHIVE_ARRAY = {"ppt", "pptx", "doc", "docx", "msg", "txt", "wpd", "xls", "xlsx", "odf", "odt", "rtf"};
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public List<FileRelatedInformation> documentList;
    public List<FileRelatedInformation> documentListFiltered;
    Bitmap imageBitmap;
    private Activity mActivity;
    private View.OnClickListener mItemClickListener;
    View rootView;

    public FileAdapterItemsAllInOne(Activity activity, View.OnClickListener onClickListener) {
        this.mActivity = activity;
        this.mItemClickListener = onClickListener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.rootView = LayoutInflater.from(this.mActivity).inflate(R.layout.save_list_item_for_videos, viewGroup, false);
        this.rootView.setOnClickListener(this.mItemClickListener);
        return new ViewHolder(this.rootView);
    }

    @SuppressLint("WrongConstant")
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        FileInputStream fileInputStream;
        ViewHolder viewHolder2 = viewHolder;
        FileRelatedInformation file_Related_Information = this.documentListFiltered.get(i);
        int i2 = this.mActivity.getSharedPreferences("MyPrefsFile", 0).getInt("idName", 0);
        if (i2 == 1) {
            viewHolder2.icon.setImageResource(R.drawable.icon_file_archive);
        }
        if (i2 == 2) {
            viewHolder2.icon.setImageResource(R.drawable.icon_unknown);
        }
        if (i2 == 3) {
            viewHolder2.play.setVisibility(0);
            viewHolder2.icon.setBackgroundColor(-16777216);
            Glide.with(this.mActivity).asBitmap().load(Uri.fromFile(new File(file_Related_Information.getFilePath()))).into(viewHolder2.icon);
        }
        if (i2 == 4) {
            viewHolder2.icon.setImageResource(R.drawable.mp3);
        }
        if (i2 == 5) {
            viewHolder2.icon.setImageResource(R.drawable.pdf);
        }
        if (i2 == 6) {
            if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[0])) {
                viewHolder2.icon.setImageResource(R.drawable.ppt);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[1])) {
                viewHolder2.icon.setImageResource(R.drawable.pptx);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[2])) {
                viewHolder2.icon.setImageResource(R.drawable.doc);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[3])) {
                viewHolder2.icon.setImageResource(R.drawable.docx);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[4])) {
                viewHolder2.icon.setImageResource(R.drawable.msg);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[5])) {
                viewHolder2.icon.setImageResource(R.drawable.txt);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[6])) {
                viewHolder2.icon.setImageResource(R.drawable.wpd);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[7])) {
                viewHolder2.icon.setImageResource(R.drawable.xls);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[8])) {
                viewHolder2.icon.setImageResource(R.drawable.xlsx);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[9])) {
                viewHolder2.icon.setImageResource(R.drawable.odf);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[10])) {
                viewHolder2.icon.setImageResource(R.drawable.odt);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[11])) {
                viewHolder2.icon.setImageResource(R.drawable.rtf);
            }
        }
        if (i2 == 7) {
            try {
                fileInputStream = new FileInputStream(file_Related_Information.getFilePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                fileInputStream = null;
            }
            this.imageBitmap = BitmapFactory.decodeStream(fileInputStream);
        }
        switch (file_Related_Information.getFileType()) {
            case folderFull:
                viewHolder2.icon.setImageResource(R.drawable.icon_folder_full);
                break;
            case folderEmpty:
                viewHolder2.icon.setImageResource(R.drawable.icon_folder_empty);
                break;
            case filearchive:
                viewHolder2.icon.setImageResource(R.drawable.icon_file_archive);
                break;
        }
        if (file_Related_Information.isFolder()) {
            viewHolder2.subCount.setText(this.mActivity.getString(R.string.items, new Object[]{Integer.valueOf(file_Related_Information.getSubCount())}));
        } else {
            viewHolder2.subCount.setText(Formatter.formatFileSize(this.mActivity, file_Related_Information.getFileLength()));
        }
        viewHolder2.itemView.setTag(Integer.valueOf(i));
        if (!file_Related_Information.getFileDate().isEmpty()) {
            viewHolder2.date.setText(file_Related_Information.getFileDate());
        }
        if (!file_Related_Information.getFileName().isEmpty()) {
            viewHolder2.fileName.setText(file_Related_Information.getFileName());
        }
        if (file_Related_Information.isSelected()) {
            viewHolder2.card.setCardBackgroundColor(this.mActivity.getResources().getColor(R.color.blue));
        } else {
            viewHolder2.card.setCardBackgroundColor(this.mActivity.getResources().getColor(R.color.white));
        }
        rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return FileAdapterItemsAllInOne.onViewHolder(FileAdapterItemsAllInOne.this,viewHolder2, v);
            }
        });
        if (i2 == 8) {
            Glide.with(this.mActivity).asBitmap().load(Uri.fromFile(new File(file_Related_Information.getFilePath()))).into(viewHolder2.icon);
        }
    }

    @SuppressLint("WrongConstant")
    public static boolean onViewHolder(FileAdapterItemsAllInOne file_Adapter_itemsAllInOne, ViewHolder viewHolder, View view) {
        if (AllInOneCompress.type.equals("compress")) {
            int intValue = ((Integer) view.getTag()).intValue();
            if (intValue < file_Adapter_itemsAllInOne.documentListFiltered.size() && !AllInOneCompress.longclickonce.booleanValue()) {
                AllInOneCompress.fab.setVisibility(0);
                AllInOneCompress.fab1.setVisibility(8);
                if (!file_Adapter_itemsAllInOne.documentListFiltered.get(intValue).isSelected()) {
                    file_Adapter_itemsAllInOne.documentListFiltered.get(intValue).setSelected(true);
                    viewHolder.card.setCardBackgroundColor(file_Adapter_itemsAllInOne.mActivity.getResources().getColor(R.color.blue));
                    AllInOneCompress.counter++;
                    AllInOneCompress.longclickonce = true;
                    AllInOneCompress.selecteditems.add(file_Adapter_itemsAllInOne.documentListFiltered.get(intValue));
                } else {
                    AllInOneCompress.counter--;
                    AllInOneCompress.selecteditems.remove(file_Adapter_itemsAllInOne.documentListFiltered.get(intValue));
                    file_Adapter_itemsAllInOne.documentListFiltered.get(intValue).setSelected(false);
                    viewHolder.card.setCardBackgroundColor(file_Adapter_itemsAllInOne.mActivity.getResources().getColor(R.color.white));
                    if (AllInOneCompress.counter == 1) {
                        AllInOneCompress.selecteditems.clear();
                        AllInOneCompress.fab.setVisibility(8);
                        AllInOneCompress.fab.close(true);
                        AllInOneCompress.longclickonce = false;
                    }
                }
                return true;
            }
        } else {
            int intValue2 = ((Integer) view.getTag()).intValue();
            if (intValue2 < file_Adapter_itemsAllInOne.documentListFiltered.size() && !AllInOneCompress.longclickonce.booleanValue()) {
                AllInOneCompress.fab.setVisibility(0);
                if (!file_Adapter_itemsAllInOne.documentListFiltered.get(intValue2).isSelected()) {
                    file_Adapter_itemsAllInOne.documentListFiltered.get(intValue2).setSelected(true);
                    viewHolder.card.setCardBackgroundColor(file_Adapter_itemsAllInOne.mActivity.getResources().getColor(R.color.blue));
                    AllInOneCompress.counter++;
                    AllInOneCompress.longclickonce = true;
                    AllInOneCompress.selecteditems.add(file_Adapter_itemsAllInOne.documentListFiltered.get(intValue2));
                } else {
                    AllInOneCompress.counter--;
                    AllInOneCompress.selecteditems.remove(file_Adapter_itemsAllInOne.documentListFiltered.get(intValue2));
                    file_Adapter_itemsAllInOne.documentListFiltered.get(intValue2).setSelected(false);
                    viewHolder.card.setCardBackgroundColor(file_Adapter_itemsAllInOne.mActivity.getResources().getColor(R.color.white));
                    if (AllInOneCompress.counter == 1) {
                        AllInOneCompress.selecteditems.clear();
                        AllInOneCompress.fab.setVisibility(8);
                        AllInOneCompress.fab.close(true);
                        AllInOneCompress.longclickonce = false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int getItemCount() {
        return this.documentListFiltered.size();
    }

    public void setDataList(List<FileRelatedInformation> list) {
        this.documentListFiltered = list;
        this.documentList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView date;
        TextView fileName;
        ImageView icon;
        ImageView play;
        TextView subCount;

        public ViewHolder(View view) {
            super(view);
            this.card = (CardView) view.findViewById(R.id.card);
            this.date = (TextView) view.findViewById(R.id.date);
            this.subCount = (TextView) view.findViewById(R.id.file_sub_count);
            this.fileName = (TextView) view.findViewById(R.id.file_item_name);
            this.play = (ImageView) view.findViewById(R.id.play);
            this.icon = (ImageView) view.findViewById(R.id.file_item_icon);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            public FilterResults performFiltering(CharSequence charSequence) {
                String charSequence2 = charSequence.toString();
                if (charSequence2.isEmpty()) {

                } else {
                    ArrayList arrayList = new ArrayList();
                    for (FileRelatedInformation file_Related_Information : FileAdapterItemsAllInOne.this.documentList) {
                        if (file_Related_Information.getFileName().toLowerCase().contains(charSequence2.toLowerCase()) || file_Related_Information.getFileName().contains(charSequence)) {
                            arrayList.add(file_Related_Information);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = FileAdapterItemsAllInOne.this.documentListFiltered;
                return filterResults;
            }
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                FileAdapterItemsAllInOne.this.notifyDataSetChanged();
            }
        };
    }
}
