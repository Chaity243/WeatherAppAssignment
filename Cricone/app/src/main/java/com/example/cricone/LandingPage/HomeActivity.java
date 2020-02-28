package com.example.cricone.LandingPage;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cricone.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.wv_home)
    WebView webView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        getData();
    }

    private void setUrl(String username, String password) {

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://criconetonline.com/webviewhome?username="+username+"&password="+password);
    }


    private void getData(){
        Intent intent = getIntent();
        if (intent!=null){
            String username =  intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            setUrl(username,password);
        }

    }
}
