package visualisations;

import java.io.FileWriter;
import java.io.IOException;
import simulateur.Simulateur;

public class EstGaussienne {

    /**
     * Méthode principale
     * 
     * @param args
     */
    public static void main(String[] args) {

        // Créer un fichier CSV pour enregistrer les résultats
        String csvFileName = "histo_bruit.csv";

        try (FileWriter csvWriter = new FileWriter(csvFileName)) {
            // Écrire l'en-tête du CSV
            csvWriter.append("Puissance de bruit");
            csvWriter.append("\n");

            // Réaliser 50 simulations en incrémentant le nombre d'échantillons par symbole
            for (int snr = 0; snr <= 10000; snr++) {
                // Effectuer la simulation
                float pb = runSimulation();

                // Écrire les résultats dans le CSV
                csvWriter.append(String.valueOf(pb));
                csvWriter.append("\n");
            }

            System.out.println("Simulation terminée. Résultats enregistrés dans " + csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Effectue une simulation avec les mêmes paramètres à chaque fois :
     * 
     * @return
     */
    private static float runSimulation() {
        float pb = 0;
        try {
            Simulateur sim = new Simulateur(
                    new String[] { "-mess", "10000", "-form", "NRZ", "-ampl", "-1f", "1f", "-snrpb", "3f" });
            sim.execute();
            pb = sim.puissanceDeBruitMoyenne();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Retourner le TEB calculé
        return pb;
    }
}
