package src.model;
public class Lampu extends PerangkatElektronik {
      @Override
    public void nyalakan() {
        super.nyalakan();
        System.out.println("Lampu menyala");
    }

    @Override
    public void matikan() {
        super.matikan();
        System.out.println("Lampu mati");
    }
}
