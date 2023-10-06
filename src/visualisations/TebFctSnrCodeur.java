package visualisations;

public class TebFctSnrCodeur extends TebFctSnr {

    public TebFctSnrCodeur() {
        super(true, false);
    }

    public static void main(String[] args) {
        TebFctSnrCodeur simu = new TebFctSnrCodeur();
        simu.loop();
    }
}
