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

import simulateur.Simulateur;

/**
 * Cette classe Java réalise des simulations de transmission de données à
 * différents niveaux de rapport signal/bruit (SNR) et enregistre les taux
 * d'erreur binaire (TEB) résultants dans un fichier CSV.
 * 
 */
public class TebFctSnrTi {

    /**
     * La méthode principale qui réalise les simulations de TEB en fonction du SNR.
     *
     * @param args Les arguments de ligne de commande (non utilisés dans ce script).
     */
    public static void main(String[] args) {
        // Créer un fichier CSV pour enregistrer les résultats

        String csvFileName = "teb_fct_snr_ti.csv";
        System.out.println("Simulation en cours...");
        try (FileWriter csvWriter = new FileWriter(csvFileName)) {
            csvWriter.append("Signal Noise Rate (SNR) par bit");
            csvWriter.append(",");
            csvWriter.append("TEB RZ Ti");
            csvWriter.append(",");
            csvWriter.append("TEB NRZ Ti");
            csvWriter.append(",");
            csvWriter.append("TEB NRZT Ti");
            csvWriter.append("\n");

            String[] forms = new String[] { "RZ", "NRZ", "NRZT" };

            for (int snr = -10; snr <= 15; snr++) {
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

    /**
     * Cette méthode effectue une simulation avec le SNR spécifié et retourne le TEB
     * résultant.
     *
     * @param snr Le rapport signal/bruit (SNR) spécifié en dB.
     * @return Le taux d'erreur binaire (TEB) calculé pour la simulation.
     */
    private static float runSimulation(int snr, String form) {
        float teb = 0;
        try {
            Simulateur sim = new Simulateur(
                    new String[] { "-seed", "1308", "-mess", "100000", "-form", String.valueOf(form), "-ampl", "-1f",
                            "1f", "-ti", "5", "0.4", "10", "0.25", "15", "0.2", "20", "0.1", "30", "0.05", "-snrpb",
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
