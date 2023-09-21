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
public class TransmetteurParfait<T> extends Transmetteur<T, T> {

    /**
     * un constructeur factorisant les initialisations communes aux
     * réalisations de la classe abstraite Transmetteur
     */
    public TransmetteurParfait() {
        super();
    }

    /**
     * Setter de l'information reçue
     * 
     * @param informationRecue
     */
    public void setInformationRecue(Information<T> informationRecue) {
        this.informationRecue = informationRecue;
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     */
    public void recevoir(Information<T> information) throws InformationNonConformeException {
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
            for (DestinationInterface<T> destinationConnectee : destinationsConnectees) {
                destinationConnectee.recevoir(informationEmise);
            }
        }
    }

    /**
     * retourne la puissance du bruit moyen
     */
    public float calculerPuissanceDeBruitMoyen() {
        return 0f;
    }
}
