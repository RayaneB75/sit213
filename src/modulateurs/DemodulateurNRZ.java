package modulateurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class DemodulateurNRZ extends Modulateur<Float, Boolean> {

    /**
     * Constructeur d'un démodulateur NRZ (Non Return to Zero)]
     * 
     * @param nbEch
     * @param ampMin
     * @param ampMax
     */
    public DemodulateurNRZ(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Permet de recevoir une information dans le démodulateur NRZ
     * 
     * @param informationGeneree l'information reçue dans le démodulateur NRZ
     * 
     */
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    /**
     * Permet d'émettre l'information générée par le démodulateur NRZ
     * après l'avoir démodulée
     * 
     */
    public void emettre() throws InformationNonConformeException {
        demoduler();
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationGeneree);
        }
    }

    /**
     * Permet de démoduler (transformer un float en booléen) l'information reçue
     * dans le démodulateur NRZ
     * 
     */
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

    /**
     * geter de l'information reçue dans le démodulateur NRZ
     */
    public Information<Float> getInformationRecue() {
        return informationRecue;
    }

    /**
     * geter de l'information générée dans le démodulateur NRZ
     */
    public Information<Boolean> getInformationEmise() {
        return informationGeneree;
    }
}
