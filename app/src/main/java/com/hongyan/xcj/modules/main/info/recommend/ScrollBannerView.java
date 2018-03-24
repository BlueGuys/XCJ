package com.hongyan.xcj.modules.main.info.recommend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.core.BaseApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 阎苏飞
 * @time 2018-03-24 PM 22:00
 * @desc 提供了一个横向可滚动Banner控件
 * {@link #setData(ArrayList)}  设置图片资源
 */
public class ScrollBannerView extends LinearLayout {

    public interface OnPageClickListener {
        void setOnPage(int position);
    }

    private int oldPos = 0;
    private int currentPos = 0;
    private ViewPager viewPager;
    private HeaderVpAdapter headerVpAdapter;
    private TextView tvTitle;
    private LinearLayout pointLayout;
    private Context mContext;

    private OnPageClickListener mOnPageClickListener;

    private ArrayList<Entity> mList = new ArrayList<>();

    public ScrollBannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new CommandTask(), 4, 4, TimeUnit.SECONDS);

        View view = View.inflate(mContext, R.layout.fragment_home_headerviewpager, this);
        pointLayout = view.findViewById(R.id.homeheader_bottomlin);
        tvTitle = view.findViewById(R.id.homeheader_title);
        viewPager = view.findViewById(R.id.homeheader_viewpager);

        headerVpAdapter = new HeaderVpAdapter();
        viewPager.setAdapter(headerVpAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                currentPos = arg0;
                if (mList.size() > 0) {
                    tvTitle.setText(mList.get(arg0 % mList.size()).getTitle());
                    View view = pointLayout.getChildAt(arg0 % mList.size());
                    if (view != null) {
                        view.setBackgroundResource(R.drawable.yuan_pressed);
                    }
                    View view2 = pointLayout.getChildAt(oldPos % mList.size());
                    if (view2 != null) {
                        view2.setBackgroundResource(R.drawable.yuan_normal);
                    }
                }
                oldPos = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    public void setData(ArrayList<Entity> entityList) {
        if (entityList == null || entityList.size() == 0) {
            return;
        }
        this.mList.addAll(entityList);
        headerVpAdapter.notifyDataSetChanged();
        notifyPointView();
    }

    public void setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.mOnPageClickListener = onPageClickListener;
    }

    /**
     * 小圆点刷新
     */
    private void notifyPointView() {
        tvTitle.setText(mList.get(0).getTitle() != null ? mList.get(0).getTitle() : "");
        pointLayout.removeAllViews();
        for (int i = 0; i < mList.size(); i++) {
            pointLayout.addView(getPointView(mContext, i != 0));
        }
    }

    public static class Entity {

        private String imageUrl;
        private String title;

        String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    class CommandTask implements Runnable {

        @Override
        public void run() {
            currentPos++;
            handler.sendEmptyMessage(0);
        }
    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            if (viewPager != null) {
                viewPager.setCurrentItem(currentPos);
            }
        }

    };

    public View getPointView(Context context, boolean flag) {
        View view = new View(context);
        int space_5 = 15;
        int space_3 = 13;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                space_5, space_5);
        params.setMargins(space_3, 0, space_3, 0);
        view.setLayoutParams(params);
        view.setBackgroundResource(flag ? R.drawable.yuan_normal : R.drawable.yuan_pressed);
        return view;
    }

    public class HeaderVpAdapter extends PagerAdapter {

        private DisplayImageOptions imageOptions;

        HeaderVpAdapter() {
            imageOptions = BaseApplication.getInstance().getOptions();
        }

        @Override
        public int getCount() {
            return mList.size() > 0 ? Integer.MAX_VALUE : 0;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item, null);
            ImageView imageView = view.findViewById(R.id.home_header_imageView);
            Entity newsListEntity = mList.get(position % mList.size());
            ImageLoader.getInstance().displayImage(newsListEntity.getImageUrl(), imageView, imageOptions);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnPageClickListener != null) {
                        mOnPageClickListener.setOnPage(position % mList.size());
                    }
                }
            });
            container.addView(view);
            return view;
        }

    }
}
