package com.hongyan.xcj.modules.coin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hongyan.xcj.R;
import com.hongyan.xcj.modules.coin.widget.CoinView;

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
            return new KLineHeader(view);
        } else if (viewType == ITEM_TYPE_MM_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_mm_item, parent, false);
            return new KLineHeader(view);
        } else if (viewType == ITEM_TYPE_DEAL_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_deal_header, parent, false);
            return new KLineHeader(view);
        } else if (viewType == ITEM_TYPE_DEAL_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_deal_item, parent, false);
            return new KLineHeader(view);
        } else if (viewType == ITEM_TYPE_COIN_DETAIL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_brief, parent, false);
            return new KLineHeader(view);
        } else if (viewType == ITEM_TYPE_INFO_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_info_header, parent, false);
            return new KLineHeader(view);
        } else if (viewType == ITEM_TYPE_INFO_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_detail_info_item, parent, false);
            return new KLineHeader(view);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof KLineHeader) {
            //Do something
        } else if (holder instanceof MMHeader) {
            //Do something
        } else if (holder instanceof MMItem) {
            //Do something
        } else if (holder instanceof DealHeader) {
            //Do something
        } else if (holder instanceof DealItem) {
            //Do something
        } else if (holder instanceof DetailBrief) {
            //Do something
        } else if (holder instanceof InfoHeader) {
            //Do something
        } else if (holder instanceof InfoItem) {
            //Do something
        }
    }

    public class KLineHeader extends RecyclerView.ViewHolder {

        public KLineHeader(View itemView) {
            super(itemView);
        }
    }

    public class MMHeader extends RecyclerView.ViewHolder {

        public MMHeader(View itemView) {
            super(itemView);
        }
    }

    public class MMItem extends RecyclerView.ViewHolder {

        public MMItem(View itemView) {
            super(itemView);
        }
    }

    public class DealHeader extends RecyclerView.ViewHolder {

        public DealHeader(View itemView) {
            super(itemView);
        }
    }

    public class DealItem extends RecyclerView.ViewHolder {

        public DealItem(View itemView) {
            super(itemView);
        }
    }

    public class DetailBrief extends RecyclerView.ViewHolder {

        public DetailBrief(View itemView) {
            super(itemView);
        }
    }

    public class InfoHeader extends RecyclerView.ViewHolder {

        public InfoHeader(View itemView) {
            super(itemView);
        }
    }

    public class InfoItem extends RecyclerView.ViewHolder {

        public InfoItem(View itemView) {
            super(itemView);
        }
    }

}