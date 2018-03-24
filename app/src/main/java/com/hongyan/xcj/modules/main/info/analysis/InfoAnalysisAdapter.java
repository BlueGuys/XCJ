package com.hongyan.xcj.modules.main.info.analysis;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.core.BaseApplication;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class InfoAnalysisAdapter extends RecyclerView.Adapter<InfoAnalysisAdapter.ViewHolder> {

    private ArrayList<InfoAnalysisResult.Article> mList;

    public void setData(ArrayList<InfoAnalysisResult.Article> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_analysis, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        InfoAnalysisResult.Article article = mList.get(position);
        if (article != null) {
            holder.articleName.setText(article.title);
            holder.articleTime.setText(article.update_time);
            holder.articleWebSite.setText(article.source);
            ImageLoader.getInstance().displayImage(article.photo, holder.articleImage, BaseApplication.getInstance().getImageLoaderOptions());
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