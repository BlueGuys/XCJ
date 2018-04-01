package com.hongyan.xcj.modules.coin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.modules.coin.widget.CoinProgressView;
import com.hongyan.xcj.modules.coin.widget.CoinView;
import com.hongyan.xcj.utils.JavaTypesHelper;

public class CoinDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * K线图Header
     */
    private static final int ITEM_TYPE_HEADER = 0;
    /**
     * 买卖10档header
     */
    private static final int ITEM_TYPE_MM_HEADER = 1;
    /**
     * 买卖10档item
     */
    private static final int ITEM_TYPE_MM_ITEM = 2;
    /**
     * 交易记录header
     */
    private static final int ITEM_TYPE_DEAL_HEADER = 3;
    /**
     * 交易记录item
     */
    private static final int ITEM_TYPE_DEAL_ITEM = 4;
    /**
     * 币种详情
     */
    private static final int ITEM_TYPE_COIN_DETAIL = 5;
    /**
     * 相关资讯Header
     */
    private static final int ITEM_TYPE_INFO_HEADER = 6;
    /**
     * 相关资讯Item
     */
    private static final int ITEM_TYPE_INFO_ITEM = 7;

    private Context mContext;
    private CoinDetailResult.Data mData;

    private KLineHeader kLineHeader;
    private CoinView coinView;

    public CoinDetailAdapter(Context context) {
        this.mContext = context;
        View headerView = View.inflate(context, R.layout.item_coin_detail_header, null);
        coinView = headerView.findViewById(R.id.coin_view);
        kLineHeader = new KLineHeader(headerView);
    }

    public void setData(CoinDetailResult.Data data) {
        if (data == null) {
            return;
        }
        this.mData = data;
        notifyDataSetChanged();
    }

    public void updateKLine(CoinResult.Data data) {
        if (data == null || kLineHeader == null) {
            return;
        }
        coinView.updateData(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        int count = 0;
        count++;//K线图
        if (mData.mmList != null && mData.mmList.size() > 0) {
            count++;//买卖10档Header
            count += mData.mmList.size();//买卖10档Item
        }
        count++;//交易记录Header
        if (mData.dealList != null && mData.dealList.size() > 0) {
            count += mData.dealList.size();//交易记录Item
        }
        count++;//币种详情
        count++;//相关资讯Header
        if (mData.infoList != null && mData.infoList.size() > 0) {
            count += mData.infoList.size();//相关资讯Item
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        //[0    1    2,3,4,5,6,7,8,9,10,11   12   13,14,15,16,17,18,19,20   21  22  23,24,25,26]
        if (position == 0) {//[0]
            return ITEM_TYPE_HEADER;
        } else if (position == 1) {//[1]
            return ITEM_TYPE_MM_HEADER;
        } else if (position > 1 && position < mData.mmList.size() + 2) {//[2,11]   10
            return ITEM_TYPE_MM_ITEM;
        } else if (position == mData.mmList.size() + 2) {//[12]
            return ITEM_TYPE_DEAL_HEADER;
        } else if (position > mData.mmList.size() + 2 && position < mData.mmList.size() + mData.dealList.size() + 3) {//[13,20]
            return ITEM_TYPE_DEAL_ITEM;
        } else if (position == mData.mmList.size() + 3 + mData.dealList.size()) {//[21]
            return ITEM_TYPE_COIN_DETAIL;//[20]
        } else if (position == mData.mmList.size() + 4 + mData.dealList.size()) {//[22]
            return ITEM_TYPE_INFO_HEADER;
        } else {//[23+]
            return ITEM_TYPE_INFO_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return kLineHeader;
        } else if (viewType == ITEM_TYPE_MM_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_mm_header, parent, false);
            return new MMHeader(view);
        } else if (viewType == ITEM_TYPE_MM_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_mm_item, parent, false);
            return new MMItem(view);
        } else if (viewType == ITEM_TYPE_DEAL_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_deal_header, parent, false);
            return new DealHeader(view);
        } else if (viewType == ITEM_TYPE_DEAL_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_deal_item, parent, false);
            return new DealItem(view);
        } else if (viewType == ITEM_TYPE_COIN_DETAIL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_brief, parent, false);
            return new DetailBrief(view);
        } else if (viewType == ITEM_TYPE_INFO_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_info_header, parent, false);
            return new InfoHeader(view);
        } else if (viewType == ITEM_TYPE_INFO_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_info_item, parent, false);
            return new InfoItem(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (this.mData == null) {
            return;
        }
        if (holder instanceof KLineHeader) {
        } else if (holder instanceof MMHeader) {
            ((MMHeader) holder).progressView.setProgress(JavaTypesHelper.toFloat(this.mData.buyRate));
        } else if (holder instanceof MMItem) {
            int realPosition = position - 2;
            CoinDetailResult.MMBean bean = this.mData.mmList.get(realPosition);
            ((MMItem) holder).tvBuyPrice.setText(bean.buyAmount);
            ((MMItem) holder).tvBuyCount.setText(bean.buyCount);
            ((MMItem) holder).tvSellerPrice.setText(bean.sellAmount);
            ((MMItem) holder).tvSellerCount.setText(bean.sellCount);
            if (realPosition == this.mData.mmList.size() - 1) {
                ((MMItem) holder).bottomLine.setVisibility(View.GONE);
            }
        } else if (holder instanceof DealHeader) {
        } else if (holder instanceof DealItem) {
            int realPosition = position - this.mData.mmList.size() - 3;
            CoinDetailResult.DealBean bean = this.mData.dealList.get(realPosition);
            ((DealItem) holder).tvDealTime.setText(bean.timeStamp);
            ((DealItem) holder).tvDealPrice.setText(bean.price);
            ((DealItem) holder).tvDealVolume.setText(bean.volume);
            if (bean.isUp()) {
                ((DealItem) holder).tvDealPrice.setTextColor(mContext.getResources().getColor(R.color.red));
            } else {
                ((DealItem) holder).tvDealPrice.setTextColor(mContext.getResources().getColor(R.color.text_color_green));
            }
            if (realPosition == this.mData.dealList.size() - 1) {
                ((DealItem) holder).bottomLine.setVisibility(View.GONE);
            }
        } else if (holder instanceof DetailBrief) {
            CoinDetailResult.CoinDetailBean bean = this.mData.coinDetail;
            if (bean != null) {
                ((DetailBrief) holder).tvDetailBrief.setText(bean.brief);
                ((DetailBrief) holder).tvDetailNameEN.setText(bean.ENName);
                ((DetailBrief) holder).tvDetailNameCN.setText(bean.CNName);
                ((DetailBrief) holder).tvDetailWebSite.setText(bean.webSiteName);
                ((DetailBrief) holder).tvDetailBlock.setText(bean.blockName);
                ((DetailBrief) holder).tvDetailWhitePaper.setText(bean.whitePaper);
                ((DetailBrief) holder).tvDetailExchangeName.setText(bean.exchangeName);
                ((DetailBrief) holder).tvDetailReleaseTime.setText(bean.releaseTime);
            }
        } else if (holder instanceof InfoHeader) {
        } else if (holder instanceof InfoItem) {
            int realPosition = position - this.mData.mmList.size() - this.mData.dealList.size() - 5;
            CoinDetailResult.Info bean = this.mData.infoList.get(realPosition);
            ((InfoItem) holder).tvInfoTitle.setText(bean.title);
            ((InfoItem) holder).tvInfoTime.setText(bean.timeStamp);
            if (realPosition == this.mData.infoList.size() - 1) {
                ((InfoItem) holder).bottomLine.setVisibility(View.GONE);
                ((InfoItem) holder).bottomMargin.setVisibility(View.VISIBLE);
            }
        }
    }

    public class KLineHeader extends RecyclerView.ViewHolder {

        public KLineHeader(View itemView) {
            super(itemView);
        }
    }

    public class MMHeader extends RecyclerView.ViewHolder {

        CoinProgressView progressView;

        public MMHeader(View itemView) {
            super(itemView);
            progressView = itemView.findViewById(R.id.item_coin_progress);
        }
    }

    public class MMItem extends RecyclerView.ViewHolder {
        TextView tvBuyPrice;
        TextView tvBuyCount;
        TextView tvSellerPrice;
        TextView tvSellerCount;
        View bottomLine;

        public MMItem(View itemView) {
            super(itemView);
            tvBuyPrice = itemView.findViewById(R.id.tv_buy_price);
            tvBuyCount = itemView.findViewById(R.id.tv_buy_count);
            tvSellerPrice = itemView.findViewById(R.id.tv_seller_price);
            tvSellerCount = itemView.findViewById(R.id.tv_seller_count);
            bottomLine = itemView.findViewById(R.id.mm_bottom_line);
        }
    }

    public class DealHeader extends RecyclerView.ViewHolder {

        public DealHeader(View itemView) {
            super(itemView);
        }
    }

    public class DealItem extends RecyclerView.ViewHolder {
        TextView tvDealTime;
        TextView tvDealPrice;
        TextView tvDealVolume;
        View bottomLine;

        public DealItem(View itemView) {
            super(itemView);
            tvDealTime = itemView.findViewById(R.id.tv_deal_time);
            tvDealPrice = itemView.findViewById(R.id.tv_deal_price);
            tvDealVolume = itemView.findViewById(R.id.tv_deal_volume);
            bottomLine = itemView.findViewById(R.id.deal_bottom_line);
        }
    }

    public class DetailBrief extends RecyclerView.ViewHolder {
        TextView tvDetailBrief;
        TextView tvDetailNameEN;
        TextView tvDetailNameCN;
        TextView tvDetailWebSite;
        TextView tvDetailBlock;
        TextView tvDetailExchangeName;
        TextView tvDetailReleaseTime;
        TextView tvDetailWhitePaper;

        public DetailBrief(View itemView) {
            super(itemView);
            tvDetailBrief = itemView.findViewById(R.id.tv_coin_detail_brief);
            tvDetailNameEN = itemView.findViewById(R.id.tv_coin_detail_en_name);
            tvDetailNameCN = itemView.findViewById(R.id.tv_coin_detail_cn_name);
            tvDetailWebSite = itemView.findViewById(R.id.tv_coin_detail_web_site);
            tvDetailBlock = itemView.findViewById(R.id.tv_coin_detail_block);
            tvDetailExchangeName = itemView.findViewById(R.id.tv_coin_detail_exchange_name);
            tvDetailReleaseTime = itemView.findViewById(R.id.tv_coin_detail__release_time);
            tvDetailWhitePaper = itemView.findViewById(R.id.tv_coin_detail_white_paper);
        }
    }

    public class InfoHeader extends RecyclerView.ViewHolder {

        public InfoHeader(View itemView) {
            super(itemView);
        }
    }

    public class InfoItem extends RecyclerView.ViewHolder {
        TextView tvInfoTitle;
        TextView tvInfoTime;
        View bottomLine;
        View bottomMargin;

        public InfoItem(View itemView) {
            super(itemView);
            tvInfoTitle = itemView.findViewById(R.id.tv_info_title);
            tvInfoTime = itemView.findViewById(R.id.tv_info_time);
            bottomLine = itemView.findViewById(R.id.bottom_Line);
            bottomMargin = itemView.findViewById(R.id.bottom_Margin);
        }
    }

}