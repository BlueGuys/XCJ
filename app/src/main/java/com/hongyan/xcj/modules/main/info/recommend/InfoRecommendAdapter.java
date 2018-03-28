package com.hongyan.xcj.modules.main.info.recommend;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.core.BaseApplication;
import com.hongyan.xcj.core.ImageLoaderOptionHelper;
import com.hongyan.xcj.modules.article.ArticleActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class InfoRecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * Header
     */
    public static final int ITEM_TYPE_HEADER = 1000;
    /**
     * Item
     */
    public static final int ITEM_TYPE_ITEM = 1001;

    private ArrayList<InfoRecommendResult.Article> mArticleList = new ArrayList<>();
    private ArrayList<InfoRecommendResult.AD> mAdList = new ArrayList<>();

    private Activity mActivity;

    public InfoRecommendAdapter(Activity activity) {
        this.mActivity = activity;
    }

    public void setData(ArrayList<InfoRecommendResult.Article> articleList, ArrayList<InfoRecommendResult.AD> adList) {
        if (articleList != null && articleList.size() > 0) {
            this.mArticleList.clear();
            this.mArticleList.addAll(articleList);
        }
        if (adList != null && adList.size() > 0) {
            this.mAdList.clear();
            this.mAdList.addAll(adList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mAdList.size() > 0) {
            if (position == 0) {
                return ITEM_TYPE_HEADER;
            } else {
                return ITEM_TYPE_ITEM;
            }
        }
        return ITEM_TYPE_ITEM;
    }

    public Object getItemData(int position) {
        if (mAdList.size() > 0) {
            if (position == 0) {
                return mAdList;
            } else {
                return mArticleList.get(position - 1);
            }
        } else {
            return mArticleList.get(position);
        }
    }

    @Override
    public int getItemCount() {
        if (mAdList.size() > 0) {
            return mArticleList.size() + 1;
        } else {
            return mArticleList.size();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_header, parent, false);
            return new HeadViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_recommend, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            ArrayList<ScrollBannerView.Entity> entityArrayList = new ArrayList<>();
            final ArrayList<InfoRecommendResult.AD> adList = (ArrayList<InfoRecommendResult.AD>) getItemData(position);
            for (int i = 0; i < adList.size(); i++) {
                ScrollBannerView.Entity entity = new ScrollBannerView.Entity();
                entity.setImageUrl(adList.get(position).photo);
                entity.setTitle(adList.get(position).title);
                entityArrayList.add(entity);
            }
            ((HeadViewHolder) holder).headerView.setData(entityArrayList);
            ((HeadViewHolder) holder).headerView.setOnPageClickListener(new ScrollBannerView.OnPageClickListener() {
                @Override
                public void setOnPage(int position) {
                    ArticleActivity.startActivity(mActivity, adList.get(position).url);
                }
            });
        } else if (holder instanceof ItemViewHolder) {
            InfoRecommendResult.Article article = (InfoRecommendResult.Article) getItemData(position);
            if (article != null) {
                ((ItemViewHolder) holder).articleName.setText(article.title);
                ((ItemViewHolder) holder).articleTime.setText(article.update_time);
                ((ItemViewHolder) holder).articleWebSite.setText(article.source);
                ImageLoader.getInstance().displayImage(article.photo, ((ItemViewHolder) holder).articleImage, ImageLoaderOptionHelper.getInstance().getListImageOption());
            }
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder {
        ScrollBannerView headerView;

        public HeadViewHolder(View itemView) {
            super(itemView);
            headerView = itemView.findViewById(R.id.head_view);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView articleImage;
        TextView articleName;
        TextView articleTime;
        TextView articleWebSite;

        public ItemViewHolder(View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.article_image);
            articleName = itemView.findViewById(R.id.article_title);
            articleTime = itemView.findViewById(R.id.article_time);
            articleWebSite = itemView.findViewById(R.id.article_website);
        }
    }
}