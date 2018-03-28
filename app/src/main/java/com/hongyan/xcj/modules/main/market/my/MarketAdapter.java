package com.hongyan.xcj.modules.main.market.my;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.xcj.R;

import java.util.ArrayList;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {

    private ArrayList<MarketResult.Market> mList;

    public void setData(ArrayList<MarketResult.Market> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_me, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        MarketResult.Market market = mList.get(position);
        if (market != null) {
            holder.tvLabel.setText(String.valueOf(position + 1));
            holder.tvLabel.setBackgroundResource(position < 3 ? R.drawable.bg_market_item_label_blue : R.drawable.bg_market_item_label_grey);
            holder.tvCurrencyName.setText(market.name);
            holder.tvCurrencyDesc.setText(market.desc);
            holder.tvCurrencyPriceUS.setText("$" + market.dollar_price);
            holder.tvCurrencyPriceCN.setText("¥" + market.price);
            if (market.isUp()) {
                holder.tvCurrencyChangeRate.setText("+" + market.chg + "%");
                holder.tvCurrencyChangeRate.setBackgroundResource(R.drawable.bg_market_item_button_red);
            } else {
                holder.tvCurrencyChangeRate.setText("-" + market.chg + "%");
                holder.tvCurrencyChangeRate.setBackgroundResource(R.drawable.bg_market_item_button_green);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageLogo;
        TextView tvLabel;
        TextView tvCurrencyName;
        TextView tvCurrencyDesc;
        TextView tvCurrencyPriceUS;
        TextView tvCurrencyPriceCN;
        TextView tvCurrencyChangeRate;

        public ViewHolder(View itemView) {
            super(itemView);
            imageLogo = itemView.findViewById(R.id.market_logo);
            tvLabel = itemView.findViewById(R.id.market_label);
            tvCurrencyName = itemView.findViewById(R.id.market_currency_name);
            tvCurrencyDesc = itemView.findViewById(R.id.market_currency_desc);
            tvCurrencyPriceUS = itemView.findViewById(R.id.market_currency_price_us);
            tvCurrencyPriceCN = itemView.findViewById(R.id.market_currency_price_cn);
            tvCurrencyChangeRate = itemView.findViewById(R.id.market_currency_change_rate);
        }
    }
}