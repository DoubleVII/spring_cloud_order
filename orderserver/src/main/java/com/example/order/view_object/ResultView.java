package com.example.order.view_object;

/**
 * http请求返回的最外层对象
 */
public class ResultView<T> {

    /**
     * 错误码
     */
    private Integer coed;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private T data;

    public Integer getCoed() {
        return coed;
    }

    public void setCoed(Integer coed) {
        this.coed = coed;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
