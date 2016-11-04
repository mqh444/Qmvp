package com.gmail.mqh444.qmvp.mvp.other;

import android.os.Bundle;

import com.gmail.mqh444.qmvp.ui.BaseActivity;

/**
 * Created by Louis on 2016/11/3.
 */

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null){
            mvpPresenter.detachView();
        }
    }

    public void showLoading(){
        showProgressDialog();
    }

    public void hideLoading(){
        dismissProgressDialog();
    }
}
