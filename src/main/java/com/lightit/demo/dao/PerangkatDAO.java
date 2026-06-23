package com.lightit.demo.dao;

import com.lightit.demo.database.DatabaseConnection;
import com.lightit.demo.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerangkatDAO {

    public PerangkatDAO() {
        System.out.println("--- MEMUAT DATA SISTEM LIGHT-IT ---");
    }

    public List<Ruangan> getAllRuangan() {
        List<Ruangan> daftarRuangan = new ArrayList<>();
        Map<Integer, Ruangan> mapRuangan = new HashMap<>();

        String queryRuangan = "SELECT id, nama_ruangan FROM ruangan";
        String queryPerangkat = "SELECT id, kode, nama, daya, jenis, status, ruangan_id, waktu_mulai_jadwal, waktu_selesai_jadwal FROM perangkat";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // CADANGAN: Jika koneksi ke MySQL gagal, langsung gunakan data lokal agar UI tidak kosong
            if (conn == null) {
                System.err.println("[PEMBERITAHUAN] Koneksi MySQL gagal. Mengaktifkan data cadangan lokal...");
                return getMockData();
            }

            // Membuat tabel & mengisi data otomatis di phpMyAdmin jika terdeteksi masih kosong
            initializeDatabase(conn);

            // 1. Ambil data Ruangan
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(queryRuangan)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nama = rs.getString("nama_ruangan");
                    
                    Ruangan r = new Ruangan(nama);
                    mapRuangan.put(id, r);
                    daftarRuangan.add(r);
                }
            }

            // 2. Ambil data Perangkat
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(queryPerangkat)) {
                while (rs.next()) {
                    String kode = rs.getString("kode");
                    String nama = rs.getString("nama");
                    double daya = rs.getDouble("daya");
                    String jenis = rs.getString("jenis");
                    boolean status = rs.getBoolean("status");
                    int ruanganId = rs.getInt("ruangan_id");
                    Time dbMulai = rs.getTime("waktu_mulai_jadwal");
                    Time dbSelesai = rs.getTime("waktu_selesai_jadwal");

                    PerangkatElektronik p = null;
                    
                    if ("Lampu".equalsIgnoreCase(jenis)) {
                        p = new Lampu(kode, nama, daya);
                    } else if ("Kipas".equalsIgnoreCase(jenis)) {
                        p = new Kipas(kode, nama, daya);
                    } else if ("StopKontak".equalsIgnoreCase(jenis)) {
                        p = new StopKontak(kode, nama, daya);
                    }

                    if (p != null) {
                        if (dbMulai != null) p.setWaktuMulaiJadwal(dbMulai.toLocalTime());
                        if (dbSelesai != null) p.setWaktuSelesaiJadwal(dbSelesai.toLocalTime());

                        if (status) {
                            p.nyalakan();
                        } else {
                            p.matikan();
                        }

                        Ruangan r = mapRuangan.get(ruanganId);
                        if (r != null) {
                            r.tambahPerangkat(p);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("[SQL ERROR] Gagal mengambil data: " + e.getMessage());
            return getMockData();
        }

        // Jika berhasil konek tapi data di databasenya kosong
        if (daftarRuangan.isEmpty()) {
            return getMockData();
        }

        return daftarRuangan;
    }

    private void initializeDatabase(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Buat tabel ruangan jika belum ada
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ruangan (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nama_ruangan VARCHAR(100)" +
                    ")");

            // Buat tabel perangkat jika belum ada
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS perangkat (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "kode VARCHAR(20), " +
                    "nama VARCHAR(100), " +
                    "daya DOUBLE, " +
                    "jenis VARCHAR(20), " +
                    "status BOOLEAN DEFAULT FALSE, " +
                    "ruangan_id INT, " +
                    "waktu_mulai_jadwal TIME, " +
                    "waktu_selesai_jadwal TIME, " +
                    "FOREIGN KEY (ruangan_id) REFERENCES ruangan(id) ON DELETE CASCADE" +
                    ")");

            // Upgrade skema database untuk tabel yang sudah ada
            try {
                stmt.executeUpdate("ALTER TABLE perangkat ADD COLUMN waktu_mulai_jadwal TIME");
                stmt.executeUpdate("ALTER TABLE perangkat ADD COLUMN waktu_selesai_jadwal TIME");
            } catch (SQLException ignored) {
                // Kolom sudah ada
            }

            // Isi data awal ruangan jika masih kosong
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ruangan")) {
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO ruangan (id, nama_ruangan) VALUES " +
                            "(1, 'Ruang Tamu'), " +
                            "(2, 'Teras Depan')");
                }
            }

            // Isi data awal perangkat jika masih kosong
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM perangkat")) {
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO perangkat (kode, nama, daya, jenis, status, ruangan_id) VALUES " +
                            "('RT-L1', 'Lampu Utama', 20.0, 'Lampu', 0, 1), " +
                            "('RT-S1', 'Stop Kontak TV', 100.0, 'StopKontak', 0, 1), " +
                            "('RT-K1', 'Kipas Angin Duduk', 45.0, 'Kipas', 0, 1), " +
                            "('TR-L1', 'Lampu Teras', 15.0, 'Lampu', 0, 2)");
                }
            }
        } catch (SQLException e) {
            System.err.println("[INFO] Sinkronisasi otomatis database dilewati: " + e.getMessage());
        }
    }

    private List<Ruangan> getMockData() {
        List<Ruangan> daftarRuangan = new ArrayList<>();

        Ruangan ruangTamu = new Ruangan("Ruang Tamu");
        ruangTamu.tambahPerangkat(new Lampu("RT-L1", "Lampu Utama", 20.0));
        ruangTamu.tambahPerangkat(new StopKontak("RT-S1", "Stop Kontak TV", 100.0));
        ruangTamu.tambahPerangkat(new Kipas("RT-K1", "Kipas Angin Duduk", 45.0));

        Ruangan teras = new Ruangan("Teras Depan");
        teras.tambahPerangkat(new Lampu("TR-L1", "Lampu Teras", 15.0));

        daftarRuangan.add(ruangTamu);
        daftarRuangan.add(teras);
        return daftarRuangan;
    }

    /**
     * Memperbarui status perangkat (0 atau 1) di database berdasarkan kode perangkat.
     */
    public void updatePerangkatStatus(String kode, boolean status) {
        String query = "UPDATE perangkat SET status = ? WHERE kode = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.err.println("[DB UPDATE WARNING] Gagal koneksi database, perubahan hanya terjadi di UI lokal.");
                return;
            }
            
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Mengubah boolean true/false menjadi 1/0 untuk database MySQL
                pstmt.setInt(1, status ? 1 : 0);
                pstmt.setString(2, kode);
                
                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("[DATABASE UPDATED] Perangkat " + kode + " berhasil diubah di MySQL menjadi: " + (status ? "1" : "0"));
                }
            }
        } catch (SQLException e) {
            System.err.println("[SQL ERROR] Gagal memperbarui status perangkat di database: " + e.getMessage());
        }
    }

    /**
     * Memperbarui nilai daya perangkat di database berdasarkan kode perangkat.
     */
    public void updateDayaPerangkat(String kode, double daya) {
        String query = "UPDATE perangkat SET daya = ? WHERE kode = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                return;
            }
            
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDouble(1, daya);
                pstmt.setString(2, kode);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[SQL ERROR] Gagal memperbarui daya perangkat: " + e.getMessage());
        }
    }

    /**
     * Menyimpan pengaturan jadwal ke database
     */
    public void updateJadwalPerangkat(String kode, java.time.LocalTime mulai, java.time.LocalTime selesai) {
        String query = "UPDATE perangkat SET waktu_mulai_jadwal = ?, waktu_selesai_jadwal = ? WHERE kode = ?";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return;
            
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setTime(1, mulai != null ? Time.valueOf(mulai) : null);
                pstmt.setTime(2, selesai != null ? Time.valueOf(selesai) : null);
                pstmt.setString(3, kode);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[SQL ERROR] Gagal memperbarui jadwal perangkat: " + e.getMessage());
        }
    }
}