package com.hiepsummer.docbao.fragment.sport;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SportViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SportViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}