package com.lightit.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Ruangan {

    private final String namaRuangan;
    private final List<PerangkatElektronik> daftarPerangkat = new ArrayList<>();

    public Ruangan(String nama) {
        this.namaRuangan = nama;
    }

    public String getNamaRuangan() {
        return namaRuangan;
    }

    public void tambahPerangkat(PerangkatElektronik p) {
        daftarPerangkat.add(p);
    }

    public List<PerangkatElektronik> getDaftarPerangkat() {
        return daftarPerangkat;
    }

    /**
     * Menghitung jumlah perangkat yang sedang aktif di ruangan ini
     */
    public int jumlahPerangkatAktif() {
        int count = 0;
        for (PerangkatElektronik p : daftarPerangkat) {
            if (p.isNyala()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Mematikan seluruh perangkat yang ada di dalam ruangan ini
     */
    public void matikanSemua() {
        for (PerangkatElektronik p : daftarPerangkat) {
            if (p.isNyala()) {
                p.matikan();
            }
        }
    }

    /**
     * Menyalakan seluruh perangkat yang ada di dalam ruangan ini
     */
    public void nyalakanSemua() {
        for (PerangkatElektronik p : daftarPerangkat) {
            if (!p.isNyala()) {
                p.nyalakan();
            }
        }
    }
}