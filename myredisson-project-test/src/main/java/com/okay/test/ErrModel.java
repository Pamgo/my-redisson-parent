package com.okay.test;

/**
 * <h2><h2>
 *
 * @author okay
 * @create 2020-07-14 13:52
 */
public class ErrModel {
    private Integer err;
    private String msg;

    public ErrModel(Integer err, String msg) {
        this.err = err;
        this.msg = msg;
    }

    public Integer getErr() {
        return err;
    }

    public void setErr(Integer err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
