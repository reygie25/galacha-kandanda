package kandanda.galacha.com.kandanda;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kandanda.galacha.com.kandanda.helper.Commons;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private WebView webView;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
    private LinearLayout llNoInternet;
    private Button btnRefresh;
    private RelativeLayout rlToolbar;
    private ImageView ivHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressbar);
        llNoInternet = findViewById(R.id.ll_no_internet);
        btnRefresh = findViewById(R.id.btn_refresh);
        rlToolbar = findViewById(R.id.rl_toolbar);
        ivHome = findViewById(R.id.iv_home);

        btnRefresh.setOnClickListener(this);
        ivHome.setOnClickListener(this);

        loadDefaultUrl();
    }

    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
    }

    private void hideLoading(){
        progressBar.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
    }

    private void initWebView(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        showLoading();

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(url.contains("facebook") || url.contains("twitter") || url.contains("google"))
                    rlToolbar.setVisibility(View.VISIBLE);
                else
                    rlToolbar.setVisibility(View.GONE);
                if(progressBar.isShown())
                    hideLoading();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        webView.loadUrl(getString(R.string.web_url));
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        webView.loadUrl(getString(R.string.web_url));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_refresh:
                loadDefaultUrl();
                break;
            case R.id.iv_home:
                loadDefaultUrl();
                break;
        }
    }

    private void loadDefaultUrl(){
        if (Commons.isNetworkAvailable(this)) {
            llNoInternet.setVisibility(View.GONE);
            rlToolbar.setVisibility(View.GONE);
            initWebView();
        } else {
            hideLoading();
            llNoInternet.setVisibility(View.VISIBLE);
        }
    }
}
