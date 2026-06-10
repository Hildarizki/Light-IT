package model;

public abstract class PerangkatElektronik {

    protected String nama;
    protected boolean status = false;

    public void nyalakan() {
        status = true;
    }

    public void matikan() {
        status = false;
    }

    public boolean isStatus() {
        return status;
    }
}