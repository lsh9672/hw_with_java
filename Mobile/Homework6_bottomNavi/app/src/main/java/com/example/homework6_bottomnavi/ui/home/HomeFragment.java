package com.example.homework6_bottomnavi.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.homework6_bottomnavi.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    WebView webview_naver;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_naver, container, false);
        webview_naver= (WebView)root.findViewById(R.id.webview_naver);
        webview_naver.setWebViewClient(new WebViewClient());
        webview_naver.getSettings().setJavaScriptEnabled(true);
        webview_naver.loadUrl("http://m.naver.com");

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });



        return root;
    }
}