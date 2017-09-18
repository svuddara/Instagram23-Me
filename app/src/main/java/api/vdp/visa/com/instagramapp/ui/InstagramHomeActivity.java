package api.vdp.visa.com.instagramapp.ui;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.StringTokenizer;

import api.vdp.visa.com.instagramapp.R;
import api.vdp.visa.com.instagramapp.repository.dao.InstagramUserImagesDAO;
import api.vdp.visa.com.instagramapp.utils.CookieUtils;
import api.vdp.visa.com.instagramapp.utils.SharedPreferencesUtils;
import api.vdp.visa.com.instagramapp.viewmodel.InstagramViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import api.vdp.visa.com.instagramapp.utils.OAuthConstants;

public class InstagramHomeActivity extends LifecycleActivity {

    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_home);
        ButterKnife.bind(this);
        loadWebView();
    }

    private void loadWebView(){
        String url = OAuthConstants.AUTH_URL;
        webView.setWebViewClient(new InstagramWebViewClient());
        webView.clearCache(true);
        CookieUtils.clearCookies(this);
        webView.loadUrl(url);
    }

    private void sendToPosts(String accessToken){
        startActivity(new Intent(getBaseContext(),PhotosListActivity.class));
    }


    public class InstagramWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            String REDIRECT_URL = "https://www.23andme.com/#access_token=";
            if(url.startsWith(REDIRECT_URL)){
                String accessToken = url.substring(REDIRECT_URL.length());
                SharedPreferencesUtils.insertIntoSharedPreferences(getApplicationContext(),OAuthConstants.ACCESS_TOKEN,accessToken);
                sendToPosts(accessToken);
            }else{
                super.onPageStarted(view, url, favicon);
            }
        }
    }



}
