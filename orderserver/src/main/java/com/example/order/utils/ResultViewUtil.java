package com.example.order.utils;


import com.example.order.view_object.ResultView;

public class ResultViewUtil {

    public static ResultView success(Object data) {
        ResultView resultView = new ResultView();
        resultView.setCoed(0);
        resultView.setMsg("success");
        resultView.setData(data);
        return resultView;
    }
}
