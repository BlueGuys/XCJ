package com.hongyan.xcj.modules.search;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.core.ImageLoaderOptionHelper;
import com.hongyan.xcj.modules.article.ArticleActivity;
import com.hongyan.xcj.modules.coin.CoinDetailActivity;
import com.hongyan.xcj.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SearchAdapter extends BaseAdapter {

    private static final int TYPE_COIN = 0;
    private static final int TYPE_ARTICLE = 1;

    private Activity activity;
    private SearchResult mResult;

    public SearchAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setData(SearchResult result) {
        this.mResult = result;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = 0;
        if (mResult != null && mResult.data != null) {
            if (mResult.data.coinList != null) {
                count += mResult.data.coinList.size();
            }
            if (mResult.data.articleList != null) {
                count += mResult.data.articleList.size();
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mResult.data.coinList.size()) {
            return TYPE_COIN;
        } else {
            return TYPE_ARTICLE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == TYPE_COIN) {
            CoinViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.item_search_coin, parent, false);
                holder = new CoinViewHolder();
                holder.imageView = convertView.findViewById(R.id.iv_image_logo);
                holder.textView = convertView.findViewById(R.id.tv_coin_name);
                convertView.setTag(holder);
            } else {
                holder = (CoinViewHolder) convertView.getTag();
            }
            SearchResult.CoinBean bean = this.mResult.data.coinList.get(position);
            holder.textView.setText(Html.fromHtml(bean.name));
            ImageLoader.getInstance().displayImage(bean.logo, holder.imageView, ImageLoaderOptionHelper.getInstance().getCircleImageOption());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, CoinDetailActivity.class);
                    intent.putExtra("id", bean.id);
                    activity.startActivity(intent);
                }
            });
        } else {
            ArticleViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.item_search_article, parent, false);
                holder = new ArticleViewHolder();
                holder.textView = convertView.findViewById(R.id.tv_search_article_name);
                holder.linearPadding = convertView.findViewById(R.id.linear_padding);
                convertView.setTag(holder);
            } else {
                holder = (ArticleViewHolder) convertView.getTag();
            }
            int currentPosition = position - this.mResult.data.coinList.size();
            holder.linearPadding.setVisibility(currentPosition == 0 ? View.VISIBLE : View.GONE);
            SearchResult.ArticleBean bean = this.mResult.data.articleList.get(currentPosition);
            if (!StringUtils.isEmpty(bean.title)) {
                holder.textView.setText(Html.fromHtml(bean.title));
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArticleActivity.startActivity(activity, bean.url);
                }
            });
        }
        return convertView;
    }

    private static class CoinViewHolder {
        private TextView textView;
        private ImageView imageView;
    }

    private static class ArticleViewHolder {
        private TextView linearPadding;
        private TextView textView;
    }
}
