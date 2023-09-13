package modulateurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class DemodulateurNRZ extends Modulateur<Float, Boolean> {

    public DemodulateurNRZ() {
        super();
    }

    public DemodulateurNRZ(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    public void emettre() throws InformationNonConformeException {
        demoduler();
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationGeneree);
        }
    }

    protected void demoduler() {
        informationGeneree = new Information<Boolean>();
        for (int i = 0; i < informationRecue.nbElements(); i += nbEch) {
            float moyenne = 0;
            for (int j = 0; j < nbEch; j++) {
                moyenne += informationRecue.iemeElement(i + j);
            }
            moyenne /= nbEch;
            informationGeneree.add(moyenne > (ampMax + ampMin) / 2);
        }
    }

    public Information<Float> getInformationRecue() {
        return informationRecue;
    }

    public Information<Boolean> getInformationEmise() {
        return informationGeneree;
    }
}
