package com.hongyan.xcj.modules.collect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.core.BaseApplication;
import com.hongyan.xcj.core.ImageLoaderOptionHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private ArrayList<CollectionResult.Collection> mList;

    public void setData(ArrayList<CollectionResult.Collection> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        CollectionResult.Collection collection = mList.get(position);
        if (collection != null) {
            holder.articleName.setText(collection.title);
            holder.articleTime.setText(collection.update_time);
            holder.articleWebSite.setText(collection.source);
            holder.articleComment.setText(collection.comment);
            ImageLoader.getInstance().displayImage(collection.photo, holder.articleImage, ImageLoaderOptionHelper.getInstance().getListImageOption());
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
        TextView articleComment;

        public ViewHolder(View itemView) {
            super(itemView);
            articleName = itemView.findViewById(R.id.collection_title);
            articleTime = itemView.findViewById(R.id.collection_time);
            articleWebSite = itemView.findViewById(R.id.collection_website);
            articleImage = itemView.findViewById(R.id.collection_image);
            articleComment = itemView.findViewById(R.id.article_comment);
        }
    }
}