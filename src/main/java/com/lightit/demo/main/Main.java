package com.lightit.demo.main;

import com.lightit.demo.controller.DashboardController;
import com.lightit.demo.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.util.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    private final DashboardController controller = new DashboardController();
    private Label lblTotalDaya;
    private VBox containerRuangan;

    // Liquid Glass Kit Stylesheet
    private final String styleMati = "-fx-background-color: linear-gradient(to bottom right, rgba(255,255,255,0.7), rgba(255,255,255,0.3)); -fx-border-color: rgba(255,255,255,0.9); -fx-border-width: 1.5; -fx-border-radius: 20; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 15, 0, 5, 5);";
    private final String styleNyala = "-fx-background-color: linear-gradient(to bottom right, rgba(142,182,155,0.6), rgba(218,241,222,0.4)); -fx-border-color: rgba(255,255,255,0.9); -fx-border-width: 1.5; -fx-border-radius: 20; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 15, 0, 5, 5);";
    private final String btnOnStyle = "-fx-background-color: linear-gradient(to right, #163832, #235347); -fx-text-fill: #DAF1DE; -fx-font-weight: bold; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-color: rgba(255,255,255,0.5); -fx-border-width: 1; -fx-padding: 6 18; -fx-effect: dropshadow(three-pass-box, rgba(22, 56, 50, 0.4), 10, 0, 0, 5); -fx-cursor: hand;";
    private final String btnOffStyle = "-fx-background-color: rgba(255,255,255,0.5); -fx-text-fill: #051F20; -fx-font-weight: bold; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-color: rgba(255,255,255,0.8); -fx-border-width: 1; -fx-padding: 6 18; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 2, 2); -fx-cursor: hand;";

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #DAF1DE, #8EB69B);");

        // ==========================================
        // 1. HEADER DASHBOARD
        // ==========================================
        VBox header = new VBox(5);
        header.setAlignment(Pos.CENTER);

        Label title = new Label("Light-IT Center");
        title.setFont(Font.font("System", FontWeight.BOLD, 26));
        title.setStyle("-fx-text-fill: #051F20;");

        lblTotalDaya = new Label("Beban Aktif: 0.0 W");
        lblTotalDaya.setFont(Font.font("System", FontWeight.BOLD, 14));
        lblTotalDaya.setStyle("-fx-text-fill: #051F20; -fx-padding: 8 20; -fx-background-color: linear-gradient(to right, rgba(255,255,255,0.8), rgba(255,255,255,0.4)); -fx-background-radius: 30; -fx-border-color: rgba(255,255,255,0.9); -fx-border-radius: 30; -fx-border-width: 1.5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 4);");

        header.getChildren().addAll(title, lblTotalDaya);
        root.getChildren().add(header);

        // ==========================================
        // 2. MASTER GLOBAL CONTROLS
        // ==========================================
        HBox masterControls = new HBox(10);
        masterControls.setAlignment(Pos.CENTER);

        Button btnNyalakanSemua = new Button("⚡ Nyalakan Semua");
        btnNyalakanSemua.setStyle("-fx-background-color: linear-gradient(to right, rgba(255,255,255,0.6), rgba(255,255,255,0.3)); -fx-text-fill: #163832; -fx-font-weight: bold; -fx-border-color: rgba(255, 255, 255, 0.9); -fx-border-width: 1.5; -fx-border-radius: 30; -fx-background-radius: 30; -fx-font-size: 13px; -fx-padding: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5); -fx-cursor: hand;");
        btnNyalakanSemua.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnNyalakanSemua, Priority.ALWAYS);
        btnNyalakanSemua.setOnAction(e -> {
            playClickBounce(btnNyalakanSemua);
            controller.nyalakanTotalRumah();
            refreshVisualState();
        });
        addHoverEffect(btnNyalakanSemua);

        Button btnMatikanSemua = new Button("🔴 Matikan Semua");
        btnMatikanSemua.setStyle("-fx-background-color: linear-gradient(to right, rgba(255,255,255,0.6), rgba(255,255,255,0.3)); -fx-text-fill: #051F20; -fx-font-weight: bold; -fx-border-color: rgba(255, 255, 255, 0.9); -fx-border-width: 1.5; -fx-border-radius: 30; -fx-background-radius: 30; -fx-font-size: 13px; -fx-padding: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5); -fx-cursor: hand;");
        btnMatikanSemua.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnMatikanSemua, Priority.ALWAYS);
        btnMatikanSemua.setOnAction(e -> {
            // Konfirmasi sebelum matikan semua
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi");
            alert.setHeaderText("Matikan Semua Perangkat?");
            alert.setContentText("Seluruh perangkat di semua ruangan akan dimatikan.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    playClickBounce(btnMatikanSemua);
                    controller.matikanTotalRumah();
                    refreshVisualState();
                }
            });
        });
        addHoverEffect(btnMatikanSemua);

        masterControls.getChildren().addAll(btnNyalakanSemua, btnMatikanSemua);
        root.getChildren().add(masterControls);

        // ==========================================
        // 3. SCROLLABLE CONTAINER DAFTAR RUANGAN
        // ==========================================
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-control-inner-background: transparent; -fx-padding: 0;");

        containerRuangan = new VBox(25);
        containerRuangan.setStyle("-fx-background-color: transparent;");

        for (Ruangan r : controller.getDataRuangan()) {
            VBox boxRuangan = new VBox(15);
            boxRuangan.setPadding(new Insets(20));
            boxRuangan.setStyle("-fx-background-color: linear-gradient(to bottom right, rgba(255,255,255,0.4), rgba(255,255,255,0.1)); -fx-border-color: rgba(255,255,255,0.7); -fx-border-width: 1.5; -fx-border-radius: 30; -fx-background-radius: 30; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 20, 0, 10, 10);");

            HBox headerRuangan = new HBox(10);
            headerRuangan.setAlignment(Pos.CENTER_LEFT);

            Label namaRuangan = new Label(r.getNamaRuangan().toUpperCase());
            namaRuangan.setFont(Font.font("System", FontWeight.BOLD, 16));
            namaRuangan.setStyle("-fx-text-fill: #051F20;");

            // Counter perangkat aktif
            Label lblCounter = new Label(r.jumlahPerangkatAktif() + "/" + r.getDaftarPerangkat().size() + " Aktif");
            lblCounter.setFont(Font.font("System", FontWeight.BOLD, 11));
            lblCounter.setStyle("-fx-text-fill: #235347; -fx-padding: 3 10; -fx-background-color: rgba(218,241,222,0.7); -fx-background-radius: 12; -fx-border-color: rgba(255,255,255,0.8); -fx-border-radius: 12; -fx-border-width: 1;");
            lblCounter.setUserData(r); // Simpan referensi ruangan untuk update

            Region spacerGrup = new Region();
            HBox.setHgrow(spacerGrup, Priority.ALWAYS);

            Button btnNyalaGrup = new Button("Nyalakan");
            btnNyalaGrup.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-text-fill: #163832; -fx-border-color: rgba(255,255,255,0.9); -fx-border-radius: 20; -fx-background-radius: 20; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 6 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 2, 2); -fx-cursor: hand;");
            btnNyalaGrup.setOnAction(e -> {
                playClickBounce(btnNyalaGrup);
                r.nyalakanSemua();
                for (PerangkatElektronik p : r.getDaftarPerangkat()) {
                    controller.updateStatusPerangkatTunggal(p.getKode(), true);
                }
                refreshVisualState();
            });
            addHoverEffect(btnNyalaGrup);

            Button btnMatiGrup = new Button("Matikan");
            btnMatiGrup.setStyle("-fx-background-color: rgba(255,255,255,0.6); -fx-text-fill: #051F20; -fx-border-color: rgba(255,255,255,0.9); -fx-border-radius: 20; -fx-background-radius: 20; -fx-font-size: 11px; -fx-font-weight: bold; -fx-padding: 6 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 5, 0, 2, 2); -fx-cursor: hand;");
            btnMatiGrup.setOnAction(e -> {
                playClickBounce(btnMatiGrup);
                r.matikanSemua();
                for (PerangkatElektronik p : r.getDaftarPerangkat()) {
                    controller.updateStatusPerangkatTunggal(p.getKode(), false);
                }
                refreshVisualState();
            });
            addHoverEffect(btnMatiGrup);

            headerRuangan.getChildren().addAll(namaRuangan, lblCounter, spacerGrup, btnNyalaGrup, btnMatiGrup);
            boxRuangan.getChildren().add(headerRuangan);

            for (PerangkatElektronik p : r.getDaftarPerangkat()) {
                HBox cardPerangkat = buatCardPerangkat(p);
                boxRuangan.getChildren().add(cardPerangkat);
            }
            containerRuangan.getChildren().add(boxRuangan);
        }

        scrollPane.setContent(containerRuangan);
        root.getChildren().add(scrollPane);

        Scene scene = new Scene(root, 480, 750);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setTitle("Light-IT System Control Center");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        updateLabelDaya();
        playStartupAnimation(root);
        startBackgroundJadwalChecker();
    }

    /**
     * Membuat Card Perangkat Individu
     */
    private HBox buatCardPerangkat(PerangkatElektronik p) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15));
        card.setStyle(p.isNyala() ? styleNyala : styleMati);

        // Ikon emoji berdasarkan jenis perangkat
        String emoji = getEmojiForJenis(p.getJenis());
        Label lblEmoji = new Label(emoji);
        lblEmoji.setFont(Font.font(22));

        VBox info = new VBox(3);
        Label nama = new Label(p.getNama());
        nama.setFont(Font.font("System", FontWeight.BOLD, 15));
        nama.setStyle("-fx-text-fill: #051F20;");

        Label tipe = new Label(p.getJenis());
        tipe.setStyle("-fx-text-fill: #163832; -fx-font-size: 12px;");
        
        Label infoDaya = new Label(String.format("Daya: %.1f W", p.hitungKonsumsi()));
        infoDaya.setStyle("-fx-text-fill: #163832; -fx-font-size: 12px; -fx-font-weight: bold;");
        info.getChildren().addAll(nama, tipe, infoDaya);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ToggleButton btnToggle = new ToggleButton(p.isNyala() ? "ON" : "OFF");
        btnToggle.setStyle(p.isNyala() ? btnOnStyle : btnOffStyle);
        btnToggle.setSelected(p.isNyala());
        addHoverEffect(btnToggle);

        btnToggle.setOnAction(e -> {
            boolean statusBaru = btnToggle.isSelected();
            if (statusBaru) {
                p.nyalakan(); 
            } else {
                p.matikan();
            }
            playClickBounce(btnToggle);
            controller.updateStatusPerangkatTunggal(p.getKode(), statusBaru);
            refreshVisualState();
        });

        Button btnJadwal = new Button("🕐");
        btnJadwal.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-text-fill: #163832; -fx-font-size: 14px; -fx-border-radius: 15; -fx-background-radius: 15; -fx-cursor: hand; -fx-padding: 2 6;");
        if (!p.getInfoJadwal().isEmpty()) {
            btnJadwal.setTooltip(new Tooltip(p.getInfoJadwal()));
            btnJadwal.setStyle("-fx-background-color: #8EB69B; -fx-text-fill: #051F20; -fx-font-size: 14px; -fx-border-radius: 15; -fx-background-radius: 15; -fx-cursor: hand; -fx-padding: 2 6;");
        }
        btnJadwal.setOnAction(e -> {
            playClickBounce(btnJadwal);
            tampilkanDialogJadwal(p, card);
        });
        addHoverEffect(btnJadwal);

        HBox toggleBox = new HBox(8);
        toggleBox.setAlignment(Pos.CENTER_RIGHT);
        toggleBox.getChildren().addAll(btnJadwal, btnToggle);

        VBox controlArea = new VBox(8);
        controlArea.setAlignment(Pos.CENTER_RIGHT);
        controlArea.getChildren().add(toggleBox);

        if (p instanceof Kipas || p instanceof Lampu) {
            Slider dayaSlider = new Slider(10, 100, p.getDaya());
            dayaSlider.setPrefWidth(90);
            dayaSlider.getStyleClass().add("glass-slider");
            dayaSlider.setDisable(!p.isNyala());

            dayaSlider.setOnMousePressed(ev -> {
                ScaleTransition grow = new ScaleTransition(Duration.millis(150), dayaSlider);
                grow.setToX(1.08);
                grow.setToY(1.08);
                grow.play();
            });
            dayaSlider.setOnMouseReleased(ev -> {
                ScaleTransition shrink = new ScaleTransition(Duration.millis(200), dayaSlider);
                shrink.setToX(1.0);
                shrink.setToY(1.0);
                shrink.play();
                // Simpan perubahan daya ke database saat slider dilepas
                controller.updateDayaPerangkat(p.getKode(), p.getDaya());
            });
            
            dayaSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                p.setDaya(newVal.doubleValue());
                updateLabelDaya();
                infoDaya.setText(String.format("Daya: %.1f W", p.hitungKonsumsi()));
            });

            controlArea.getChildren().add(dayaSlider);
        }

        card.getChildren().addAll(lblEmoji, info, spacer, controlArea);
        card.setUserData(p);
        addHoverEffect(card);
        return card;
    }

    /**
     * Mengembalikan emoji berdasarkan jenis perangkat
     */
    private String getEmojiForJenis(String jenis) {
        switch (jenis) {
            case "Lampu":      return "💡";
            case "Kipas":      return "🌀";
            case "StopKontak": return "🔌";
            default:           return "📦";
        }
    }



    /**
     * Memperbarui visual seluruh komponen UI
     */
    private void refreshVisualState() {
        updateLabelDaya();
        playPulse(lblTotalDaya);

        for (Node boxNode : containerRuangan.getChildren()) {
            if (boxNode instanceof VBox) {
                VBox boxRuangan = (VBox) boxNode;

                // Update counter di header ruangan
                HBox headerRuangan = (HBox) boxRuangan.getChildren().get(0);
                for (Node headerChild : headerRuangan.getChildren()) {
                    if (headerChild instanceof Label && headerChild.getUserData() instanceof Ruangan) {
                        Ruangan r = (Ruangan) headerChild.getUserData();
                        Label lblCounter = (Label) headerChild;
                        lblCounter.setText(r.jumlahPerangkatAktif() + "/" + r.getDaftarPerangkat().size() + " Aktif");
                        playPulse(lblCounter);
                    }
                }

                for (Node cardNode : boxRuangan.getChildren()) {
                    if (cardNode instanceof HBox && cardNode.getUserData() != null) {
                        PerangkatElektronik p = (PerangkatElektronik) cardNode.getUserData();

                        cardNode.setStyle(p.isNyala() ? styleNyala : styleMati);
                        playCardFlash(cardNode);

                        // Update label daya perangkat
                        VBox infoBox = (VBox) ((HBox) cardNode).getChildren().get(1);
                        if (infoBox.getChildren().size() > 2) {
                            Label infoDaya = (Label) infoBox.getChildren().get(2);
                            infoDaya.setText(String.format("Daya: %.1f W", p.hitungKonsumsi()));
                        }

                        // Ambil VBox controlArea yang berada di index ke-3
                        VBox controlArea = (VBox) ((HBox) cardNode).getChildren().get(3);
                        HBox toggleBox = (HBox) controlArea.getChildren().get(0);
                        ToggleButton btnToggle = (ToggleButton) toggleBox.getChildren().get(1);

                        btnToggle.setSelected(p.isNyala());
                        btnToggle.setText(p.isNyala() ? "ON" : "OFF");
                        btnToggle.setStyle(p.isNyala() ? btnOnStyle : btnOffStyle);

                        Button btnJadwal = (Button) toggleBox.getChildren().get(0);
                        if (!p.getInfoJadwal().isEmpty()) {
                            btnJadwal.setTooltip(new Tooltip(p.getInfoJadwal()));
                            btnJadwal.setStyle("-fx-background-color: #8EB69B; -fx-text-fill: #051F20; -fx-font-size: 14px; -fx-border-radius: 15; -fx-background-radius: 15; -fx-cursor: hand; -fx-padding: 2 6;");
                        } else {
                            btnJadwal.setTooltip(null);
                            btnJadwal.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-text-fill: #163832; -fx-font-size: 14px; -fx-border-radius: 15; -fx-background-radius: 15; -fx-cursor: hand; -fx-padding: 2 6;");
                        }

                        if (controlArea.getChildren().size() > 1) {
                            Node sliderNode = controlArea.getChildren().get(1);
                            if (sliderNode instanceof Slider) {
                                sliderNode.setDisable(!p.isNyala());
                            }
                        }
                    }
                }
            }
        }
    }



    // ==========================================
    // LOGIKA BACKGROUND & JADWAL
    // ==========================================
    private void startBackgroundJadwalChecker() {
        Timeline checker = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            java.time.LocalTime waktuSekarang = java.time.LocalTime.now();
            boolean butuhRefresh = false;

            for (Node boxNode : containerRuangan.getChildren()) {
                if (boxNode instanceof VBox) {
                    VBox boxRuangan = (VBox) boxNode;
                    for (Node cardNode : boxRuangan.getChildren()) {
                        if (cardNode instanceof HBox && cardNode.getUserData() != null) {
                            PerangkatElektronik p = (PerangkatElektronik) cardNode.getUserData();
                            int trigger = p.cekTriggerJadwal(waktuSekarang);
                            if (trigger == 1) {
                                p.nyalakan();
                                controller.updateStatusPerangkatTunggal(p.getKode(), true);
                                butuhRefresh = true;
                            } else if (trigger == 0) {
                                p.matikan();
                                controller.updateStatusPerangkatTunggal(p.getKode(), false);
                                butuhRefresh = true;
                            }
                        }
                    }
                }
            }
            if (butuhRefresh) {
                refreshVisualState();
            }
        }));
        checker.setCycleCount(Animation.INDEFINITE);
        checker.play();
    }

    private void tampilkanDialogJadwal(PerangkatElektronik p, Node cardNode) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Set Jadwal");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #DAF1DE, #8EB69B); -fx-border-color: rgba(255,255,255,0.6); -fx-border-width: 2;");
        
        Label titleLabel = new Label("Atur Jadwal " + p.getNama());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        titleLabel.setStyle("-fx-text-fill: #051F20;");

        Label descLabel = new Label("Masukkan waktu untuk otomatisasi.\nKosongkan untuk menonaktifkan jadwal.");
        descLabel.setStyle("-fx-text-fill: #163832; -fx-font-size: 12px;");

        ButtonType btnSimpanType = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(btnSimpanType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(12);
        grid.setPadding(new Insets(20, 30, 10, 10));

        TextField txtMulai = new TextField();
        txtMulai.setPromptText("18:00");
        txtMulai.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: rgba(255,255,255,0.9); -fx-padding: 5 10;");
        if (p.getWaktuMulaiJadwal() != null) txtMulai.setText(p.getWaktuMulaiJadwal().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));

        TextField txtSelesai = new TextField();
        txtSelesai.setPromptText("06:00");
        txtSelesai.setStyle("-fx-background-color: rgba(255,255,255,0.7); -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: rgba(255,255,255,0.9); -fx-padding: 5 10;");
        if (p.getWaktuSelesaiJadwal() != null) txtSelesai.setText(p.getWaktuSelesaiJadwal().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));

        Label lblMulai = new Label("Jam Nyala:");
        lblMulai.setStyle("-fx-text-fill: #051F20; -fx-font-weight: bold;");
        Label lblSelesai = new Label("Jam Mati:");
        lblSelesai.setStyle("-fx-text-fill: #051F20; -fx-font-weight: bold;");

        grid.add(lblMulai, 0, 0);
        grid.add(txtMulai, 1, 0);
        grid.add(lblSelesai, 0, 1);
        grid.add(txtSelesai, 1, 1);

        VBox content = new VBox(10);
        content.getChildren().addAll(titleLabel, descLabel, grid);
        dialogPane.setContent(content);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnSimpanType) {
                try {
                    String strMulai = txtMulai.getText().trim();
                    String strSelesai = txtSelesai.getText().trim();
                    
                    java.time.LocalTime mulai = strMulai.isEmpty() ? null : java.time.LocalTime.parse(strMulai, java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
                    java.time.LocalTime selesai = strSelesai.isEmpty() ? null : java.time.LocalTime.parse(strSelesai, java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
                    
                    p.setWaktuMulaiJadwal(mulai);
                    p.setWaktuSelesaiJadwal(selesai);
                    controller.updateJadwalPerangkat(p.getKode(), mulai, selesai);
                    refreshVisualState();
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Format jam salah! Gunakan format HH:mm (contoh: 18:00)");
                    alert.showAndWait();
                }
            }
            return dialogButton;
        });

        dialog.showAndWait();
    }

    // ==========================================
    // ANIMASI: Hover Scale Effect
    // ==========================================
    private void addHoverEffect(Node node) {
        ScaleTransition stIn = new ScaleTransition(Duration.millis(150), node);
        stIn.setToX(1.03);
        stIn.setToY(1.03);
        
        ScaleTransition stOut = new ScaleTransition(Duration.millis(150), node);
        stOut.setToX(1.0);
        stOut.setToY(1.0);

        node.setOnMouseEntered(e -> {
            stOut.stop();
            stIn.playFromStart();
        });
        node.setOnMouseExited(e -> {
            stIn.stop();
            stOut.playFromStart();
        });
    }

    // ==========================================
    // ANIMASI: Klik Bounce
    // ==========================================
    private void playClickBounce(Node node) {
        ScaleTransition shrink = new ScaleTransition(Duration.millis(80), node);
        shrink.setToX(0.90);
        shrink.setToY(0.90);

        ScaleTransition grow = new ScaleTransition(Duration.millis(120), node);
        grow.setToX(1.05);
        grow.setToY(1.05);

        ScaleTransition settle = new ScaleTransition(Duration.millis(100), node);
        settle.setToX(1.0);
        settle.setToY(1.0);

        SequentialTransition bounce = new SequentialTransition(shrink, grow, settle);
        bounce.play();
    }

    // ==========================================
    // ANIMASI: Pulse
    // ==========================================
    private void playPulse(Node node) {
        ScaleTransition pulse = new ScaleTransition(Duration.millis(150), node);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.08);
        pulse.setToY(1.08);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(2);
        pulse.play();
    }

    // ==========================================
    // ANIMASI: Card Flash
    // ==========================================
    private void playCardFlash(Node node) {
        FadeTransition flash = new FadeTransition(Duration.millis(120), node);
        flash.setFromValue(0.5);
        flash.setToValue(1.0);
        flash.play();
    }

    // ==========================================
    // ANIMASI: Startup fade-in + slide bertahap
    // ==========================================
    private void playStartupAnimation(VBox root) {
        int delayMs = 0;
        for (Node child : root.getChildren()) {
            child.setOpacity(0);
            child.setTranslateY(30);

            FadeTransition fade = new FadeTransition(Duration.millis(400), child);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setDelay(Duration.millis(delayMs));

            TranslateTransition slide = new TranslateTransition(Duration.millis(400), child);
            slide.setFromY(30);
            slide.setToY(0);
            slide.setDelay(Duration.millis(delayMs));
            slide.setInterpolator(Interpolator.EASE_OUT);

            fade.play();
            slide.play();

            delayMs += 150;
        }
    }

    private void updateLabelDaya() {
        double total = controller.kalkulasiTotalDayaRumah();
        lblTotalDaya.setText(String.format("⚡ Beban Aktif: %.1f W", total));
    }

    public static void main(String[] args) {
        launch(args);
    }
}