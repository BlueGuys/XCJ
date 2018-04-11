package com.hongyan.xcj.modules.main.info.reports;

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

public class InfoReportAdapter extends RecyclerView.Adapter<InfoReportAdapter.ViewHolder> {

    private ArrayList<InfoReportResult.Report> mList;
    private Activity mActivity;

    public InfoReportAdapter(Activity activity) {
        this.mActivity = activity;
    }

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // 绑定数据
        final InfoReportResult.Report report = mList.get(position);
        if (report != null) {
            holder.articleName.setText(report.title);
            holder.articleTime.setText(report.update_time);
            holder.articleWebSite.setText(report.source);
            holder.articleComment.setText(report.comment);
            holder.collectImage.setImageResource(report.isCollect() ? R.drawable.icon_item_collection_s : R.drawable.icon_item_collection_n);
            ImageLoader.getInstance().displayImage(report.photo, holder.articleImage, ImageLoaderOptionHelper.getInstance().getListImageOption());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArticleActivity.startActivity(mActivity, report.url);
                }
            });
            holder.collectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (report.isCollect()) {
                        CollectionManager.getInstance().cancelCollectionArticle(report.id, "1");
                    } else {
                        CollectionManager.getInstance().collectionArticle(report.id, "1");
                    }
                }
            });
            holder.collectImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView articleImage;
        ImageView collectImage;
        TextView articleName;
        TextView articleTime;
        TextView articleWebSite;
        TextView articleComment;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.linear_layout_report);
            articleImage = itemView.findViewById(R.id.article_image);
            collectImage = itemView.findViewById(R.id.image_collection);
            articleName = itemView.findViewById(R.id.article_title);
            articleTime = itemView.findViewById(R.id.article_time);
            articleWebSite = itemView.findViewById(R.id.article_website);
            articleComment = itemView.findViewById(R.id.article_comment);
        }
    }
}