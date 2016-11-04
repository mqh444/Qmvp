package com.gmail.mqh444.qmvp.mvp.other;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gmail.mqh444.qmvp.ui.BaseFragment;

/**
 * Created by Louis on 2016/11/3.
 */

public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null){
            mvpPresenter.detachView();
        }
    }
}
