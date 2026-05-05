package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import src.model.Lampu;
import src.model.Ruangan;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // ================= LOGIC =================
        Ruangan ruangan = new Ruangan();

        Lampu lampu1 = new Lampu();
        Lampu lampu2 = new Lampu();

        ruangan.tambahPerangkat(lampu1);
        ruangan.tambahPerangkat(lampu2);

        // ================= UI ROOT =================
        VBox root = new VBox(20);
        root.setStyle("-fx-padding: 20; -fx-background-color: #F1F5F9;");
        root.setAlignment(Pos.CENTER);

        // ================= TITLE =================
        Label title = new Label("Light-IT Dashboard");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // ================= CARD 1 =================
        VBox card1 = new VBox(10);
        card1.setStyle(
            "-fx-background-color: white;" +
            "-fx-padding: 15;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10,0,0,5);"
        );
        card1.setAlignment(Pos.CENTER);

        Label lampu1Label = new Label("Lampu 1");
        Button lampuBtn1 = new Button("ON");
        lampuBtn1.setStyle("-fx-background-color: #22C55E; -fx-text-fill: white;");

        lampuBtn1.setOnAction(e -> {
            if (lampuBtn1.getText().equals("ON")) {
                lampu1.matikan();
                lampuBtn1.setText("OFF");
                lampuBtn1.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white;");
            } else {
                lampu1.nyalakan();
                lampuBtn1.setText("ON");
                lampuBtn1.setStyle("-fx-background-color: #22C55E; -fx-text-fill: white;");
            }
        });

        card1.getChildren().addAll(lampu1Label, lampuBtn1);

        // ================= CARD 2 =================
        VBox card2 = new VBox(10);
        card2.setStyle(
            "-fx-background-color: white;" +
            "-fx-padding: 15;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10,0,0,5);"
        );
        card2.setAlignment(Pos.CENTER);

        Label lampu2Label = new Label("Lampu 2");
        Button lampuBtn2 = new Button("ON");
        lampuBtn2.setStyle("-fx-background-color: #22C55E; -fx-text-fill: white;");

        lampuBtn2.setOnAction(e -> {
            if (lampuBtn2.getText().equals("ON")) {
                lampu2.matikan();
                lampuBtn2.setText("OFF");
                lampuBtn2.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white;");
            } else {
                lampu2.nyalakan();
                lampuBtn2.setText("ON");
                lampuBtn2.setStyle("-fx-background-color: #22C55E; -fx-text-fill: white;");
            }
        });

        card2.getChildren().addAll(lampu2Label, lampuBtn2);

        // ================= ROW =================
        HBox deviceRow = new HBox(15, card1, card2);
        deviceRow.setAlignment(Pos.CENTER);

        // ================= BUTTON NYALAKAN SEMUA =================
        Button nyalaBtn = new Button("Nyalakan Semua");
        nyalaBtn.setStyle(
            "-fx-background-color: #22C55E;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;"
        );

        nyalaBtn.setOnAction(e -> {
            ruangan.nyalakanSemua();

            lampuBtn1.setText("ON");
            lampuBtn2.setText("ON");

            lampuBtn1.setStyle("-fx-background-color: #22C55E; -fx-text-fill: white;");
            lampuBtn2.setStyle("-fx-background-color: #22C55E; -fx-text-fill: white;");
        });

        // ================= BUTTON MATIKAN SEMUA =================
        Button matiBtn = new Button("Matikan Semua");
        matiBtn.setStyle(
            "-fx-background-color: #2563EB;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;"
        );

        matiBtn.setOnAction(e -> {
            ruangan.matikanSemua();

            lampuBtn1.setText("OFF");
            lampuBtn2.setText("OFF");

            lampuBtn1.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white;");
            lampuBtn2.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white;");
        });

        // ================= ADD =================
        root.getChildren().addAll(title, deviceRow, nyalaBtn, matiBtn);

        // ================= SCENE =================
        Scene scene = new Scene(root, 400, 320);

        stage.setTitle("Light-IT");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
    try {
        launch();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}