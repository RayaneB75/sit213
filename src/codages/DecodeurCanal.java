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
public class DecodeurCanal extends Codeur<Boolean, Boolean> {
    /**
     * Constructeur du CodeurCanal avec les paramètres suivants :
     */
    public DecodeurCanal() {
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
        String informationTrame[] = new String[informationRecue.nbElements() / 3];
        int i = 0;
        int j = 0;
        for (boolean information : informationGeneree) {
            if (j < 3) {
                if (information) {
                    informationTrame[i] += "1";
                } else {
                    informationTrame[i] += "0";
                }
                j++;
            } else {
                i++;
                j = 0;
            }
        }
        for (String information : informationTrame) {
            if (information.equals("000") || information.equals("010") || information.equals("011") || information.equals("110")) {
                informationGeneree.add(false);
            } else {
                informationGeneree.add(true);
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
