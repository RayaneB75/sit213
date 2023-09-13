package modulateurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class DemodulateurNRZT extends Modulateur<Float, Boolean> {

    /**
     * Constructeur d'un démodulateur NRZT (Non Return to Zero)]
     *
     * @param nbEch
     * @param ampMin
     * @param ampMax
     */
    public DemodulateurNRZT(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Permet de recevoir une information dans le démodulateur NRZT
     *
     * @param informationGeneree l'information reçue dans le démodulateur NRZT
     *
     */
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    /**
     * Permet d'émettre l'information générée par le démodulateur NRZT
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
     * dans le démodulateur NRZT
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
     * geter de l'information reçue dans le démodulateur NRZT
     */
    public Information<Float> getInformationRecue() {
        return informationRecue;
    }

    /**
     * geter de l'information générée dans le démodulateur NRZT
     */
    public Information<Boolean> getInformationEmise() {
        return informationGeneree;
    }
}
