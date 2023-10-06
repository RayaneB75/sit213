package visualisations;

public class TebFctSnrBase extends TebFctSnr {

    public TebFctSnrBase() {
        super(false, false);
    }

    public static void main(String[] args) {
        TebFctSnrBase simu = new TebFctSnrBase();
        simu.loop();
    }
}
