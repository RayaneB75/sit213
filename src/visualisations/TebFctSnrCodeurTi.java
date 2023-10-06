package visualisations;

public class TebFctSnrCodeurTi extends TebFctSnr {

    public TebFctSnrCodeurTi() {
        super(true, true);
    }

    public static void main(String[] args) {
        TebFctSnrCodeurTi simu = new TebFctSnrCodeurTi();
        simu.loop();
    }
}
