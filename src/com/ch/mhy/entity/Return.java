package com.ch.mhy.entity;

public class Return {

    private boolean result;
    private String message;
    private Object object;//返回对象

    public Return(boolean result, String message, Object object) {
        super();
        this.result = result;
        this.message = message;
        this.object = object;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
