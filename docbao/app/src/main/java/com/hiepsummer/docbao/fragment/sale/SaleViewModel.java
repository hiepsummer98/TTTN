package com.hiepsummer.docbao.fragment.sale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SaleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SaleViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}