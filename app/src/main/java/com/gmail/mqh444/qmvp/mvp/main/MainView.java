package com.gmail.mqh444.qmvp.mvp.main;


/**
 * Created by Louis on 2016/11/3.
 */

public interface MainView extends BaseView {

    void getDataSuccess(MainModel model);

    void getDataFail(String msg);
}
