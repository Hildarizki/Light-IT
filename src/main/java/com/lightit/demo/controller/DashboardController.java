package com.lightit.demo.controller;

import com.lightit.demo.dao.PerangkatDAO;
import com.lightit.demo.model.PerangkatElektronik;
import com.lightit.demo.model.Ruangan;
import java.util.List;

public class DashboardController {

    private final PerangkatDAO dao = new PerangkatDAO();
    // Cache lokal untuk menyimpan data yang sedang aktif digunakan di UI
    private List<Ruangan> cacheRuangan;

    // Memuat data dari database dan menyimpannya ke cache
    public List<Ruangan> getDataRuangan() {
        if (cacheRuangan == null) {
            cacheRuangan = dao.getAllRuangan();
        }
        return cacheRuangan;
    }

    // Kalkulasi total berdasarkan status objek yang ada di cache (RAM)
    public double kalkulasiTotalDayaRumah() {
        double total = 0;
        for (Ruangan r : getDataRuangan()) {
            for (PerangkatElektronik p : r.getDaftarPerangkat()) {
                total += p.hitungKonsumsi();
            }
        }
        return total;
    }

    public void matikanTotalRumah() {
        List<Ruangan> daftar = getDataRuangan();
        for (Ruangan r : daftar) {
            // 1. Matikan di memori (RAM) agar objek p.isNyala() menjadi false
            r.matikanSemua(); 
            // 2. Sinkronisasi ke Database
            for (PerangkatElektronik p : r.getDaftarPerangkat()) {
                dao.updatePerangkatStatus(p.getKode(), false);
            }
        }
        System.out.println("[DATABASE] Seluruh perangkat dimatikan.");
    }

    public void nyalakanTotalRumah() {
        List<Ruangan> daftar = getDataRuangan();
        for (Ruangan r : daftar) {
            // 1. Nyalakan di memori (RAM) agar objek p.isNyala() menjadi true
            r.nyalakanSemua(); 
            // 2. Sinkronisasi ke Database
            for (PerangkatElektronik p : r.getDaftarPerangkat()) {
                dao.updatePerangkatStatus(p.getKode(), true);
            }
        }
        System.out.println("[DATABASE] Seluruh perangkat dinyalakan.");
    }
    
    /**
     * Memperbarui status sakelar perangkat tunggal di database
     */
    public void updateStatusPerangkatTunggal(String kode, boolean status) {
        dao.updatePerangkatStatus(kode, status);
    }

    /**
     * Memperbarui nilai daya perangkat di database (dari slider)
     */
    public void updateDayaPerangkat(String kode, double daya) {
        dao.updateDayaPerangkat(kode, daya);
    }

    /**
     * Memperbarui pengaturan jadwal otomatis di database
     */
    public void updateJadwalPerangkat(String kode, java.time.LocalTime mulai, java.time.LocalTime selesai) {
        dao.updateJadwalPerangkat(kode, mulai, selesai);
    }
}