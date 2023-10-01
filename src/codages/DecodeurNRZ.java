package codages;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe d'un composant ayant le comportement d'un décodeur NRZ
 * Récupère une information de type Float et la transforme en une information de
 * type Boolean
 */
public class DecodeurNRZ extends Codeur<Float, Boolean> {

    /**
     * Constructeur du Decodeur NRZ avec les paramètres suivants :
     * 
     * @param nbEch  nombre d'échantillons par symbole
     * @param ampMin amplitude minimale
     * @param ampMax amplitude maximale
     */
    public DecodeurNRZ(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Méthode qui reçoit une information et la stocke dans informationRecue
     * 
     * @param information l'information reçue
     */
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    /**
     * Méthode qui émet l'information générée par le codeur après avoir
     * décodé l'information reçue
     */
    public void emettre() throws InformationNonConformeException {
        decoder();
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationGeneree);
        }
    }

    /**
     * Méthode qui décode l'information reçue et la stocke dans informationGeneree
     */
    protected void decoder() {
        informationGeneree = new Information<Boolean>();
        int i = 0;
        float moyenne = 0;
        for (float information : informationRecue) {
            if (i < nbEch) {
                moyenne += information;
            }
            i++;
            if (i == nbEch) {
                moyenne /= nbEch;
                informationGeneree.add(moyenne > (ampMax + ampMin) / 2);
                i = 0;
                moyenne = 0;
            }
        }
    }

    /**
     * Méthode qui retourne l'information reçue
     * 
     * @return informationRecue
     */
    public Information<Float> getInformationRecue() {
        return informationRecue;
    }

    /**
     * Méthode qui retourne l'information générée
     * 
     * @return informationGeneree
     */
    public Information<Boolean> getInformationEmise() {
        return informationGeneree;
    }
}
