package src.model;

public abstract class PerangkatElektronik {
    protected boolean status = false;

    public void nyalakan() {
        status = true;
        System.out.println("Perangkat dinyalakan");
    }

    public void matikan() {
        status = false;
        System.out.println("Perangkat dimatikan");
    }

    public boolean isStatus() {
        return status;
    }
}
