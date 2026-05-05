package src.model;

import java.util.ArrayList;

public class Ruangan {
    public ArrayList<PerangkatElektronik> perangkat = new ArrayList<>();

    public void tambahPerangkat(PerangkatElektronik p) {
        perangkat.add(p);
    }

    public void nyalakanSemua() {
        for (PerangkatElektronik p : perangkat) {
            p.nyalakan();
        }
    }

    public void matikanSemua() {
        for (PerangkatElektronik p : perangkat) {
            p.matikan();
        }
    }
}
