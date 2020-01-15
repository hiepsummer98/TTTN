package com.hiepsummer.docbao.ui.law;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LawViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LawViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}