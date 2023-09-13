package modulateurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class DemodulateurRZ extends Modulateur<Float, Boolean> {

    /**
     * Constructeur du DemodulateurRZ avec les paramètres suivants :
     * 
     * @param nbEch
     * @param ampMin
     * @param ampMax
     */
    public DemodulateurRZ(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Méthode qui reçoit une information et la stocke dans informationRecue
     * 
     * @param information
     */
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    /**
     * Méthode qui émet l'information générée par le modulateur après avoir
     * démodulé l'information reçue
     */
    public void emettre() throws InformationNonConformeException {
        demoduler();
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationGeneree);
        }
    }

    /**
     * Méthode qui démodule l'information reçue et la stocke dans informationGeneree
     */
    protected void demoduler() {
        informationGeneree = new Information<Boolean>();
        for (int i = 0; i < informationRecue.nbElements(); i += nbEch) {
            float moyenne = 0;
            for (int j = nbEch / 3; j < 2 * nbEch / 3; j++) {
                moyenne += informationRecue.iemeElement(i + j);
            }
            moyenne /= nbEch / 3;
            informationGeneree.add(moyenne > (ampMax + ampMin) / 2);
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
