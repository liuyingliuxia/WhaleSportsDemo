package com.lolinico.technical.open.entity;

import java.util.List;

/**
 * Created by Rico on 2017/12/25.
 */

public class Event<T> {
    private int code;
    private T data;

    public Event(int code) {
        this.code = code;
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getEventCode() {
        return code;
    }

    public void setEventCode(int code) {
        this.code = code;
    }

    public T getEventData() {
        return data;
    }

    public void setEventData(T data) {
        this.data = data;
    }

    public interface onResponedObjecListener {
        void onReturnObject(List list);
    }
}
