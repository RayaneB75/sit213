package visualisations;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import simulateur.Simulateur;

public class TebFctSnr {

    public static void main(String[] args) {
        List<Integer> snrValues = new ArrayList<>();
        List<Float> tebValues = new ArrayList<>();

        // Créer un fichier CSV pour enregistrer les résultats
        String csvFileName = "teb_fct_snr.csv";

        try (FileWriter csvWriter = new FileWriter(csvFileName)) {
            // Écrire l'en-tête du CSV
            csvWriter.append("Signal Noise Rate (SNR) par bit");
            csvWriter.append(",");
            csvWriter.append("Taux d'erreur binaire (TEB)");
            csvWriter.append("\n");

            // Réaliser 50 simulations en incrémentant le nombre d'échantillons par symbole
            for (int snr = -40; snr <= 15; snr++) {
                // Effectuer la simulation
                float teb = runSimulation(snr);

                // Enregistrer les résultats dans les listes
                snrValues.add(snr);
                tebValues.add(teb);

                // Écrire les résultats dans le CSV
                csvWriter.append(String.valueOf(snr));
                csvWriter.append(",");
                csvWriter.append(String.valueOf(teb));
                csvWriter.append("\n");
            }

            System.out.println("Simulation terminée. Résultats enregistrés dans " + csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static float runSimulation(int snr) {
        float teb = 0;
        try {
            Simulateur sim = new Simulateur(
                    new String[] { "-seed", "1308", "-mess", "100000", "-form", "NRZ", "-ampl", "-1f", "1f", "-snrpb",
                            String.valueOf(snr) });
            sim.execute();
            teb = sim.calculTauxErreurBinaire();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Retourner le TEB calculé
        return teb;
    }
}
