package com.hongyan.xcj.modules.main.info.reports;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class InfoReportAdapter extends RecyclerView.Adapter<InfoReportAdapter.ViewHolder> {

    private ArrayList<InfoReportResult.Report> mList;

    public void setData(ArrayList<InfoReportResult.Report> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_report, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        InfoReportResult.Report report = mList.get(position);
        if (report != null) {
            holder.articleName.setText(report.title);
            holder.articleTime.setText(report.update_time);
            holder.articleWebSite.setText(report.source);
            ImageLoader.getInstance().displayImage(report.photo, holder.articleImage);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView articleImage;
        TextView articleName;
        TextView articleTime;
        TextView articleWebSite;

        public ViewHolder(View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.article_image);
            articleName = itemView.findViewById(R.id.article_title);
            articleTime = itemView.findViewById(R.id.article_time);
            articleWebSite = itemView.findViewById(R.id.article_website);
        }
    }
}