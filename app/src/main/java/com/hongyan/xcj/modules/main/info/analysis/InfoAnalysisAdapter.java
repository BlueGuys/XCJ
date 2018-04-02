package com.hongyan.xcj.modules.main.info.analysis;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.core.BaseApplication;
import com.hongyan.xcj.core.CollectionManager;
import com.hongyan.xcj.core.ImageLoaderOptionHelper;
import com.hongyan.xcj.modules.article.ArticleActivity;
import com.hongyan.xcj.modules.main.info.recommend.InfoRecommendAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class InfoAnalysisAdapter extends RecyclerView.Adapter<InfoAnalysisAdapter.ViewHolder> {

    private ArrayList<InfoAnalysisResult.Article> mList;

    private Activity mActivity;

    public InfoAnalysisAdapter(Activity activity) {
        this.mActivity = activity;
    }

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // 绑定数据
        final InfoAnalysisResult.Article article = mList.get(position);
        if (article != null) {
            holder.articleName.setText(article.title);
            holder.articleTime.setText(article.update_time);
            holder.articleWebSite.setText(article.source);
            ImageLoader.getInstance().displayImage(article.photo, holder.articleImage, ImageLoaderOptionHelper.getInstance().getListImageOption());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArticleActivity.startActivity(mActivity, article.url);
                }
            });
            holder.collectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (article.isCollect()) {
                        CollectionManager.getInstance().cancelCollectionArticle(article.id, "0");
//                            setCollection(position, false);
                        holder.collectImage.setImageResource(R.drawable.icon_item_collection_n);
                    } else {
                        CollectionManager.getInstance().collectionArticle(article.id, "0");
                        holder.collectImage.setImageResource(R.drawable.icon_item_collection_s);
//                            setCollection(position, true);
                    }
                    article.setCollect(!article.isCollect());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView collectImage;
        ImageView articleImage;
        TextView articleName;
        TextView articleTime;
        TextView articleWebSite;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.linear_layout_analysis);
            articleImage = itemView.findViewById(R.id.article_image);
            collectImage = itemView.findViewById(R.id.image_collection);
            articleName = itemView.findViewById(R.id.article_title);
            articleTime = itemView.findViewById(R.id.article_time);
            articleWebSite = itemView.findViewById(R.id.article_website);
        }
    }
}