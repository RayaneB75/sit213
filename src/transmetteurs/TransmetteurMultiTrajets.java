package transmetteurs;

import destinations.*;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurMultiTrajets extends Transmetteur<Float, Float> {

    private Information<Float> informationRecue;
    private Information<Float> informationEmise;
    private Float[] informationGeneree;
    /** Les délais et amplitudes des trajets multiples */
    private float[][] ti;
    /** Décalage maximum donnant la taille du tableau informationGeneree */
    private int maxDT;

    /**
     * un constructeur factorisant les initialisations communes aux
     * réalisations de la classe abstraite Transmetteur
     * 
     * @param dt Décalage temporel des trajets multiples
     * @param ar Amplitude (entre 0 et 1) appliquée aux trajets multiples
     */
    public TransmetteurMultiTrajets(float[][] ti) {
        super();
        this.ti = ti;
        this.maxDT = 0;
        for (float[] trajet : ti) {
            if (trajet[0] > this.maxDT) {
                this.maxDT = (int) trajet[0];
            }
        }
    }

    /**
     * Retourne 0 dans cette classe puisqu'il n'y a pas de bruit
     * 
     * @return la puissance de bruit moyen (toujours 0 dans cette classe)
     */
    public float calculerPuissanceDeBruitMoyen() {
        return 0.0f;
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     */
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        this.informationRecue = information;
        emettre();
    }

    /**
     * Ajoute un décalage temporel (dt) pour simuler les trajets multiples
     * avec une amplitude (ar) pour simuler l'atténuation du signal
     */
    private void genererSignalRetarde() {
        this.informationGeneree = new Float[this.informationRecue.nbElements() + maxDT];
        for (int i = 0; i < maxDT; i++) {
            this.informationGeneree[i] = 0.0f;
        }

        for (float[] trajet : ti) {
            int dt = (int) trajet[0];
            float ar = trajet[1];
            int cpt = dt;
            for (float i : informationRecue) {
                this.informationGeneree[cpt] = i * ar;
                cpt++;
            }
        }
    }

    private void genererSignalTransmis() {

        int cpt = 0;
        for (Float i : informationRecue) {
            informationGeneree[cpt] += i;
            cpt++;
        }

        this.informationEmise = new Information<Float>();
        for (int i = 0; i < informationGeneree.length; i++) {
            this.informationEmise.add(informationGeneree[i]);
        }

    }

    /**
     * émet l'information construite par le transmetteur
     */
    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        else {
            genererSignalRetarde();
            genererSignalTransmis();
            for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
                destinationConnectee.recevoir(this.informationEmise);
            }
        }
    }
}
