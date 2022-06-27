package edu.haui.bvdong.quizapp.dto;

import java.io.Serializable;

public class JsonGeneratorDto<T> implements Serializable{
    private boolean status;
    private T msg;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    public JsonGeneratorDto(boolean status, T msg) {
        this.status = status;
        this.msg = msg;
    }
}
