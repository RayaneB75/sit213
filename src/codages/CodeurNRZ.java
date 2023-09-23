package codages;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class CodeurNRZ extends Codeur<Boolean, Float> {

    /**
     * Constructeur d'un codeur NRZ (Non Return to Zero)]
     * Utilisation du constructeur de la classe mère
     * 
     * @param nbEch  le nombre d'échantillons par symbole
     * @param ampMin l'amplitude minimale
     * @param ampMax l'amplitude maximale
     * 
     */
    public CodeurNRZ(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Permet de recevoir une information dans le codeur NRZ.
     *
     * @param information l'information reçue dans le codeur NRZ.
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }


    /**
     * Permet d'émettre l'information générée par le codeur NRZ
     * après l'avoir codée
     * 
     */
    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        coder();
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationGeneree);
        }
    }

    /**
     * Permet de coder (transformer un booléen en float) l'information reçue dans
     * le codeur NRZ
     * 
     */
    protected void coder() {
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
     * geter de l'information reçue dans le codeur NRZ
     */
    public Information<Boolean> getInformationRecue() {
        return informationRecue;
    }

    /**
     * geter de l'information générée et émise par le codeur NRZ
     */
    public Information<Float> getInformationEmise() {
        return informationGeneree;
    }
}
