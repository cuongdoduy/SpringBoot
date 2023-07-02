package com.springboot.tutorial.models;

public class ResponseObject {
    private String status;
    private String message;
    private Object data;

    public ResponseObject() {
    }

    public ResponseObject(String status,String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }

   public void setStatus(String status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
