package codages;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class CodeurNRZT extends Codeur<Boolean, Float> {

    /**
     * Constructeur d'un codeur NRZT (Non Return to Zero)]
     * Utilisation du constructeur de la classe mère
     *
     * @param nbEch  le nombre d'échantillons par symbole
     * @param ampMin l'amplitude minimale
     * @param ampMax l'amplitude maximale
     *
     */
    public CodeurNRZT(int nbEch, float ampMin, float ampMax) {
        super(nbEch, ampMin, ampMax);
    }

    /**
     * Permet de recevoir une information dans le codeur NRZT
     *
     * @param informationGeneree l'information reçue dans le codeur NRZT
     *
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    /**
     * Permet d'émettre l'information générée par le codeur NRZT
     * après l'avoir codée
     *
     */
    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        coder();
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationGeneree);
        }
    }

    /**
     * Permet de coder (transformer un booléen en float) l'information reçue dans
     * le codeur NRZT
     *
     */
    protected void coder() {
        informationGeneree = new Information<Float>();
        for (int i = 0; i < informationRecue.nbElements(); i++) {
            if (informationRecue.iemeElement(i)) {
                for (int j = 0; j < nbEch; j++) {
                    if (j < nbEch / 3) {
                        if (i != 0 && informationRecue.iemeElement(i - 1))
                            informationGeneree.add(ampMax);
                        else
                            informationGeneree.add((float) j / (nbEch / 3) * ampMax);
                    } else if (j < 2 * nbEch / 3) {
                        informationGeneree.add(ampMax);
                    } else {
                        if (i + 1 < informationRecue.nbElements() && informationRecue.iemeElement(i + 1))
                            informationGeneree.add(ampMax);
                        else
                            informationGeneree.add((float) (nbEch - j) / (nbEch / 3) * ampMax);
                    }
                }
            } else {
                for (int j = 0; j < nbEch; j++) {
                    if (j < nbEch / 3) {
                        if (i != 0 && !informationRecue.iemeElement(i - 1))
                            informationGeneree.add(ampMin);
                        else
                            informationGeneree.add((float) j / (nbEch / 3) * ampMin);
                    } else if (j < 2 * nbEch / 3) {
                        informationGeneree.add(ampMin);
                    } else {
                        if (i + 1 < informationRecue.nbElements() && !informationRecue.iemeElement(i + 1))
                            informationGeneree.add(ampMin);
                        else
                            informationGeneree.add((float) (nbEch - j) / (nbEch / 3) * ampMin);
                    }
                }
            }

        }
    }

    /**
     * geter de l'information reçue dans le codeur NRZT
     */
    public Information<Boolean> getInformationRecue() {
        return informationRecue;
    }

    /**
     * geter de l'information générée et émise par le codeur NRZT
     */
    public Information<Float> getInformationEmise() {
        return informationGeneree;
    }
}
