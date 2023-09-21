/**
 * Cette classe Java réalise des simulations de transmission de données à différents niveaux de rapport signal/bruit (SNR)
 * et enregistre les taux d'erreur binaire (TEB) résultants dans un fichier CSV.
 *
 * Le script effectue 50 simulations en variant le SNR de -40 dB à 15 dB et enregistre les résultats dans un fichier CSV
 * Chaque simulation utilise un simulateur (classe Simulateur) pour générer et transmettre des données.
 *
 * Pour chaque valeur de SNR, le script enregistre le SNR et le TEB correspondant dans le fichier CSV.
 */
package visualisations;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import simulateur.Simulateur;

public class TebFctSnr {

    /**
     * La méthode principale qui réalise les simulations de TEB en fonction du SNR.
     *
     * @param args Les arguments de ligne de commande (non utilisés dans ce script).
     */
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

    /**
     * Cette méthode effectue une simulation avec le SNR spécifié et retourne le TEB résultant.
     *
     * @param snr Le rapport signal/bruit (SNR) spécifié en dB.
     * @return Le taux d'erreur binaire (TEB) calculé pour la simulation.
     */
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
