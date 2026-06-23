package com.lightit.demo.model;

public class StopKontak extends PerangkatElektronik {

    public StopKontak(String kode, String nama, double daya) {
        super(kode, nama, daya);
    }

    @Override
    public String getJenis() {
        return "StopKontak";
    }
}