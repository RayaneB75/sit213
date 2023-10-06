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
public class TebFctSnr {

    protected Simulateur simulateur;

    protected String form;

    protected Boolean codeur;
    protected Boolean ti;

    protected String csvFileName;

    protected LinkedList<String> args = new LinkedList<String>();

    public TebFctSnr(Boolean codeur, Boolean ti) {
        addBasicArgs();
        this.codeur = codeur;
        this.ti = ti;
        this.csvFileName = "teb_fct_snr";
        if (ti) {
            addSpecificArgs(new String[] { "-ti", "5", "0.4", "10", "0.25", "15", "0.2", "20", "0.1", "30", "0.05" });
            this.csvFileName += "_ti";
        }
        if (codeur) {
            addSpecificArgs(new String[] { "-codeur" });
            this.csvFileName += "_codeur";
        }
        this.csvFileName += ".csv";
    }

    private void addBasicArgs() {
        String[] basic_args = new String[] { "-seed", "1308", "-mess", "100000", "-ampl", "-1f", "1f" };

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

        System.out.println("Simulation en cours pour " + this.csvFileName + "...");
        String endTitle = (this.codeur ? " Codeur" : "" + (this.ti ? " Ti" : ""));
        try (FileWriter csvWriter = new FileWriter(csvFileName)) {
            csvWriter.append("SnrPb");
            csvWriter.append(",");
            csvWriter.append("TEB RZ" + endTitle);
            csvWriter.append(",");
            csvWriter.append("TEB NRZ" + endTitle);
            csvWriter.append(",");
            csvWriter.append("TEB NRZT" + endTitle);
            csvWriter.append("\n");

            String[] forms = new String[] { "RZ", "NRZ", "NRZT" };

            for (int snr = -10; snr <= 15; snr++) {
                csvWriter.append(String.valueOf(snr));
                for (String form : forms) {
                    float teb = runSimulation(form, snr);
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(teb));
                }
                csvWriter.append("\n");
            }

            System.out.println("Simulation terminée");
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
    private float runSimulation(String form, float snr) {
        float teb = 0;
        LinkedList<String> loc_args = new LinkedList<String>(this.args);
        try {
            loc_args.add("-snrpb");
            loc_args.add(String.valueOf(snr));
            loc_args.add("-form");
            loc_args.add(form);
            System.out.println(loc_args);
            Simulateur sim = new Simulateur(loc_args.toArray(new String[loc_args.size()]));
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
        Boolean ti = false;
        for (int i = 0; i < args.length; i++) { // traiter les arguments 1 par 1
            if (args[i].matches("-ti")) {
                ti = true;
            } else if (args[i].matches("-codeur")) {
                codeur = true;
            } else {
                throw new IllegalArgumentException("Argument " + args[i] + " non reconnu");
            }
        }
        TebFctSnr simu = new TebFctSnr(codeur, ti);
        simu.loop();
    }

}
