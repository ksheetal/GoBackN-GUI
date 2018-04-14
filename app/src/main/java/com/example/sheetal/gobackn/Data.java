package com.example.sheetal.gobackn;

/**
 * Created by sheetal on 4/14/2018.
 */

public class Data {

    public int ack ;
    public String message ;

    public Data() {
    }

    public Data(int ack, String message) {

        this.ack = ack;
        this.message = message;
    }

    public int  getAck() {
        return ack;
    }

    public void setAck(int ack) {
        this.ack = ack;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
