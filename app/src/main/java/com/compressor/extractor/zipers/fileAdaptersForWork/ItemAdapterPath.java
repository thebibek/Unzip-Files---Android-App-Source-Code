package com.compressor.extractor.zipers.fileAdaptersForWork;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.unzipfile.smartextractfile.compressor.zipperfile.R;
import java.io.File;

public class ItemAdapterPath extends RecyclerView.Adapter<ItemAdapterPath.ViewHolder> {

    private Activity mActivity;
    private String mCurPath;
    private String[] mPathStringList;

    public ItemAdapterPath(Activity activity) {
        this.mActivity = activity;
        this.mPathStringList = new String[0];
    }

    public void setPathView(String str) {
        this.mCurPath = str;
        this.mPathStringList = this.mCurPath.split(File.separator);
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.mActivity).inflate(R.layout.activity_item_list_path, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String str = this.mPathStringList[i];
        String str2 = "";
        for (int i2 = 0; i2 < i + 1; i2++) {
            str2 = str2 + this.mPathStringList[i2] + File.separator;
        }
        viewHolder.itemView.setTag(str2);
        viewHolder.pathText.setText(str);
    }

    public int getItemCount() {
        return this.mPathStringList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pathText;

        public ViewHolder(View view) {
            super(view);
            this.pathText = (TextView) view.findViewById(R.id.path_list_text);
        }
    }
}
