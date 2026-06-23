package com.lightit.demo.model;

public class Kipas extends PerangkatElektronik {

    public Kipas(String kode, String nama, double daya) {
        super(kode, nama, daya);
    }

    @Override
    public String getJenis() {
        return "Kipas";
    }
}