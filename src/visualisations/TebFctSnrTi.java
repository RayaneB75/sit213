package visualisations;

public class TebFctSnrTi extends TebFctSnr {

    public TebFctSnrTi() {
        super(false, true);
    }

    public static void main(String[] args) {
        TebFctSnrTi simu = new TebFctSnrTi();
        simu.loop();
    }
}
