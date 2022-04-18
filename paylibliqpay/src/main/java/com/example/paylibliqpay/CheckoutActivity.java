//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.paylibliqpay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.utils.URLEncodedUtils;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EncodingUtils;

import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public final class CheckoutActivity extends Activity {
    WebView mWebView;

    public CheckoutActivity() {
    }

    @SuppressLint({"ResourceType", "SetJavaScriptEnabled"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(1);
        this.setTheme(16974124);
        this.mWebView = new WebView(this);
        this.setContentView(this.mWebView);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String postData = this.getIntent().getStringExtra("postData");
        this.mWebView.postUrl(LiqPayUtil.LIQPAY_API_URL_CHECKOUT, EncodingUtils.getBytes(postData, "BASE64"));
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setDomStorageEnabled(true);
        this.mWebView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                LogUtil.log("==== onPageFinished:" + url);
                progressDialog.cancel();
                super.onPageFinished(view, url);
            }

            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);

                LogUtil.log(url);
                String startUrl = "/api/mob/webview";
                if (url.contains(startUrl)) {
                    LogUtil.log("==== checkout resp:" + url);

                    try {
                        JSONObject data = CheckoutActivity.parseUrl(url);
                        Intent intent = new Intent("ua.privatbank.paylibliqpay.broadcast");
                        intent.setPackage(CheckoutActivity.this.getPackageName());
                        intent.putExtra("data", data.toString());
                        CheckoutActivity.this.sendBroadcast(intent);
                    } catch (Exception var6) {
                        var6.printStackTrace();
                        CheckoutActivity.this.sendBroadcast(new Intent("ua.privatbank.paylibliqpay.broadcast"));
                    }

                    CheckoutActivity.this.finish();
                }

            }
        });
    }

    public static JSONObject parseUrl(String url) throws URISyntaxException {
        List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");
        JSONObject data = new JSONObject();

        for (NameValuePair param : params) {
            try {
                if ("data".equals(param.getName())) {
                    JSONObject object = new JSONObject(new String(Base64.decode(param.getValue(), 2)));
                    LiqPayUtil.addAll(data, object);
                } else {
                    data.put(param.getName(), param.getValue());
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

        return data;
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        this.mWebView.destroy();
        this.sendBroadcast(new Intent("ua.privatbank.paylibliqpay.broadcast"));
        super.onDestroy();
    }
}
