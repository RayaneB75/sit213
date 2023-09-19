/**
 * Cette classe représente un demodulateur RZ (Return to Zero).
 * Il prend en entrée une information sous forme d'une séquence de valeurs flottantes
 * et produit en sortie une information sous forme d'une séquence de valeurs booléennes.
 * Le demodulateur RZ compare chaque échantillon à la moyenne entre ampMin et ampMax
 * pour déterminer si le signal est haut (true) ou bas (false).
 *
 * @param <Float>    Le type des éléments de l'information d'entrée.
 * @param <Boolean>  Le type des éléments de l'information de sortie.
 */
package modulateurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class DemodulateurRZ extends Modulateur<Float, Boolean> {

    /**
     * Constructeur de la classe DemodulateurRZ.
     *
     * @param nbEch   Le nombre d'échantillons par symbole.
     * @param ampMin  La valeur minimale de l'amplitude du signal.
     * @param ampMax  La valeur maximale de l'amplitude du signal.
     */
    public DemodulateurRZ(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Méthode pour recevoir une information en entrée.
     *
     * @param information L'information à recevoir.
     * @throws InformationNonConformeException Si l'information est nulle ou non conforme.
     */
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
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
        demoduler();
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationGeneree);
        }
    }

    /**
     * Méthode protégée pour effectuer la démodulation RZ de l'information d'entrée.
     */
    protected void demoduler() {
        informationGeneree = new Information<Boolean>();
        for (int i = 0; i < informationRecue.nbElements(); i += nbEch) {
            float moyenne = 0;
            for (int j = 0; j < nbEch; j++) {
                moyenne += informationRecue.iemeElement(i + j);
            }
            moyenne /= nbEch;
            informationGeneree.add(moyenne > (ampMax + ampMin) / 2);
        }
    }

    /**
     * Méthode pour obtenir l'information reçue en entrée.
     *
     * @return L'information reçue.
     */
    public Information<Float> getInformationRecue() {
        return informationRecue;
    }

    /**
     * Méthode pour obtenir l'information émise en sortie.
     *
     * @return L'information émise.
     */
    public Information<Boolean> getInformationEmise() {
        return informationGeneree;
    }
}
