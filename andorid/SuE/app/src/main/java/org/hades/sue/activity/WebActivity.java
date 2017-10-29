package org.hades.sue.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.hades.sue.R;
import org.hades.sue.base.BaseActivity;
import org.hades.sue.bean.HeathNews;

import butterknife.BindView;
import cn.bingoogolapple.titlebar.BGATitleBar;

public class WebActivity extends BaseActivity {

    private static final String TAG = WebActivity.class.getSimpleName();

    public static final String DATA_KEY = "data";

    @BindView(R.id.my_title_bar_back)
    BGATitleBar mTitleBar;

    @BindView(R.id.wv_content)
    WebView mWebContent;

    @BindView(R.id.index_progressBar)
    ProgressBar mProgressBar;

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public View getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void initViews() {
        initBar();
        initWeb();

    }

    private void initWeb() {
        //声明WebSettings子类
        WebSettings webSettings = mWebContent.getSettings();

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        mWebContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mProgressBar.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        //显示进度
        mWebContent.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, newProgress+"");
                mProgressBar.setProgress(newProgress);
                if(newProgress >= 100){
                    mProgressBar.setVisibility(View.GONE);
                }

            }

        });

    }

    private void initBar() {
        mTitleBar.setTitleText(R.string.titile_jkzx);
        mTitleBar.setDelegate(new BGATitleBar.Delegate() {
            @Override
            public void onClickLeftCtv() {
                WebActivity.this.finish();
            }

            @Override
            public void onClickTitleCtv() {

            }

            @Override
            public void onClickRightCtv() {

            }

            @Override
            public void onClickRightSecondaryCtv() {

            }
        });
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        HeathNews data = (HeathNews) bundle.getSerializable(DATA_KEY);
        mWebContent.loadUrl(data.link);
    }

    public static void startActivity(Context context, HeathNews data) {
        Intent intent = new Intent();
        intent.setClass(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WebActivity.DATA_KEY, data);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


}
