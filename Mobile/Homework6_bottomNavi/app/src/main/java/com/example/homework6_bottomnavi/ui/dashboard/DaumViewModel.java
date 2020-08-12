package com.example.homework6_bottomnavi.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DaumViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DaumViewModel() {
        mText = new MutableLiveData<>();
       mText.setValue("This is daum fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}