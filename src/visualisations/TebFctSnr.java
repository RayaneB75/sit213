package visualisations;

import java.io.FileWriter;
import java.io.IOException;

import simulateur.Simulateur;

public class TebFctSnr {

    public static void main(String[] args) {
        String csvFileName = "teb_fct_snr.csv";

        try (FileWriter csvWriter = new FileWriter(csvFileName)) {
            csvWriter.append("Signal Noise Rate (SNR) par bit");
            csvWriter.append(",");
            csvWriter.append("TEB RZ");
            csvWriter.append(",");
            csvWriter.append("TEB NRZ");
            csvWriter.append(",");
            csvWriter.append("TEB NRZT");
            csvWriter.append("\n");

            String[] forms = new String[] { "RZ", "NRZ", "NRZT" };

            for (int snr = -20; snr <= 15; snr++) {
                csvWriter.append(String.valueOf(snr));
                for (String form : forms) {
                    float teb = runSimulation(snr, form);
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(teb));
                }
                csvWriter.append("\n");
            }

            System.out.println("Simulation terminée. Résultats enregistrés dans " + csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static float runSimulation(int snr, String form) {
        float teb = 0;
        try {
            Simulateur sim = new Simulateur(
                    new String[] { "-seed", "1308", "-mess", "100000", "-form", String.valueOf(form), "-ampl", "-1f",
                            "1f", "-snrpb",
                            String.valueOf(snr) });
            sim.execute();
            teb = sim.calculTauxErreurBinaire();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teb;
    }
}
