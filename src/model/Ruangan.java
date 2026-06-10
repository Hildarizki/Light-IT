package model;

import java.util.ArrayList;

public class Ruangan {

    private String namaRuangan;

    private ArrayList<PerangkatElektronik> perangkat =
            new ArrayList<>();

    public Ruangan(String namaRuangan) {
        this.namaRuangan = namaRuangan;
    }

    public void tambahPerangkat(
            PerangkatElektronik p) {

        perangkat.add(p);
    }

    public void nyalakanSemua() {

        for (PerangkatElektronik p : perangkat) {
            p.nyalakan();
        }
    }

    public void matikanSemua() {

        for (PerangkatElektronik p : perangkat) {
            p.matikan();
        }
    }
}