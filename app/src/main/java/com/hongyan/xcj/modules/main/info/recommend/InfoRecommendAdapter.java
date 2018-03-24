package com.hongyan.xcj.modules.main.info.recommend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.xcj.R;

import java.util.ArrayList;

public class InfoRecommendAdapter extends RecyclerView.Adapter<InfoRecommendAdapter.ViewHolder> {

    private ArrayList<InfoRecommendResult.Article> mList;

    public void setData(ArrayList<InfoRecommendResult.Article> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_recommend, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        InfoRecommendResult.Article article = mList.get(position);
        if (article != null) {
            holder.articleName.setText(article.title);
            holder.articleTime.setText(article.update_time);
            holder.articleWebSite.setText(article.source);
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