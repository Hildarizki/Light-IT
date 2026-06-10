package controller;

import dao.PerangkatDAO;
import model.Ruangan;
import view.DashboardView;

public class DashboardController {

    private DashboardView view;

    private Ruangan ruangTamu;
    private Ruangan kamarTidur;

    private PerangkatDAO dao =
            new PerangkatDAO();

    public DashboardController(
            DashboardView view,
            Ruangan ruangTamu,
            Ruangan kamarTidur) {

        this.view = view;
        this.ruangTamu = ruangTamu;
        this.kamarTidur = kamarTidur;

        initEvents();
    }

    private void initEvents() {

        view.btnSemuaOn.setOnAction(e -> {

            ruangTamu.nyalakanSemua();
            kamarTidur.nyalakanSemua();

            view.statusLabel.setText(
                    "Semua perangkat ON");

            dao.updateStatus(
                    "Lampu Utama",
                    true
            );
        });

        view.btnSemuaOff.setOnAction(e -> {

            ruangTamu.matikanSemua();
            kamarTidur.matikanSemua();

            view.statusLabel.setText(
                    "Semua perangkat OFF");

            dao.updateStatus(
                    "Lampu Utama",
                    false
            );
        });
    }
}