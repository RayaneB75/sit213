package modulateurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class ModulateurNRZ extends Modulateur<Boolean, Float> {

    /**
     * Constructeur d'un modulateur NRZ (Non Return to Zero)]
     * Utilisation du constructeur de la classe mère
     * 
     * @param nbEch  le nombre d'échantillons par symbole
     * @param ampMin l'amplitude minimale
     * @param ampMax l'amplitude maximale
     * 
     */
    public ModulateurNRZ(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Permet de recevoir une information dans le modulateur NRZ
     * 
     * @param informationGeneree l'information reçue dans le modulateur NRZ
     * 
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    /**
     * Permet d'émettre l'information générée par le modulateur NRZ
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
     * le modulateur NRZ
     * 
     */
    protected void moduler() {
        informationGeneree = new Information<Float>();
        for (boolean i : informationRecue) {
            if (i) {
                for (int j = 0; j < nbEch; j++) {
                    informationGeneree.add(ampMax);
                }
            } else {
                for (int j = 0; j < nbEch; j++) {
                    informationGeneree.add(ampMin);
                }
            }
        }
    }

    /**
     * geter de l'information reçue dans le modulateur NRZ
     */
    public Information<Boolean> getInformationRecue() {
        return informationRecue;
    }

    /**
     * geter de l'information générée et émise par le modulateur NRZ
     */
    public Information<Float> getInformationEmise() {
        return informationGeneree;
    }
}
