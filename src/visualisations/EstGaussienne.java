package visualisations;

import destinations.DestinationInterface;
import codages.CodeurNRZ;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceFixe;
import transmetteurs.TransmetteurGaussien;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe d'un composant ayant le comportement d'un visualiseur
 * d'informations dont les éléments sont de type Float.
 * 
 */
public class EstGaussienne implements DestinationInterface<Float> {

    Information<Float> information;

    /**
     * Constructeur de la classe EstGaussienne
     * (constructeur d'un composant)
     */
    public EstGaussienne() {
        information = new Information<>();
    }

    /**
     * getter de l'information reçue
     * 
     * @return l'information reçue
     */
    public Information<Float> getInformationRecue() {
        return information;
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode émettre.
     *
     * @param information l'information reçue
     */
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        for (Float i : information) {
            this.information.add(i);
        }
    }

    /**
     * émet l'information construite par le transmetteur
     * 
     * @throws InformationNonConformeException si l'information reçue n'est pas
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) throws Exception {
        EstGaussienne graph = new EstGaussienne();
        SourceFixe src = new SourceFixe("0000000000");
        CodeurNRZ codeurNRZ = new CodeurNRZ(1000000, -2, 2);
        TransmetteurGaussien transmetteur = new TransmetteurGaussien(-10, 1000000);

        src.connecter(codeurNRZ);
        codeurNRZ.connecter(transmetteur);

        transmetteur.connecter(graph);

        src.emettre();

        // Enregistrement dans un fichier CSV
        String csvFileName = "noise_gaussian.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName))) {
            // Écriture de l'en-tête du CSV (facultatif)
            writer.println("Bruit gaussien");

            // Écriture des valeurs dans le fichier CSV
            for (Float value : graph.getInformationRecue()) {
                writer.println(value);
            }

            System.out.println("Données enregistrées dans " + csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}