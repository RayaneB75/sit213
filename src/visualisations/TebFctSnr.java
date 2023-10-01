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
public class TebFctSnr {

    /**
     * La méthode principale qui réalise les simulations de TEB en fonction du SNR.
     *
     * @param args Les arguments de ligne de commande (non utilisés dans ce script).
     */
    public static void main(String[] args) {
        // Créer un fichier CSV pour enregistrer les résultats

        String[] forms = new String[] { "RZ", "NRZ", "NRZT" };
        String csvFileName = "teb_fct_snr_";
        System.out.println("Simulation en cours...");
        for (String form : forms) {
            System.out.println("Génération des données pour la forme d'onde " + form + "...");
            try (FileWriter csvWriter = new FileWriter(csvFileName + form + ".csv")) {
                // Écrire l'en-tête du CSV
                csvWriter.append("Signal Noise Rate (SNR) par bit");
                csvWriter.append(",");
                csvWriter.append("Taux d'erreur binaire (TEB)");
                csvWriter.append("\n");

                // Réaliser 50 simulations en incrémentant le nombre d'échantillons par symbole
                for (int snr = -20; snr <= 15; snr++) {
                    // Effectuer la simulation
                    float teb = runSimulation(snr, form);

                    // Écrire les résultats dans le CSV
                    csvWriter.append(String.valueOf(snr));
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(teb));
                    csvWriter.append("\n");
                }

                System.out.println("Simulation terminée. Résultats enregistrés dans " + csvFileName + form + ".csv");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                            "1f", "-snrpb",
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
