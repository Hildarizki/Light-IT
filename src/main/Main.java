package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import dao.PerangkatDAO;
import model.Lampu;
import model.StopKontak;
import model.Ruangan;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // ================= MODEL =================
        PerangkatDAO dao = new PerangkatDAO();

        Ruangan ruangTamu = new Ruangan("Ruang Tamu");
        Ruangan kamarTidur = new Ruangan("Kamar Tidur");

        Lampu lampuUtama = new Lampu("Lampu Utama");
        Lampu lampuSudut = new Lampu("Lampu Sudut");
        StopKontak tv = new StopKontak("TV");

        Lampu lampuKamar = new Lampu("Lampu Kamar");
        StopKontak charger = new StopKontak("Charger");

        ruangTamu.tambahPerangkat(lampuUtama);
        ruangTamu.tambahPerangkat(lampuSudut);
        ruangTamu.tambahPerangkat(tv);

        kamarTidur.tambahPerangkat(lampuKamar);
        kamarTidur.tambahPerangkat(charger);

        // ================= ROOT =================

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root");

        // ================= HEADER =================

        Label title = new Label("💡 Light-IT");
        title.getStyleClass().add("title");

        Label subtitle = new Label("Smart Home Dashboard");
        subtitle.getStyleClass().add("subtitle");

        Label statusLabel = new Label("Semua perangkat OFF");
        statusLabel.getStyleClass().add("status");

        // ================= RUANG TAMU =================

        VBox ruangTamuCard = new VBox(12);
        ruangTamuCard.getStyleClass().add("card");

        Label rtTitle = new Label("🏠 Ruang Tamu");
        rtTitle.getStyleClass().add("card-title");

        Label rtInfo = new Label("2 Lampu • 1 Stop Kontak");
        rtInfo.getStyleClass().add("card-info");

        Button btnRuangTamu = new Button("Nyalakan");
        btnRuangTamu.getStyleClass().add("primary-button");

        btnRuangTamu.setOnAction(e -> {

            if (lampuUtama.isStatus()) {

                ruangTamu.matikanSemua();
                dao.updateStatus("Lampu Utama", false);
                dao.updateStatus("Lampu Sudut", false);
                dao.updateStatus("TV", false);

                btnRuangTamu.setText("Nyalakan");
                statusLabel.setText("Ruang Tamu OFF");

            } else {

                ruangTamu.nyalakanSemua();
                dao.updateStatus("Lampu Utama", true);
                dao.updateStatus("Lampu Sudut", true);
                dao.updateStatus("TV", true);

                btnRuangTamu.setText("Matikan");
                statusLabel.setText("Ruang Tamu ON");
            }
        });

        ruangTamuCard.getChildren().addAll(
                rtTitle,
                rtInfo,
                btnRuangTamu
        );

        // ================= KAMAR TIDUR =================

        VBox kamarCard = new VBox(12);
        kamarCard.getStyleClass().add("card");

        Label kamarTitle = new Label("🛏 Kamar Tidur");
        kamarTitle.getStyleClass().add("card-title");

        Label kamarInfo = new Label("1 Lampu • 1 Stop Kontak");
        kamarInfo.getStyleClass().add("card-info");

        Button btnKamar = new Button("Nyalakan");
        btnKamar.getStyleClass().add("primary-button");

        btnKamar.setOnAction(e -> {

            if (lampuKamar.isStatus()) {

                kamarTidur.matikanSemua();
                dao.updateStatus("Lampu Kamar", false);
                dao.updateStatus("Charger", false);

                btnKamar.setText("Nyalakan");
                statusLabel.setText("Kamar Tidur OFF");

            } else {

                kamarTidur.nyalakanSemua();
                dao.updateStatus("Lampu Kamar", true);
                dao.updateStatus("Charger", true);
                btnKamar.setText("Matikan");
                statusLabel.setText("Kamar Tidur ON");
            }
        });

        kamarCard.getChildren().addAll(
                kamarTitle,
                kamarInfo,
                btnKamar
        );

        // ================= GLOBAL CONTROL =================

        VBox globalBox = new VBox(10);
        globalBox.setAlignment(Pos.CENTER);

        Button nyalakanSemua =
                new Button("⚡ Nyalakan Semua");
        nyalakanSemua.getStyleClass().add("primary-button");

        Button matikanSemua =
                new Button("⛔ Matikan Semua");
        matikanSemua.getStyleClass().add("danger-button");

        nyalakanSemua.setPrefWidth(220);
        matikanSemua.setPrefWidth(220);

        nyalakanSemua.setOnAction(e -> {

            ruangTamu.nyalakanSemua();
            kamarTidur.nyalakanSemua();
            dao.updateStatus("Lampu Utama", true);
            dao.updateStatus("Lampu Sudut", true);
            dao.updateStatus("TV", true);
            dao.updateStatus("Lampu Kamar", true);
            dao.updateStatus("Charger", true);

            btnRuangTamu.setText("Matikan");
            btnKamar.setText("Matikan");

            statusLabel.setText("Semua perangkat ON");
        });

        matikanSemua.setOnAction(e -> {

            ruangTamu.matikanSemua();
            kamarTidur.matikanSemua();
            dao.updateStatus("Lampu Utama", false);
            dao.updateStatus("Lampu Sudut", false);
            dao.updateStatus("TV", false);
            dao.updateStatus("Lampu Kamar", false);
            dao.updateStatus("Charger", false);

            btnRuangTamu.setText("Nyalakan");
            btnKamar.setText("Nyalakan");

            statusLabel.setText("Semua perangkat OFF");
        });

        globalBox.getChildren().addAll(
                nyalakanSemua,
                matikanSemua
        );

        // ================= LAYOUT =================

        root.getChildren().addAll(
                title,
                subtitle,
                statusLabel,
                ruangTamuCard,
                kamarCard,
                globalBox
        );

        Scene scene = new Scene(root, 500, 650);

        var css = getClass().getResource("/css/style.css");

        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        stage.setTitle("Light-IT");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}