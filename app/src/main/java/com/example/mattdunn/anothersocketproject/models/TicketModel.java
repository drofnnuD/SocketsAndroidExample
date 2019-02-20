package com.example.mattdunn.anothersocketproject.models;

import org.json.JSONObject;

public class TicketModel {

    private String name, time, barcode, scannerName;
    private Boolean entered;

    public TicketModel(JSONObject object) {
        try {
            this.name = object.getString("name");
            this.time = object.getString("time");
            this.barcode = object.getString("barcode");
            this.scannerName = object.getString("scannerName");
            this.entered = object.getBoolean("entered");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getScannerName() {
        return scannerName;
    }

    public Boolean getEntered() {
        return entered;
    }

    public void setEntered(Boolean entered) {
        this.entered = entered;
    }
}
