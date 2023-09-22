package transmetteurs;

import java.util.LinkedList;
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

    /** SNR par bit en db */
    protected float snrdB;
    /** nombre d'échantillons par symbole */
    protected int nbEch;
    /** variance du bruit */
    protected float variance;
    /** puissance moyenne du signal */
    protected float puissanceMoyenneSignal;
    /** linked list contenant le bruit emis pour chaque échantillon */
    protected LinkedList<Float> bruitEmis = new LinkedList<Float>();
    protected int seed = 0;

    /**
     * un constructeur factorisant les initialisations communes aux
     * réalisations de la classe abstraite Transmetteur
     */
    public TransmetteurGaussien(float snrdB, int nbEch) {
        super();
        this.snrdB = snrdB;
        this.nbEch = nbEch;
    }

    public TransmetteurGaussien(float snrdB, int nbEch, int seed) {
        super();
        this.snrdB = snrdB;
        this.nbEch = nbEch;
        this.seed = seed;
    }

    /**
     * Setter de l'information reçue
     * 
     * @param informationRecue
     */
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

    /**
     * calcule la puissance de bruit moyen à partir de l'information reçue
     * 
     * @return puissance de bruit moyen
     */
    public float calculerPuissanceDeBruitMoyen() {
        float somme = 0;
        for (float i : this.bruitEmis)
            somme += Math.pow(2, i);
        return (float) somme / this.bruitEmis.size();
    }

    /**
     * calcule la puissance moyenne du signal à partir de l'information reçue
     */
    private void calculerPuissanceMoyenneSignal() {
        float somme = 0;
        for (float i : this.informationRecue)
            somme += Math.pow(i, 2);
        this.puissanceMoyenneSignal = (float) somme / this.informationRecue.nbElements();
    }

    /**
     * calcule la variance du bruit à partir du SNR par bit en db
     */
    private void calculerVariance() {
        calculerPuissanceMoyenneSignal();
        this.variance = (this.puissanceMoyenneSignal * nbEch) / (2 * (float) Math.pow(10, snrdB / 10));
    }

    /**
     * génère le signal bruité à partir du signal reçu
     */
    private void genererSignalBruite() {
        this.informationEmise = new Information<Float>();
        calculerVariance();
        double bruit = 0f;
        Random random = null;
        if (this.seed != 0)
            random = new Random(this.seed);
        else
            random = new Random();
        for (float i : this.informationRecue) {
            bruit = random.nextGaussian() * Math.sqrt(variance);
            this.bruitEmis.add((float) (bruit));
            this.informationEmise.add(i + (float) (bruit));
            bruit = 0f;
        }
    }

    /**
     * émet l'information construite par le transmetteur à l'ensemble
     * des composants connectés à sa sortie.
     */
    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        genererSignalBruite();
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}
