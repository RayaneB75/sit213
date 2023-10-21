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
import java.util.LinkedList;

import simulateur.Simulateur;

/**
 * Cette classe Java réalise des simulations de transmission de données à
 * différents niveaux de rapport signal/bruit (SNR) et enregistre les taux
 * d'erreur binaire (TEB) résultants dans un fichier CSV.
 * 
 */
public class TebFctNbEch {

    protected Simulateur simulateur;

    protected String form;

    protected String csvFileName;

    protected Boolean codeur;

    protected LinkedList<String> args = new LinkedList<String>();

    public TebFctNbEch(Boolean codeur) {
        this.codeur = codeur;
        addBasicArgs();
        this.csvFileName = "teb_fct_nbEch";
        if (codeur) {
            addSpecificArgs(new String[] { "-codeur" });
            this.csvFileName += "_codeur";
        }
        this.csvFileName += ".csv";
    }

    private void addBasicArgs() {
        String[] basic_args = new String[] { "-seed", "1308", "-mess", "1000", "-ti", "500", "0.5", "-snrpb", "7" };

        addSpecificArgs(basic_args);
    }

    protected void addSpecificArgs(String[] specific_args) {
        for (String arg : specific_args) {
            args.add(arg);
        }
    }

    /**
     * La méthode principale qui réalise les simulations de TEB en fonction du SNR.
     *
     */
    protected void loop() {
        // Créer un fichier CSV pour enregistrer les résultats

        System.out.println("Simulations en cours pour " + this.csvFileName + "...");
        try (FileWriter csvWriter = new FileWriter(csvFileName)) {
            csvWriter.append("NbEch");
            csvWriter.append(",");
            csvWriter.append("TEB RZ");
            csvWriter.append(",");
            csvWriter.append("TEB NRZ");
            csvWriter.append(",");
            csvWriter.append("TEB NRZT");
            csvWriter.append("\n");

            String[] forms = new String[] { "RZ", "NRZ", "NRZT" };
            System.out.print("Progression : [");
            for (int nbEch = 3; nbEch <= 1000; nbEch += 10) {
                csvWriter.append(String.valueOf(nbEch));
                for (String form : forms) {

                    float teb = runSimulation(form, nbEch);
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(teb));

                }
                // Update progress bar
                int percentage = ((nbEch - 10) / 100) * 100;
                System.out.print("\rProgression: [");
                for (int j = 0; j <= 100; j++) {
                    if (j <= percentage) {
                        System.out.print("=");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("] " + percentage + "%");
                csvWriter.append("\n");
            }

            System.out.println("\nSimulations terminées");
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
    private float runSimulation(String form, int nbEch) {
        float teb = 0;
        LinkedList<String> loc_args = new LinkedList<String>(this.args);
        try {
            loc_args.add("-nbEch");
            loc_args.add(String.valueOf(nbEch));
            loc_args.add("-form");
            loc_args.add(form);
            String[] finalargs = loc_args.toArray(new String[loc_args.size()]);
            Simulateur sim = new Simulateur(finalargs);
            sim.execute();
            teb = sim.calculTauxErreurBinaire();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Retourner le TEB calculé
        return teb;
    }

    public static void main(String[] args) {
        Boolean codeur = false;
        for (int i = 0; i < args.length; i++) { // traiter les arguments 1 par 1
            if (args[i].matches("-codeur")) {
                codeur = true;
            } else {
                throw new IllegalArgumentException("Argument " + args[i] + " non reconnu");
            }
        }
        TebFctNbEch simu = new TebFctNbEch(codeur);
        simu.loop();
    }

}
