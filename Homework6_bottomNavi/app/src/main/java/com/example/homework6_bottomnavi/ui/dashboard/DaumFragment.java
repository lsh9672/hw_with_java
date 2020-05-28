package com.example.homework6_bottomnavi.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.homework6_bottomnavi.R;

public class DaumFragment extends Fragment {

    private DaumViewModel DaumViewModel;
    WebView webview_daum;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DaumViewModel =
                ViewModelProviders.of(this).get(DaumViewModel.class);
        View root = inflater.inflate(R.layout.fragment_daum, container, false);
        webview_daum= (WebView)root.findViewById(R.id.webview_daum);
        webview_daum.setWebViewClient(new WebViewClient());
        webview_daum.getSettings().setJavaScriptEnabled(true);
        webview_daum.loadUrl("http://m.daum.net");

        DaumViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });





        return root;
    }
}