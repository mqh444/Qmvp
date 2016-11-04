package com.gmail.mqh444.qmvp.mvp.other;

import com.gmail.mqh444.qmvp.retrofit.ApiStores;
import com.gmail.mqh444.qmvp.retrofit.AppClient;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Louis on 2016/11/3.
 */

public class BasePresenter<V> {
    public V mvpView;
    protected ApiStores apiStores;
    private CompositeSubscription mCompositeSubscription;

    public void attachView(V mvpView){
        this.mvpView = mvpView;
        apiStores = AppClient.retrofit().create(ApiStores.class);
    }

    public void detachView(){
        this.mvpView = null;
        onUnsubscribe();
    }

    // Rxjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Observable observable, Subscriber subscriber){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

}
