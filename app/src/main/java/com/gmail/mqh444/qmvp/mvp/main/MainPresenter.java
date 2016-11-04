package com.gmail.mqh444.qmvp.mvp.main;

import com.gmail.mqh444.qmvp.mvp.other.BasePresenter;
import com.gmail.mqh444.qmvp.retrofit.ApiCallback;

/**
 * Created by Louis on 2016/11/3.
 */

public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view){
        attachView(view);
    }

    public void loadDataByRetrofitRxjava(String cityId){
        mvpView.showLoading();
        addSubscription(apiStores.loadDataByRetrofitRxjava(cityId),
                new ApiCallback<MainModel>() {
                    @Override
                    public void onSuccess(MainModel model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }
}
