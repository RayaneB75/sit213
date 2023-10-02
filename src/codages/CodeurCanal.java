package codages;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe d'un composant ayant le comportement d'un codeur RZ
 * Récupère une information de type Boolean et la transforme en une information
 * de type Float
 * 
 */
public class CodeurCanal extends Codeur<Boolean, Boolean> {
    /**
     * Constructeur du CodeurCanal avec les paramètres suivants :
     */
    public CodeurCanal() {
        super();
    }

    /**
     * Méthode qui reçoit une information et la stocke dans informationRecue
     * 
     * @param information l'information reçue
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
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationGeneree);
        }
    }

    /**
     * Méthode qui code l'information reçue et la stocke dans informationGeneree
     */
    protected void coder() {
        informationGeneree = new Information<Boolean>();
        for (boolean i : informationRecue) {
            if (i) {
                informationGeneree.add(true);
                informationGeneree.add(false);
                informationGeneree.add(true);
            } else {
                informationGeneree.add(false);
                informationGeneree.add(true);
                informationGeneree.add(false);
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
    public Information<Boolean> getInformationEmise() {
        return informationGeneree;
    }
}
