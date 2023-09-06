package transmetteurs;

import destinations.*;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurParfait extends Transmetteur<Boolean, Boolean> {

    public TransmetteurParfait() {
        super();
    }

    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

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
