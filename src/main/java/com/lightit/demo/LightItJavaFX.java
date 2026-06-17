package com.lightit.demo;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LightItJavaFX extends Application {

    private final List<Ruangan> daftarRuangan = new ArrayList<>();
    private Label lblTotalDaya;
    private VBox containerRuangan;

    // Konstanta Desain (iOS Light Mode - Frosted Glass)
    private final String styleMati = "-fx-background-color: rgba(255, 255, 255, 0.4); -fx-border-color: rgba(255, 255, 255, 0.7); -fx-border-radius: 20; -fx-background-radius: 20;";
    private final String styleNyala = "-fx-background-color: rgba(52, 199, 89, 0.2); -fx-border-color: rgba(52, 199, 89, 0.5); -fx-border-radius: 20; -fx-background-radius: 20;";
    private final String btnOnStyle = "-fx-background-color: #34C759; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 5 15; -fx-effect: dropshadow(three-pass-box, rgba(52, 199, 89, 0.4), 5, 0, 0, 2);";
    private final String btnOffStyle = "-fx-background-color: rgba(0, 0, 0, 0.08); -fx-text-fill: #475569; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 5 15;";

    @Override
    public void init() {
        System.out.println("--- MEMUAT DATA SISTEM LIGHT-IT ---");

        Ruangan ruangTamu = new Ruangan("Ruang Tamu");
        ruangTamu.tambahPerangkat(new Lampu("RT-L1", "Lampu Utama", 20.0));
        ruangTamu.tambahPerangkat(new StopKontak("RT-S1", "Stop Kontak TV", 100.0));
        ruangTamu.tambahPerangkat(new Kipas("RT-K1", "Kipas Angin Duduk", 45.0));

        Ruangan teras = new Ruangan("Teras Depan");
        teras.tambahPerangkat(new Lampu("TR-L1", "Lampu Teras", 15.0));
        teras.tambahPerangkat(new StopKontak("TR-S1", "Stop Kontak Luar", 50.0));

        daftarRuangan.add(ruangTamu);
        daftarRuangan.add(teras);
    }

    @Override
    public void start(Stage primaryStage) {
        // 1. Setup Root Background (Gradasi Biru Muda ke Hijau Mint)
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        // linear-gradient dari Biru Langit (#8fd3f4) ke Hijau Mint/Muda (#84fab0)
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #8fd3f4, #84fab0);");

        // 2. Header Aplikasi (Teks Gelap agar terbaca di background terang)
        VBox header = new VBox(5);
        header.setAlignment(Pos.CENTER);

        Label title = new Label("Light-IT Center");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setStyle("-fx-text-fill: #0f172a; -fx-letter-spacing: 0.5;");

        lblTotalDaya = new Label("Beban Aktif: 0.0 W");
        lblTotalDaya.setFont(Font.font("System", FontWeight.BOLD, 14));
        lblTotalDaya.setStyle("-fx-text-fill: #047857; -fx-padding: 6 16; -fx-background-color: rgba(255, 255, 255, 0.6); -fx-background-radius: 20; -fx-border-color: rgba(255, 255, 255, 0.8); -fx-border-radius: 20;");

        header.getChildren().addAll(title, lblTotalDaya);
        root.getChildren().add(header);

        // 3. Tombol Master Kontrol Total (HBox untuk 2 Tombol Sejajar)
        HBox masterControls = new HBox(10);
        masterControls.setAlignment(Pos.CENTER);

        // Tombol Master: Nyalakan Semua
        Button btnNyalakanSemua = new Button("Nyalakan Semua");
        btnNyalakanSemua.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-text-fill: #059669; -fx-font-weight: bold; -fx-border-color: rgba(255, 255, 255, 0.8); -fx-border-radius: 20; -fx-background-radius: 20; -fx-font-size: 13px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");
        btnNyalakanSemua.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnNyalakanSemua, Priority.ALWAYS);
        btnNyalakanSemua.setPadding(new Insets(12));
        btnNyalakanSemua.setOnAction(e -> {
            for (Ruangan r : daftarRuangan) {
                r.nyalakanSemua();
            }
            refreshVisualState();
        });

        // Tombol Master: Matikan Semua
        Button btnMatikanSemua = new Button("Matikan Semua");
        btnMatikanSemua.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-text-fill: #e11d48; -fx-font-weight: bold; -fx-border-color: rgba(255, 255, 255, 0.8); -fx-border-radius: 20; -fx-background-radius: 20; -fx-font-size: 13px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 0, 2);");
        btnMatikanSemua.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnMatikanSemua, Priority.ALWAYS);
        btnMatikanSemua.setPadding(new Insets(12));
        btnMatikanSemua.setOnAction(e -> {
            for (Ruangan r : daftarRuangan) {
                r.matikanSemua();
            }
            refreshVisualState();
        });

        masterControls.getChildren().addAll(btnNyalakanSemua, btnMatikanSemua);
        root.getChildren().add(masterControls);

        // 4. ScrollPane Transparan
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-padding: 0;");

        containerRuangan = new VBox(25);
        containerRuangan.setStyle("-fx-background-color: transparent;");

        for (Ruangan r : daftarRuangan) {
            // Box Ruangan (Kaca Putih Frosted)
            VBox boxRuangan = new VBox(15);
            boxRuangan.setPadding(new Insets(20));
            boxRuangan.setStyle("-fx-background-color: rgba(255, 255, 255, 0.25); -fx-border-color: rgba(255, 255, 255, 0.5); -fx-border-radius: 28; -fx-background-radius: 28; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");

            // Header Grup Ruangan
            HBox headerRuangan = new HBox(10);
            headerRuangan.setAlignment(Pos.CENTER_LEFT);

            Label namaRuangan = new Label(r.getNamaRuangan());
            namaRuangan.setFont(Font.font("System", FontWeight.BOLD, 18));
            namaRuangan.setStyle("-fx-text-fill: #1e293b;"); // Teks Navy Gelap

            Region spacerGrup = new Region();
            HBox.setHgrow(spacerGrup, Priority.ALWAYS);

            // Tombol Grup
            Button btnNyalaGrup = new Button("Nyalakan");
            btnNyalaGrup.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6); -fx-text-fill: #059669; -fx-background-radius: 12; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 6 12;");
            btnNyalaGrup.setOnAction(e -> {
                r.nyalakanSemua();
                refreshVisualState();
            });

            Button btnMatiGrup = new Button("Matikan");
            btnMatiGrup.setStyle("-fx-background-color: rgba(255, 255, 255, 0.6); -fx-text-fill: #e11d48; -fx-background-radius: 12; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 6 12;");
            btnMatiGrup.setOnAction(e -> {
                r.matikanSemua();
                refreshVisualState();
            });

            headerRuangan.getChildren().addAll(namaRuangan, spacerGrup, btnNyalaGrup, btnMatiGrup);
            boxRuangan.getChildren().add(headerRuangan);

            // Daftar Perangkat
            for (PerangkatElektronik p : r.getDaftarPerangkat()) {
                HBox cardPerangkat = buatCardPerangkat(p);
                boxRuangan.getChildren().add(cardPerangkat);
            }
            containerRuangan.getChildren().add(boxRuangan);
        }

        scrollPane.setContent(containerRuangan);
        root.getChildren().add(scrollPane);

        Scene scene = new Scene(root, 450, 750);
        primaryStage.setTitle("Light-IT iOS Day Mode");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        kalkulasiTotalDaya();
    }

    private HBox buatCardPerangkat(PerangkatElektronik p) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15));
        card.setStyle(p.isStatus() ? styleNyala : styleMati);

        VBox info = new VBox(5);
        Label nama = new Label(p.getNama());
        nama.setFont(Font.font("System", FontWeight.BOLD, 15));
        nama.setStyle("-fx-text-fill: #0f172a;"); // Teks Navy

        Label tipe = new Label(p.getTipe() + " • " + p.dayaDasar + "W");
        tipe.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;"); // Teks Abu-abu
        info.getChildren().addAll(nama, tipe);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Toggle Button
        ToggleButton btnToggle = new ToggleButton(p.isStatus() ? "ON" : "OFF");
        btnToggle.setStyle(p.isStatus() ? btnOnStyle : btnOffStyle);

        btnToggle.setOnAction(e -> {
            if (btnToggle.isSelected()) {
                p.nyalakan();
                btnToggle.setText("ON");
                btnToggle.setStyle(btnOnStyle);
                card.setStyle(styleNyala);
            } else {
                p.matikan();
                btnToggle.setText("OFF");
                btnToggle.setStyle(btnOffStyle);
                card.setStyle(styleMati);
            }
            kalkulasiTotalDaya();
        });
        btnToggle.setSelected(p.isStatus());

        VBox controlArea = new VBox(8);
        controlArea.setAlignment(Pos.CENTER_RIGHT);
        controlArea.getChildren().add(btnToggle);

        if (p instanceof Lampu) {
            Lampu lampu = (Lampu) p;
            Slider slider = new Slider(0, 100, lampu.getTingkatKecerahan());
            slider.setPrefWidth(100);
            slider.setStyle("-fx-control-inner-background: rgba(0, 0, 0, 0.1);");
            slider.valueProperty().addListener((obs, oldVal, newVal) -> {
                lampu.setKecerahan(newVal.intValue());
                kalkulasiTotalDaya();
            });
            controlArea.getChildren().add(slider);
        } else if (p instanceof Kipas) {
            Kipas kipas = (Kipas) p;
            Slider slider = new Slider(1, 3, kipas.getKecepatan());
            slider.setMajorTickUnit(1);
            slider.setMinorTickCount(0);
            slider.setSnapToTicks(true);
            slider.setPrefWidth(100);
            slider.setStyle("-fx-control-inner-background: rgba(0, 0, 0, 0.1);");
            slider.valueProperty().addListener((obs, oldVal, newVal) -> {
                kipas.setKecepatan(newVal.intValue());
                kalkulasiTotalDaya();
            });
            controlArea.getChildren().add(slider);
        }

        card.getChildren().addAll(info, spacer, controlArea);
        card.setUserData(p);
        return card;
    }

    private void refreshVisualState() {
        kalkulasiTotalDaya();

        for (Node boxNode : containerRuangan.getChildren()) {
            if (boxNode instanceof VBox) {
                VBox boxRuangan = (VBox) boxNode;
                for (Node cardNode : boxRuangan.getChildren()) {
                    if (cardNode instanceof HBox && cardNode.getUserData() != null) {
                        PerangkatElektronik p = (PerangkatElektronik) cardNode.getUserData();

                        cardNode.setStyle(p.isStatus() ? styleNyala : styleMati);

                        VBox controlArea = (VBox) ((HBox) cardNode).getChildren().get(2);
                        ToggleButton btnToggle = (ToggleButton) controlArea.getChildren().get(0);

                        btnToggle.setSelected(p.isStatus());
                        btnToggle.setText(p.isStatus() ? "ON" : "OFF");
                        btnToggle.setStyle(p.isStatus() ? btnOnStyle : btnOffStyle);
                    }
                }
            }
        }
    }

    private void kalkulasiTotalDaya() {
        double total = 0;
        for (Ruangan r : daftarRuangan) {
            for (PerangkatElektronik p : r.getDaftarPerangkat()) {
                total += p.hitungKonsumsi();
            }
        }
        lblTotalDaya.setText(String.format("Beban Aktif: %.1f W", total));
    }

    public static void main(String[] args) {
        launch(args);
    }

    // =========================================================
    // ARSITEKTUR OOP FUNDAMENTAL
    // =========================================================
    public interface KontrolDaya {

        void nyalakan();

        void matikan();

        double hitungKonsumsi();
    }

    public abstract static class PerangkatElektronik implements KontrolDaya {

        protected String idPerangkat;
        protected String nama;
        protected boolean status;
        protected double dayaDasar;
        protected String tipe;

        public PerangkatElektronik(String idPerangkat, String nama, double dayaDasar, String tipe) {
            this.idPerangkat = idPerangkat;
            this.nama = nama;
            this.dayaDasar = dayaDasar;
            this.status = false;
            this.tipe = tipe;
        }

        public String getNama() {
            return nama;
        }

        public boolean isStatus() {
            return status;
        }

        public String getTipe() {
            return tipe;
        }

        @Override
        public void nyalakan() {
            this.status = true;
        }

        @Override
        public void matikan() {
            this.status = false;
        }
    }

    public static class Lampu extends PerangkatElektronik {

        private int tingkatKecerahan = 100;

        public Lampu(String id, String nama, double dayaDasar) {
            super(id, nama, dayaDasar, "Lampu");
        }

        public int getTingkatKecerahan() {
            return tingkatKecerahan;
        }

        public void setKecerahan(int level) {
            if (status) {
                this.tingkatKecerahan = level;
        
            }}

        @Override
        public double hitungKonsumsi() {
            return status ? dayaDasar * (tingkatKecerahan / 100.0) : 0;
        }
    }

    public static class Kipas extends PerangkatElektronik {

        private int kecepatan = 1;

        public Kipas(String id, String nama, double dayaDasar) {
            super(id, nama, dayaDasar, "Kipas");
        }

        public int getKecepatan() {
            return kecepatan;
        }

        public void setKecepatan(int speed) {
            if (status) {
                this.kecepatan = speed;
        
            }}

        @Override
        public double hitungKonsumsi() {
            return status ? dayaDasar * kecepatan : 0;
        }
    }

    public static class StopKontak extends PerangkatElektronik {

        public StopKontak(String id, String nama, double dayaDasar) {
            super(id, nama, dayaDasar, "StopKontak");
        }

        @Override
        public double hitungKonsumsi() {
            return status ? dayaDasar : 0;
        }
    }

    public static class Ruangan {

        private String namaRuangan;
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

        public void matikanSemua() {
            for (PerangkatElektronik p : daftarPerangkat) {
                if (p.isStatus()) {
                    p.matikan();
            
                }}
        }

        public void nyalakanSemua() {
            for (PerangkatElektronik p : daftarPerangkat) {
                if (!p.isStatus()) {
                    p.nyalakan();
            
                }}
        }
    }
}
