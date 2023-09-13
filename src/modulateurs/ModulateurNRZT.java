package modulateurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class ModulateurNRZT extends Modulateur<Boolean, Float> {

    /**
     * Constructeur d'un modulateur NRZT (Non Return to Zero)]
     * Utilisation du constructeur de la classe mère
     *
     * @param nbEch  le nombre d'échantillons par symbole
     * @param ampMin l'amplitude minimale
     * @param ampMax l'amplitude maximale
     *
     */
    public ModulateurNRZT(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Permet de recevoir une information dans le modulateur NRZT
     *
     * @param informationGeneree l'information reçue dans le modulateur NRZT
     *
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    /**
     * Permet d'émettre l'information générée par le modulateur NRZT
     * après l'avoir modulée
     *
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
     * Permet de moduler (transformer un booléen en float) l'information reçue dans
     * le modulateur NRZT
     *
     */
    protected void moduler() {
        informationGeneree = new Information<Float>();
        for (int i = 0; i < informationRecue.nbElements(); i++) {
            if (informationRecue.iemeElement(i)) {
                for (int j = 0; j < nbEch; j++) {
                    if (j < nbEch/3)
                        informationGeneree.add(j / (nbEch/3) * ampMax);
                    else if (j < 2*nbEch/3)
                        informationGeneree.add(ampMax);
                    else
                        informationGeneree.add((nbEch-j) / (nbEch/3) * ampMax);
                }
            } else {
                for (int j = 0; j < nbEch; j++) {
                    if (j < nbEch/3)
                        informationGeneree.add(j / (nbEch/3) * ampMin);
                    else if (j < 2*nbEch/3)
                        informationGeneree.add(ampMin);
                    else
                        informationGeneree.add((nbEch-j) / (nbEch/3) * ampMin);
                }
            }
        }
    }

    /**
     * geter de l'information reçue dans le modulateur NRZT
     */
    public Information<Boolean> getInformationRecue() {
        return informationRecue;
    }

    /**
     * geter de l'information générée et émise par le modulateur NRZT
     */
    public Information<Float> getInformationEmise() {
        return informationGeneree;
    }
}
