package com.hongyan.xcj.modules.main.info.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class InfoNewsAdapter extends RecyclerView.Adapter<InfoNewsAdapter.ViewHolder> {

    private ArrayList<InfoNewsResult.News> mList;

    public void setData(ArrayList<InfoNewsResult.News> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        InfoNewsResult.News news = mList.get(position);
        if (news != null) {
            holder.newsTitle.setText(news.content);
            holder.newsTime.setText(news.update_time);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        TextView newsTime;

        public ViewHolder(View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsTime = itemView.findViewById(R.id.news_time);
        }
    }
}