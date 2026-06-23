package com.lightit.demo.model;

import com.lightit.demo.interfaces.KontrolDaya;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class PerangkatElektronik implements KontrolDaya {
    protected String kode;
    protected String nama;
    protected double daya;
    protected boolean status;
    protected LocalTime waktuMulaiJadwal;
    protected LocalTime waktuSelesaiJadwal;

    private static final DateTimeFormatter JAM_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public PerangkatElektronik(String kode, String nama, double daya) {
        this.kode = kode;
        this.nama = nama;
        this.daya = daya;
        this.status = false;
        this.waktuMulaiJadwal = null;
        this.waktuSelesaiJadwal = null;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public double getDaya() {
        return daya;
    }

    public void setDaya(double daya) {
        this.daya = daya;
    }

    public boolean isNyala() {
        return status;
    }

    public LocalTime getWaktuMulaiJadwal() {
        return waktuMulaiJadwal;
    }

    public void setWaktuMulaiJadwal(LocalTime waktuMulaiJadwal) {
        this.waktuMulaiJadwal = waktuMulaiJadwal;
    }

    public LocalTime getWaktuSelesaiJadwal() {
        return waktuSelesaiJadwal;
    }

    public void setWaktuSelesaiJadwal(LocalTime waktuSelesaiJadwal) {
        this.waktuSelesaiJadwal = waktuSelesaiJadwal;
    }

    public String getInfoJadwal() {
        if (waktuMulaiJadwal != null && waktuSelesaiJadwal != null) {
            return "Jadwal: " + waktuMulaiJadwal.format(JAM_FORMAT) + " - " + waktuSelesaiJadwal.format(JAM_FORMAT);
        }
        return "";
    }

    public int cekTriggerJadwal(LocalTime waktuSekarang) {
        if (waktuMulaiJadwal != null && !status) {
            if (waktuSekarang.getHour() == waktuMulaiJadwal.getHour() && 
                waktuSekarang.getMinute() == waktuMulaiJadwal.getMinute()) {
                return 1;
            }
        }
        if (waktuSelesaiJadwal != null && status) {
            if (waktuSekarang.getHour() == waktuSelesaiJadwal.getHour() && 
                waktuSekarang.getMinute() == waktuSelesaiJadwal.getMinute()) {
                return 0;
            }
        }
        return -1;
    }

    // Implementasi dari interface KontrolDaya
    @Override
    public void nyalakan() {
        this.status = true;
    }

    @Override
    public void matikan() {
        this.status = false;
    }

    @Override
    public double hitungKonsumsi() {
        return status ? daya : 0.0;
    }

    public abstract String getJenis();
}