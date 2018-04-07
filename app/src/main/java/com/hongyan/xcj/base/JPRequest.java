package com.hongyan.xcj.base;

import com.hongyan.xcj.core.AccountManager;
import com.hongyan.xcj.core.LogUtils;
import com.hongyan.xcj.modules.event.TokenMessageEvent;
import com.hongyan.xcj.network.AuthFailureError;
import com.hongyan.xcj.network.DefaultRetryPolicy;
import com.hongyan.xcj.network.NetworkResponse;
import com.hongyan.xcj.network.Request;
import com.hongyan.xcj.network.Response;
import com.hongyan.xcj.network.VolleyError;
import com.hongyan.xcj.network.toolbox.HttpHeaderParser;
import com.hongyan.xcj.utils.AppMD5Util;
import com.hongyan.xcj.utils.GsonUtils;
import com.hongyan.xcj.utils.MD5Utils;
import com.hongyan.xcj.utils.SerializableHashMapUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class JPRequest<T extends JPResult> extends Request<JPResponse> {

    private Response.Listener<JPResponse> mResponseListener;
    private Response.ErrorListener mErrorListener;
    private String mUrl;
    private Class<T> mResultClass = null;
    private SerializableHashMapUtils mMap = new SerializableHashMapUtils();
    private boolean checkLogin = false;


    public JPRequest(Class<T> resultClass, String url, Response.Listener<JPResponse> listener, Response.ErrorListener errorListener) {
        this(Method.POST, url, listener, errorListener);
        mResultClass = resultClass;
        mErrorListener = errorListener;
        mUrl = url;
        addCommonParams();
    }

    private JPRequest(int method, String url, Response.Listener<JPResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mResponseListener = listener;
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(30 * 1000, 0, 1.0f);
        setRetryPolicy(retryPolicy);
        setShouldCache(true);
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> tHeaderMap = new HashMap<>();
        return tHeaderMap;
    }

    @Override
    protected Map<String, String> getPostParams() throws AuthFailureError {
        return mMap.getMap();
    }

    @Override
    protected void deliverResponse(JPResponse response) {
        if (mResponseListener != null) {
            if (response == null) {
                response = new JPResponse();
            }
            mResponseListener.onResponse(response);
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        if (mErrorListener != null) {
            error.setErrorMessage("网络连接异常");
            mErrorListener.onErrorResponse(error);
        }
    }

    @Override
    protected Response<JPResponse> parseNetworkResponse(NetworkResponse networkResponse) {
        if (mResponseListener != null && networkResponse != null) {
            JPResponse response = new JPResponse();
            String string;
            try {
                string = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            } catch (UnsupportedEncodingException e) {
                string = new String(networkResponse.data);
            }
            response.setResponse(string);
            JPResult result = GsonUtils.gsonResolve(string, mResultClass);
            if (checkLogin && result != null && ("50002".equals(result.getReturnCode()) || "50003".equals(result.getReturnCode()))) {
                EventBus.getDefault().post(new TokenMessageEvent());
            }
            response.setResult(result);
            return Response.success(response, HttpHeaderParser.parseCacheHeaders(networkResponse));
        }
        return null;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }

    public void addParam(String key, String value) {
        if (value == null) {
            return;
        }
        mMap.put(key, value);
    }

    public void setCheckLogin(boolean checkLogin) {
        this.checkLogin = checkLogin;
    }

    public void addParam(String key, int value) {
        mMap.put(key, String.valueOf(value));
    }

    public Map<String, String> getParams() {
        return mMap.getMap();
    }

    private void addCommonParams() {
        String currentTime = System.currentTimeMillis() + "";
        String time = currentTime.substring(0, 10);
//        String sign = AppMD5Util.getMD5(time + "086bb83dfd123ac1a012c58096bfea04");
        String sign = MD5Utils.toMD5(time + "086bb83dfd123ac1a012c58096bfea04").toLowerCase();
//        LogUtils.e("时间戳：" + time + "\n验签字符串：" + time + "086bb83dfd123ac1a012c58096bfea04" + "\n验签结果：" + AppMD5Util.getMD5(time + "086bb83dfd123ac1a012c58096bfea04"));
        mMap.put("sdkClientVersion", "1.0.0");
        mMap.put("appID", "1");
        mMap.put("platform", "android");
        mMap.put("sign", sign);
        mMap.put("timestamp", time);
        if (null != AccountManager.getInstance().getAccountInfo()) {
            mMap.put("token", AccountManager.getInstance().getAccountInfo().getToken());
        }
    }
}

