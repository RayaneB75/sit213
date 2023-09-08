package transmetteurs;

import destinations.*;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe d'un composant ayant le comportement d'un transmetteur parfait
 * d'informations dont les éléments sont de type Boolean.
 *
 * @see Transmetteur
 */
public class TransmetteurParfait extends Transmetteur<Boolean, Boolean> {

    /**
     * un constructeur factorisant les initialisations communes aux
     * réalisations de la classe abstraite Transmetteur
     */
    public TransmetteurParfait() {
        super();
    }

    public void setInformationRecue(Information<Boolean> informationRecue) {
        this.informationRecue = informationRecue;
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
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
     * émet l'information construite par le transmetteur à l'ensemble
     * des composants connectés à sa sortie.
     */
    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue == null) {
            throw new InformationNonConformeException("Erreur : Information non conforme");
        } else {
            this.informationEmise = this.informationRecue;
            for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
                destinationConnectee.recevoir(informationEmise);
            }
        }
    }
}
