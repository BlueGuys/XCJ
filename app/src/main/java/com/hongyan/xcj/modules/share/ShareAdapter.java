package com.hongyan.xcj.modules.share;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyan.xcj.R;

import java.util.ArrayList;

/**
 * Created by wangning on 2018/4/10.
 */

public class ShareAdapter extends BaseAdapter {


    private ArrayList<ShareChannel> mList = ShareDataFactory.getShareChannelList();
    private Activity mActivity;

    public ShareAdapter(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ShareChannel getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_share_channel, viewGroup, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.image_channel);
            holder.textView = convertView.findViewById(R.id.tv_channel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(getItem(position).getLabel());
        holder.imageView.setImageResource(getItem(position).getDrawable());
        return convertView;
    }

    private static class ViewHolder {
        private TextView textView;
        private ImageView imageView;
    }
}
