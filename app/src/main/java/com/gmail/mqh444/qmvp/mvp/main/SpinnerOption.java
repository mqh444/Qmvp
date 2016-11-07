package com.gmail.mqh444.qmvp.mvp.main;

/**
 * Created by Louis on 2016/11/7.
 * 各城市下拉列表使用
 * ID为城市编码
 * Value为城市名称
 */

public class SpinnerOption {
    private String ID = "";
    private String Value = "";

    public SpinnerOption(){
        ID = "";
        Value = "";
    }

    public String CityItem(String _ID,String _Value){
        ID = _ID;
        Value = _Value;
    }

    @Override
    public String toString(){
        return Value;
    }

    public String GetID(){
        return ID;
    }

    public String getValue(){
        return Value;
    }
}
