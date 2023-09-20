package codages;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class CodeurRZ extends Codeur<Boolean, Float> {

    /**
     * Constructeur du ModulateurRZ avec les paramètres suivants :
     * 
     * @param nbEch
     * @param ampMin
     * @param ampMax
     */
    public CodeurRZ(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Méthode qui reçoit une information et la stocke dans informationRecue
     * 
     * @param information
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    /**
     * Méthode qui émet l'information générée par le codeur après avoir codée
     * l'information reçue
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
     * Méthode qui code l'information reçue et la stocke dans informationGeneree
     */
    protected void coder() {
        informationGeneree = new Information<Float>();
        for (boolean i : informationRecue) {
            if (i) {
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
     * Méthode qui retourne l'information reçue
     * 
     * @return informationRecue
     */
    public Information<Boolean> getInformationRecue() {
        return informationRecue;
    }

    /**
     * Méthode qui retourne l'information générée
     * 
     * @return informationGeneree
     */
    public Information<Float> getInformationEmise() {
        return informationGeneree;
    }
}