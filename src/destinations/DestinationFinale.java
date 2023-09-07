package destinations;

import information.Information;
import information.InformationNonConformeException;

/**
 * Classe d'un composant ayant le comportement d'une destination finale
 * d'informations dont les éléments sont de type Boolean.
 * Elle hérite de la classe DestinationInterface.
 *
 * @see DestinationInterface
 * @see Destination
 */
public class DestinationFinale extends Destination<Boolean> {

    /**
     * un constructeur factorisant les initialisations communes aux
     * réalisations de la classe abstraite Destination
     */
    public DestinationFinale() {
        super();
        this.informationRecue = new Information<>();
    }

    /**
     * reçoit une information
     *
     * @param information l'information à recevoir
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        this.informationRecue = information;
    }
}
