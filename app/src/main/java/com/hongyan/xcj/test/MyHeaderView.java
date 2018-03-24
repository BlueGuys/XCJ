package com.hongyan.xcj.test;

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
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.xcj.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by feilu-PC on 2018/3/24.
 */

public class MyHeaderView extends LinearLayout{
    private int oldPos = 0;
    private int currentPos = 0;
    private ViewPager viewPager;
    private HeaderVpAdapter headerVpAdapter;
    private FrameLayout headerviewpager_layout;
    private TextView tvTitle;
    private LinearLayout layout;
    private Context mContext;

    private OnPageClickListener onPageClickListener;

    public MyHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        intiHomeheader();

    }

//    public void setEntryList(List<Entity> newsListEntities){
//        this.newsListEntities = newsListEntities;
//        headerVpAdapter.notifyDataSetChanged();
//    }

public void init(){
    ScheduledExecutorService service = Executors
            .newSingleThreadScheduledExecutor();
    service.scheduleAtFixedRate(new CommandTask(), 4, 4, TimeUnit.SECONDS);
}

public void initData(final List<Entity> newsListEntities,OnPageClickListener onPageClickListener){
    this.onPageClickListener = onPageClickListener;

    headerVpAdapter = new HeaderVpAdapter(mContext, newsListEntities);
    for (int i = 0; i < newsListEntities.size(); i++) {
        if (i == 0) {
            layout.addView(getView(mContext,false));

        } else {

            layout.addView(getView(mContext,true));
        }
    }
    viewPager.setAdapter(headerVpAdapter);

    tvTitle.setText(newsListEntities.get(0).getTitle()!=null?newsListEntities.get(0).getTitle():"");

    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {

            currentPos = arg0;
            try{
                if (newsListEntities.size() > 0) {
                    tvTitle.setText(newsListEntities.get(
                            arg0 % newsListEntities.size()).getTitle());
                    View view = layout.getChildAt(arg0
                            % newsListEntities.size());
                    if(view!=null){

                        view.setBackgroundResource(R.drawable.yuan_pressed);
                    }

                    View view2 = layout.getChildAt(oldPos
                            % newsListEntities.size());
                    if(view2!=null) {
                        view2.setBackgroundResource(R.drawable.yuan_normal);
                    }
                }
            }catch (Exception e){



            }

            oldPos = arg0;

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    });

    init();
}

    public void intiHomeheader() {
        View v = View.inflate(mContext,
                R.layout.fragment_home_headerviewpager,this);
        headerviewpager_layout = (FrameLayout) v.findViewById(R.id.headerviewpager_layout);
        layout = (LinearLayout) v.findViewById(R.id.homeheader_bottomlin);
        tvTitle = (TextView) v.findViewById(R.id.homeheader_title);
        viewPager = (ViewPager) v.findViewById(R.id.homeheader_viewpager);


    }

    public static class Entity {

        private String  imageurl;
        private String title;

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
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
            // TODO Auto-generated method stub
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

            // textView.setText(text);
        };
    };

    public View getView(Context context,boolean flag) {
        View view = new View(context);
//		int space_5 = (int) getResources().getDimension(R.dimen.space_5);
//		int space_3 = (int) getResources().getDimension(R.dimen.space_3);

        int space_5 = 15;
        int space_3 = 13;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                space_5, space_5);
        params.setMargins(space_3, 0, space_3, 0);
        view.setLayoutParams(params);
        if (flag) {
            view.setBackgroundResource(R.drawable.yuan_normal);
        } else {
            view.setBackgroundResource(R.drawable.yuan_pressed);

        }
        return view;
    }



    public interface OnPageClickListener {
        void setOnPage(int position);
    }

//    public void setHeight(int dip){
//
//        if(headerviewpager_layout!=null){
//
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) headerviewpager_layout.getLayoutParams();
//
//            params.width =    FrameLayout.LayoutParams.MATCH_PARENT;
//            if(dip<0){
//                dip=170;
//            }
//            params.height =  dip;
//
//            headerviewpager_layout.setLayoutParams(params);
//        }
//
//    }


    public class HeaderVpAdapter extends PagerAdapter {

        private Context context;
        private List<Entity> newsListEntities;
        private DisplayImageOptions imageOptions;
        public HeaderVpAdapter(Context context,
                               List<Entity> mNewsListEntities) {
            this.context = context;
            this.newsListEntities = mNewsListEntities;
            imageOptions = ImageLoaderUtils.initOptions();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            // return datas.length;
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            ((ViewPager) container).removeView((View) object);
            // super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            View view = LayoutInflater.from(context).inflate(
                    R.layout.viewpager_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.home_header_imageView);

            if (newsListEntities.size() > 0) {
                Entity newsListEntity = newsListEntities.get(position
                        % newsListEntities.size());
                if (newsListEntity != null) {
                    ImageLoader.getInstance().displayImage(newsListEntity.getImageurl(), imageView, imageOptions);


                }

            }

            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (onPageClickListener != null) {
                        onPageClickListener.setOnPage(position % newsListEntities.size());
                    }
                }
            });

            ((ViewPager) container).addView(view);
            return view;
        }

    }
}
