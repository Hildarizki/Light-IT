package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardView {

    public Label statusLabel =
            new Label("Semua perangkat OFF");

    public Button btnRuangTamu =
            new Button("Nyalakan");

    public Button btnKamar =
            new Button("Nyalakan");

    public Button btnSemuaOn =
            new Button("⚡ Nyalakan Semua");

    public Button btnSemuaOff =
            new Button("⛔ Matikan Semua");

    public VBox root = new VBox(20);

    public VBox getRoot() {
        return root;
    }
}