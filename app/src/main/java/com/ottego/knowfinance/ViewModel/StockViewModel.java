package com.ottego.knowfinance.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StockViewModel extends ViewModel {
    MutableLiveData<String> mutableLiveData=new MutableLiveData<>();

public void setText(String s)
    {
        mutableLiveData.setValue(s);
    }

    public MutableLiveData<String> getText()
    {
        return mutableLiveData;
    }

}
