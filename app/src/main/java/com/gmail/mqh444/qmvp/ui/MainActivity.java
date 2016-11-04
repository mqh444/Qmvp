package com.gmail.mqh444.qmvp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gmail.mqh444.qmvp.R;
import com.gmail.mqh444.qmvp.mvp.main.MainModel;
import com.gmail.mqh444.qmvp.mvp.main.MainPresenter;
import com.gmail.mqh444.qmvp.mvp.main.MainView;
import com.gmail.mqh444.qmvp.mvp.other.MvpActivity;
import com.gmail.mqh444.qmvp.retrofit.ApiCallback;
import com.gmail.mqh444.qmvp.retrofit.RetrofitCallback;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;


/**
 * Created by Louis on 2016/11/3.
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    @BindView(R.id.strText)
    protected TextView strText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBarAsHome("MVP+Retrofit+Rxjava");
    }

    protected MainPresenter createPresenter(){
        return new MainPresenter(this);
    }

    @Override
    public void getDataSuccess(MainModel model) {
        // 接口成功回调
        dataSuccess(model);
    }

    @Override
    public void getDataFail(String msg) {
        toastShow("网络不给力");
    }

    @OnClick({R.id.button0,R.id.button1,R.id.button2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button0:
                loadDataByRetrofit();
                break;
            case R.id.button1:
                loadDataByRetrofitRxjava();
                break;
            case R.id.button2:
                // 请求接口
                mvpPresenter.loadDataByRetrofitRxjava("101030600");
                break;
        }
    }

    private void loadDataByRetrofit() {
        showProgressDialog();
        Call<MainModel> call = apiStores.loadDataByRetrofit("101190201");
        call.enqueue(new RetrofitCallback<MainModel>() {
            @Override
            public void onSuccess(MainModel model) {
                dataSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                toastShow(msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                toastShow(t.getMessage());
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
        addCalls(call);
    }

    // 全国+国外主要城市代码http://mobile.weather.com.cn/js/city/citylist.xml
    private void loadDataByRetrofitRxjava(){
        showProgressDialog();
        addSubscription(apiStores.loadDataByRetrofitRxjava("101310222"),
                new ApiCallback<MainModel>() {
                    @Override
                    public void onSuccess(MainModel model) {
                        dataSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        toastShow(msg);
                    }

                    @Override
                    public void onFinish() {
                        dismissProgressDialog();
                    }
                });
    }

    @BindString(R.string.city)
    protected String label_city;
    @BindString(R.string.wd)
    protected String label_wd;
    @BindString(R.string.ws)
    protected String label_ws;
    @BindString(R.string.time)
    protected String label_time;
    private void dataSuccess(MainModel model) {
        MainModel.WeatherinfoBean weatherinfo = model.getWeatherinfo();
        String showData = label_city + weatherinfo.getCity()
                + label_wd + weatherinfo.getWD()
                + label_ws + weatherinfo.getWS()
                + label_time + weatherinfo.getTime();
        strText.setText(showData);
    }
}
