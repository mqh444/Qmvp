package com.gmail.mqh444.qmvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.mqh444.qmvp.R;
import com.gmail.mqh444.qmvp.retrofit.ApiStores;
import com.gmail.mqh444.qmvp.retrofit.AppClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Louis on 2016/11/2.
 */

public class BaseActivity extends AppCompatActivity {
    public Activity mActivity;
    public ApiStores apiStores = AppClient.retrofit().create(ApiStores.class);
    private CompositeSubscription mCompositeSubscription;
    private List<Call> calls;

    @Override
    public void setContentView(@LayoutRes int layoutResID){
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    public void setContentView(View view){
        super.setContentView(view);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params){
        super.setContentView(view,params);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    protected void onDestroy(){
        callCancel();
        onUnsubscribe();
        super.onDestroy();
    }

    public void addCalls(Call call){
        if (calls == null){
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size()>0){
            for (Call call : calls){
                if (!call.isCanceled()){
                    call.cancel();
                }
            }
            calls.clear();
        }
    }

    public void addSubscription(Observable observable, Subscriber subscriber){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
        );
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    private void onUnsubscribe() {
        // LogUtil.d("onUnsubscribe");
        // 取消注册，以避免内存泄露
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    public Toolbar initToolBar(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            toolbarTitle.setText(title);
        }
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    public Toolbar initToolBarAsHome(String title){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            toolbarTitle.setText(title);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void toastShow(int resId){
        Toast.makeText(mActivity,resId,Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resID){
        Toast.makeText(mActivity,resID,Toast.LENGTH_SHORT).show();
    }

    public ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog(){
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("加载中");
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message){
        progressDialog =new ProgressDialog(mActivity);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog(){
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
