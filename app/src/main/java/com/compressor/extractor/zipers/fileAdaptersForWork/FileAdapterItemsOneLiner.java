package com.compressor.extractor.zipers.fileAdaptersForWork;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.unzipfile.smartextractfile.compressor.zipperfile.ashsunzip.SaveFileActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileAdapterItemsOneLiner extends RecyclerView.Adapter<FileAdapterItemsOneLiner.ViewHolder> implements Filterable {
    private static final String[] ARCHIVE_ARRAY = {"pdf", "ppt", "doc", "docx", "msg", "txt", "mp4", "avi", "mpg", "mov", "wmv", "jpg", "png", "wpd", "xls", "xlsx", "odf", "odt", "rtf", "apk", "pptx"};
    public List<FileRelatedInformation> documentList;
    public List<FileRelatedInformation> documentListFiltered = new ArrayList();
    int iconId;
    public Activity mActivity;
    private View.OnClickListener mItemClickListener;
    View rootView;

    public FileAdapterItemsOneLiner(Activity activity, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        this.mActivity = activity;
        this.mItemClickListener = onClickListener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.rootView = LayoutInflater.from(this.mActivity).inflate(R.layout.save_list_item_liner, viewGroup, false);
        this.rootView.setOnClickListener(this.mItemClickListener);
        return new ViewHolder(this.rootView);
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        FileRelatedInformation file_Related_Information = this.documentListFiltered.get(i);
        String filePath = file_Related_Information.getFilePath();
        if (this.mActivity.getSharedPreferences("MyPrefsFile", 0).getInt("idName", 0) == 21) {
            if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[0])) {
                this.iconId = R.drawable.pdf;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[1])) {
                this.iconId = R.drawable.ppt;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[2])) {
                this.iconId = R.drawable.doc;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[3])) {
                this.iconId = R.drawable.docx;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[4])) {
                this.iconId = R.drawable.msg;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[5])) {
                this.iconId = R.drawable.txt;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[6]) || file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[7]) || file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[8]) || file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[9]) || file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[10])) {
                Glide.with(this.mActivity).asBitmap().load(Uri.fromFile(new File(filePath))).into(viewHolder.icon);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[11]) || file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[12])) {
                Glide.with(this.mActivity).load(filePath).into(viewHolder.icon);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[13])) {
                this.iconId = R.drawable.wpd;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[14])) {
                this.iconId = R.drawable.xls;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[15])) {
                this.iconId = R.drawable.xlsx;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[16])) {
                this.iconId = R.drawable.odf;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[17])) {
                this.iconId = R.drawable.odt;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[18])) {
                this.iconId = R.drawable.rtf;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[19])) {
                this.iconId = R.drawable.apk;
                viewHolder.icon.setImageResource(this.iconId);
            } else if (file_Related_Information.getFileName().endsWith(ARCHIVE_ARRAY[20])) {
                this.iconId = R.drawable.pptx;
                viewHolder.icon.setImageResource(this.iconId);
            } else {
                this.iconId = R.drawable.icon_unknown;
                viewHolder.icon.setImageResource(this.iconId);
            }
        }
        switch (file_Related_Information.getFileType()) {
            case folderFull:
                this.iconId = R.drawable.icon_folder_full;
                viewHolder.icon.setImageResource(this.iconId);
                break;
            case folderEmpty:
                this.iconId = R.drawable.icon_folder_empty;
                viewHolder.icon.setImageResource(this.iconId);
                break;
            case filearchive:
                this.iconId = R.drawable.icon_file_archive;
                viewHolder.icon.setImageResource(this.iconId);
                break;
        }
        if (file_Related_Information.isFolder()) {
            viewHolder.subCount.setText(this.mActivity.getString(R.string.items, new Object[]{Integer.valueOf(file_Related_Information.getSubCount())}));
        } else {
            viewHolder.subCount.setText(Formatter.formatFileSize(this.mActivity, file_Related_Information.getFileLength()));
        }
        viewHolder.itemView.setTag(Integer.valueOf(i));
        viewHolder.date.setText(file_Related_Information.getFileDate());
        viewHolder.fileName.setText(file_Related_Information.getFileName());
        viewHolder.more.setTag(Integer.valueOf(i));
        viewHolder.more.setOnClickListener(this.mItemClickListener);
        if (file_Related_Information.isSelected()) {
            viewHolder.card.setCardBackgroundColor(this.mActivity.getResources().getColor(R.color.blue));
        } else {
            viewHolder.card.setCardBackgroundColor(this.mActivity.getResources().getColor(R.color.white));
        }
        this.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("WrongConstant")
            public boolean onLongClick(View view) {
                int intValue = ((Integer) view.getTag()).intValue();
                if (intValue >= FileAdapterItemsOneLiner.this.documentListFiltered.size() || SaveFileActivity.longclickonce.booleanValue()) {
                    return false;
                }
                SaveFileActivity.fab.setVisibility(0);
                if (!((FileRelatedInformation) FileAdapterItemsOneLiner.this.documentListFiltered.get(intValue)).isSelected()) {
                    ((FileRelatedInformation) FileAdapterItemsOneLiner.this.documentListFiltered.get(intValue)).setSelected(true);
                    viewHolder.card.setCardBackgroundColor(FileAdapterItemsOneLiner.this.mActivity.getResources().getColor(R.color.blue));
                    SaveFileActivity.counter++;
                    SaveFileActivity.longclickonce = true;
                    SaveFileActivity.selecteditems.add(FileAdapterItemsOneLiner.this.documentListFiltered.get(intValue));
                } else {
                    SaveFileActivity.counter--;
                    SaveFileActivity.selecteditems.remove(FileAdapterItemsOneLiner.this.documentListFiltered.get(intValue));
                    ((FileRelatedInformation) FileAdapterItemsOneLiner.this.documentListFiltered.get(intValue)).setSelected(false);
                    viewHolder.card.setCardBackgroundColor(FileAdapterItemsOneLiner.this.mActivity.getResources().getColor(R.color.white));
                    if (SaveFileActivity.counter == 1) {
                        SaveFileActivity.selecteditems.clear();
                        SaveFileActivity.fab.setVisibility(8);
                        SaveFileActivity.longclickonce = false;
                    }
                }
                return true;
            }
        });
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
        ImageView more;
        TextView subCount;

        public ViewHolder(View view) {
            super(view);
            this.card = (CardView) view.findViewById(R.id.card);
            this.more = (ImageView) view.findViewById(R.id.moremenu);
            this.date = (TextView) view.findViewById(R.id.date);
            this.subCount = (TextView) view.findViewById(R.id.file_sub_count);
            this.fileName = (TextView) view.findViewById(R.id.file_item_name);
            this.icon = (ImageView) view.findViewById(R.id.file_item_icon);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            public FilterResults performFiltering(CharSequence charSequence) {
                String charSequence2 = charSequence.toString();
                if (charSequence2.isEmpty()) {
                    List unused = FileAdapterItemsOneLiner.this.documentListFiltered = FileAdapterItemsOneLiner.this.documentList;
                } else {
                    ArrayList arrayList = new ArrayList();
                    for (FileRelatedInformation file_Related_Information : FileAdapterItemsOneLiner.this.documentList) {
                        if (file_Related_Information.getFileName().toLowerCase().contains(charSequence2.toLowerCase()) || file_Related_Information.getFileName().contains(charSequence)) {
                            arrayList.add(file_Related_Information);
                        }
                    }

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = FileAdapterItemsOneLiner.this.documentListFiltered;
                return filterResults;
            }
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                FileAdapterItemsOneLiner.this.notifyDataSetChanged();
            }
        };
    }
}
