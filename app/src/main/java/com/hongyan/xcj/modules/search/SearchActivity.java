package com.hongyan.xcj.modules.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hongyan.xcj.R;
import com.hongyan.xcj.base.BaseActivity;
import com.hongyan.xcj.base.JPBaseModel;
import com.hongyan.xcj.base.JPRequest;
import com.hongyan.xcj.base.JPResponse;
import com.hongyan.xcj.base.UrlConst;
import com.hongyan.xcj.modules.setting.SetNickNameResult;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.utils.StringUtils;
import com.umeng.analytics.MobclickAgent;

public class SearchActivity extends BaseActivity {


    private EditText editText;
    private ListView listView;
    private LinearLayout linearNoData;

    private String searchKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        hideNavigationView();
        editText = findViewById(R.id.et_search);
        linearNoData = findViewById(R.id.linear_no_data);
        listView = findViewById(R.id.listView);
        searchKey = getIntent().getStringExtra("searchText");
        if (!StringUtils.isEmpty(searchKey)) {
            editText.setText(searchKey);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void search() {
        String nickName = editText.getText().toString();
        if (StringUtils.isEmpty(nickName)) {
            showErrorToast("请输入昵称");
        }
        JPRequest request = new JPRequest<>(SearchResult.class, UrlConst.getSearchUrl(), new Response.Listener<JPResponse>() {
            @Override
            public void onResponse(JPResponse response) {
                if (null == response || null == response.getResult()) {
                    return;
                }
                SearchResult result = (SearchResult) response.getResult();
                if (result != null && result.isSuccessful()) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorToast(error.getErrorMessage());
            }
        });
        request.addParam("nickname", nickName);
        JPBaseModel baseModel = new JPBaseModel();
        baseModel.sendRequest(request);
    }
}
