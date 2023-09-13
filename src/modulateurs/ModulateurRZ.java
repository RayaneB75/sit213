package modulateurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class ModulateurRZ extends Modulateur<Boolean, Float> {

    public ModulateurRZ(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        moduler();
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationGeneree);
        }
    }

    protected void moduler() {
        informationGeneree = new Information<Float>();
        for (int i = 0; i < informationRecue.nbElements(); i++) {
            if (informationRecue.iemeElement(i)) {
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

    public Information<Boolean> getInformationRecue() {
        return informationRecue;
    }

    public Information<Float> getInformationEmise() {
        return informationGeneree;
    }
}
