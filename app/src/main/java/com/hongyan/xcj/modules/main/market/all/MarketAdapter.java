package com.hongyan.xcj.modules.main.market.all;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.core.ImageLoaderOptionHelper;
import com.hongyan.xcj.modules.coin.CoinDetailActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {

    private ArrayList<MarketResult.Market> mList;

    private Activity activity;

    public MarketAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setData(ArrayList<MarketResult.Market> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_all, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        final MarketResult.Market market = mList.get(position);
        if (market != null) {
            ImageLoader.getInstance().displayImage(market.logo, holder.imageLogo, ImageLoaderOptionHelper.getInstance().getListImageOption());
            holder.tvCurrencyName.setText(market.name);
            holder.tvCurrencyDesc.setText(market.desc);
            holder.tvCurrencyPriceUS.setText("$" + market.dollar_price);
            holder.tvCurrencyPriceCN.setText("¥" + market.price);
            if (market.isUp()) {
                holder.tvCurrencyChangeRate.setText(market.chg + "%");
                holder.tvCurrencyChangeRate.setBackgroundResource(R.drawable.bg_market_item_button_red);
            } else {
                holder.tvCurrencyChangeRate.setText(market.chg + "%");
                holder.tvCurrencyChangeRate.setBackgroundResource(R.drawable.bg_market_item_button_green);
            }
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, CoinDetailActivity.class);
                    intent.putExtra("id", market.id);
                    activity.startActivity(intent);
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
        ImageView imageLogo;
        TextView tvCurrencyName;
        TextView tvCurrencyDesc;
        TextView tvCurrencyPriceUS;
        TextView tvCurrencyPriceCN;
        TextView tvCurrencyChangeRate;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.linear_market_all);
            imageLogo = itemView.findViewById(R.id.market_logo);
            tvCurrencyName = itemView.findViewById(R.id.market_currency_name);
            tvCurrencyDesc = itemView.findViewById(R.id.market_currency_desc);
            tvCurrencyPriceUS = itemView.findViewById(R.id.market_currency_price_us);
            tvCurrencyPriceCN = itemView.findViewById(R.id.market_currency_price_cn);
            tvCurrencyChangeRate = itemView.findViewById(R.id.market_currency_change_rate);
        }
    }
}