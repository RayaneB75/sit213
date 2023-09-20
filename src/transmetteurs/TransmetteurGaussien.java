package transmetteurs;

import java.util.Random;

import destinations.*;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe d'un composant ayant le comportement d'un transmetteur parfait
 * d'informations dont les éléments sont de type Boolean.
 *
 * @see Transmetteur
 */
public class TransmetteurGaussien extends Transmetteur<Float, Float> {

    protected float snrdB;
    protected int nbEch;
    protected float variance;
    protected float puissanceMoyenneSignal;
    protected Information<Float> bruitBlancGaussien;

    public TransmetteurGaussien(float snrdB, int nbEch) {
        super();
        this.snrdB = snrdB;
        this.nbEch = nbEch;
    }

    public void setInformationRecue(Information<Float> informationRecue) {
        this.informationRecue = informationRecue;
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     */
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    private void calculerPuissanceMoyenneSignal() {
        float somme = 0;
        for (int i = 0; i < this.informationRecue.nbElements(); i++) {
            somme += Math.pow(2, this.informationRecue.iemeElement(i));
        }
        this.puissanceMoyenneSignal = (float) somme / this.informationRecue.nbElements();
    }

    private void calculerVariance() {
        calculerPuissanceMoyenneSignal();
        this.variance = (this.puissanceMoyenneSignal * nbEch) / (2 * (float) Math.pow(10, snrdB / 10));
    }

    private void genererBruitBlancGaussien() {
        this.bruitBlancGaussien = new Information<Float>();
        calculerVariance();
        Random random = new Random();
        for (int i = 0; i < this.informationRecue.nbElements(); i++) {
            this.bruitBlancGaussien.add((float) (random.nextGaussian() * Math.sqrt(variance)));
        }
    }

    private void genererSignalBruite() throws InformationNonConformeException {
        this.informationEmise = new Information<Float>();
        if (this.informationRecue == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        for (int i = 0; i < this.informationRecue.nbElements(); i++) {
            this.informationEmise.add(this.informationRecue.iemeElement(i) + this.bruitBlancGaussien.iemeElement(i));
        }
    }

    /**
     * émet l'information construite par le transmetteur à l'ensemble
     * des composants connectés à sa sortie.
     */
    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        genererBruitBlancGaussien();
        genererSignalBruite();
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}
