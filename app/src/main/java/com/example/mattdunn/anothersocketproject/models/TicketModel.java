package com.example.mattdunn.anothersocketproject.models;

import org.json.JSONObject;

public class TicketModel {

    private String barcode, ticketName, String;
    private boolean redeemed;

    public TicketModel(JSONObject object) {
        try {
            this.barcode = object.getString("barcode");
            this.ticketName = object.getString("tickeName");
            this.String = object.getString("string");
            this.redeemed = object.getBoolean("redeemed");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public java.lang.String getBarcode() {
        return barcode;
    }

    public java.lang.String getTicketName() {
        return ticketName;
    }

    public java.lang.String getString() {
        return String;
    }

    public boolean getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed){
        this.redeemed = redeemed;
    }
}
