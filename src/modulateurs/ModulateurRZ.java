/**
 * Cette classe représente un modulateur RZ (Return to Zero).
 * Il prend en entrée une information sous forme d'une séquence de valeurs booléennes
 * et produit en sortie une information sous forme d'une séquence de valeurs flottantes.
 * Le modulateur RZ convertit chaque bit "true" en une séquence de valeurs comprises entre ampMin et ampMax,
 * et chaque bit "false" en une séquence de valeurs égales à ampMin.
 *
 * @param <Boolean>  Le type des éléments de l'information d'entrée.
 * @param <Float>    Le type des éléments de l'information de sortie.
 */
package modulateurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class ModulateurRZ extends Modulateur<Boolean, Float> {

    /**
     * Constructeur de la classe ModulateurRZ.
     *
     * @param nbEch   Le nombre d'échantillons par symbole.
     * @param ampMin  La valeur minimale de l'amplitude du signal.
     * @param ampMax  La valeur maximale de l'amplitude du signal.
     */
    public ModulateurRZ(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Méthode pour recevoir une information en entrée.
     *
     * @param information L'information à recevoir.
     * @throws InformationNonConformeException Si l'information est nulle ou non conforme.
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    /**
     * Méthode pour émettre l'information modulée en sortie vers les destinations connectées.
     *
     * @throws InformationNonConformeException Si l'information générée est non conforme.
     */
    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        moduler();
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationGeneree);
        }
    }

    /**
     * Méthode protégée pour effectuer la modulation RZ de l'information d'entrée.
     */
    protected void moduler() {
        informationGeneree = new Information<Float>();
        for (int i = 0; i < informationRecue.nbElements(); i++) {
            if (informationRecue.iemeElement(i)) {
                for (int j = 0; j < nbEch / 3; j++) {
                    informationGeneree.add(ampMin);
                }
                for (int j = 0; j < nbEch / 3; j++) {
                    informationGeneree.add(ampMax);
                }
                for (int j = 0; j < nbEch / 3; j++) {
                    informationGeneree.add(ampMin);
                }
            } else {
                for (int j = 0; j < nbEch; j++) {
                    informationGeneree.add(ampMin);
                }
            }
        }
    }

    /**
     * Méthode pour obtenir l'information reçue en entrée.
     *
     * @return L'information reçue.
     */
    public Information<Boolean> getInformationRecue() {
        return informationRecue;
    }

    /**
     * Méthode pour obtenir l'information émise en sortie.
     *
     * @return L'information émise.
     */
    public Information<Float> getInformationEmise() {
        return informationGeneree;
    }
}
