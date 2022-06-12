package com.mini.rpc.resp;

public class ResponseSample {
    String code;

    String data;

    Long  timestamp;
    public ResponseSample(String ok, String data, long currentTimeMillis) {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimestamp() {
       return  this.timestamp;
    }
}
