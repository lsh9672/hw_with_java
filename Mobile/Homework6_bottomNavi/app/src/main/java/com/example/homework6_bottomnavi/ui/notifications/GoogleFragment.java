package com.example.homework6_bottomnavi.ui.notifications;

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

public class GoogleFragment extends Fragment {

    private GoogleViewModel googleViewModel;
    WebView webview_google;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        googleViewModel =
                ViewModelProviders.of(this).get(GoogleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_google, container, false);

        webview_google= (WebView)root.findViewById(R.id.webview_google);
        webview_google.setWebViewClient(new WebViewClient());
        webview_google.getSettings().setJavaScriptEnabled(true);
        webview_google.loadUrl("http://m.google.com");
        googleViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        return root;
    }
}