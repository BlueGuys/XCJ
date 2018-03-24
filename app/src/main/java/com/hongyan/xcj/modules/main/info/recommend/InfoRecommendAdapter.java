package com.hongyan.xcj.modules.main.info.recommend;

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

public class InfoRecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * Header
     */
    public static final int ITEM_TYPE_HEADER = 1000;
    /**
     * Item
     */
    public static final int ITEM_TYPE_ITEM = 1001;

    private ArrayList<InfoRecommendResult.Article> mList;

    public void setData(ArrayList<InfoRecommendResult.Article> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        }
        return ITEM_TYPE_ITEM;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            ((HeadViewHolder) holder).headerView.setData(getData());
            ((HeadViewHolder) holder).headerView.setOnPageClickListener(new ScrollBannerView.OnPageClickListener() {
                @Override
                public void setOnPage(int position) {

                }
            });
        } else if (holder instanceof ItemViewHolder) {
            InfoRecommendResult.Article article = mList.get(position);
            if (article != null) {
                ((ItemViewHolder) holder).articleName.setText(article.title);
                ((ItemViewHolder) holder).articleTime.setText(article.update_time);
                ((ItemViewHolder) holder).articleWebSite.setText(article.source);
                ImageLoader.getInstance().displayImage(article.photo, ((ItemViewHolder) holder).articleImage, BaseApplication.getInstance().getImageLoaderOptions());
            }
        }
    }

    private ArrayList<ScrollBannerView.Entity> getData() {
        ArrayList<ScrollBannerView.Entity> list = new ArrayList<ScrollBannerView.Entity>();
        ScrollBannerView.Entity Entity1 = new ScrollBannerView.Entity();
        Entity1.setImageUrl("http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png");
        Entity1.setTitle("AAA");
        list.add(Entity1);
        ScrollBannerView.Entity Entity2 = new ScrollBannerView.Entity();
        Entity2.setImageUrl("http://pic71.nipic.com/file/20150610/13549908_104823135000_2.jpg");
        Entity2.setTitle("BBB");
        list.add(Entity2);
        return list;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
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