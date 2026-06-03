package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import model.Lampu;
import model.Ruangan;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        // ================= OOP =================
        Ruangan ruangan = new Ruangan();

        Lampu lampu1 = new Lampu();
        Lampu lampu2 = new Lampu();

        ruangan.tambahPerangkat(lampu1);
        ruangan.tambahPerangkat(lampu2);

        // ================= ROOT =================
        VBox root = new VBox(25);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        // ================= HEADER =================
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("header");

        Label brand = new Label("Light-IT");
        brand.getStyleClass().add("label-title");

        header.getChildren().add(brand);

        // ================= HERO =================
        VBox hero = new VBox(10);

        Label welcome = new Label("Welcome to Light-IT");
        welcome.setStyle("-fx-text-fill: white; -fx-font-size: 28;");

        Label status = new Label("Devices Ready");
        status.setStyle("-fx-text-fill: #cccccc;");

        Button turnOnBtn = new Button("Nyalakan Semua");
        turnOnBtn.getStyleClass().add("primary-button");
        turnOnBtn.setMaxWidth(Double.MAX_VALUE);

        Button turnOffBtn = new Button("Matikan Semua");
        turnOffBtn.getStyleClass().add("primary-button");
        turnOffBtn.setMaxWidth(Double.MAX_VALUE);

        // ACTION BUTTON
        turnOnBtn.setOnAction(e -> {
            ruangan.nyalakanSemua();
            status.setText("Semua perangkat ON");
        });

        turnOffBtn.setOnAction(e -> {
            ruangan.matikanSemua();
            status.setText("Semua perangkat OFF");
        });

        hero.getChildren().addAll(welcome, status, turnOnBtn, turnOffBtn);

        // ================= ENERGY CARD =================
        VBox energyCard = new VBox(10);
        energyCard.getStyleClass().add("glass-card");

        Label usageTitle = new Label("CURRENT USAGE");
        usageTitle.setStyle("-fx-text-fill: #888; -fx-font-size: 12;");

        Label usageVal = new Label("1.2 kW");
        usageVal.getStyleClass().add("label-usage");

        ProgressBar pb = new ProgressBar(0.65);
        pb.setMaxWidth(Double.MAX_VALUE);

        energyCard.getChildren().addAll(usageTitle, usageVal, pb);

        // ================= SMART DEVICES =================
        VBox deviceSection = new VBox(15);

        Label deviceTitle = new Label("Smart Devices");
        deviceTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");

        HBox lampCard1 = createDeviceCard("Lampu 1", lampu1);
        HBox lampCard2 = createDeviceCard("Lampu 2", lampu2);

        deviceSection.getChildren().addAll(deviceTitle, lampCard1, lampCard2);

        // ================= ROOT ADD =================
        root.getChildren().addAll(header, hero, energyCard, deviceSection);

        // ================= SCROLL =================
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Scene scene = new Scene(scroll, 400, 700);

        // ================= CSS =================
        var css = getClass().getResource("/css/style.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        stage.setTitle("Light-IT Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    // ================= DEVICE CARD =================
    private HBox createDeviceCard(String name, Lampu lampu) {

        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
            "-fx-background-color: rgba(255,255,255,0.08);" +
            "-fx-padding: 15;" +
            "-fx-background-radius: 20;"
        );

        Label title = new Label(name);
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Label status = new Label("OFF");
        status.setStyle("-fx-text-fill: #cccccc;");

        VBox info = new VBox(5, title, status);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button toggle = new Button("OFF");
        toggle.setPrefWidth(60);

        updateToggle(toggle, lampu.isStatus());

        toggle.setOnAction(e -> {
            if (lampu.isStatus()) {
                lampu.matikan();
            } else {
                lampu.nyalakan();
            }

            updateToggle(toggle, lampu.isStatus());
            status.setText(lampu.isStatus() ? "ON" : "OFF");
        });

        card.getChildren().addAll(info, spacer, toggle);

        return card;
    }

    // ================= TOGGLE STYLE =================
    private void updateToggle(Button btn, boolean isOn) {
        if (isOn) {
            btn.setText("ON");
            btn.setStyle("-fx-background-color: #22C55E; -fx-text-fill: white;");
        } else {
            btn.setText("OFF");
            btn.setStyle("-fx-background-color: #334155; -fx-text-fill: white;");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}