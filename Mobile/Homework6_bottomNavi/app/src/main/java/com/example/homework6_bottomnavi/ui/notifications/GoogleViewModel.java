package com.example.homework6_bottomnavi.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GoogleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GoogleViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is google fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}