package com.lightit.demo.model;

public class Lampu extends PerangkatElektronik {

    public Lampu(String kode, String nama, double daya) {
        super(kode, nama, daya);
    }

    @Override
    public String getJenis() {
        return "Lampu";
    }
}