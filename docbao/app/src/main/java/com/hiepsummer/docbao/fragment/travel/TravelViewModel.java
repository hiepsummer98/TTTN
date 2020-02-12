package com.hiepsummer.docbao.fragment.travel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TravelViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TravelViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}