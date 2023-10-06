package transmetteurs;

import destinations.*;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe d'un composant ayant le comportement d'une sonde d'informations
 * dont les éléments sont de type T.
 * 
 */
public class TransmetteurFiltreAdapte extends Transmetteur<Float, Boolean> {
    protected int nbEch;
    protected float ampMin;
    protected float ampMax;
    protected float[] filtreAdapte0;
    protected float[] filtreAdapte1;
    
    /**
     * un constructeur factorisant les initialisations communes aux
     * réalisations de la classe abstraite Transmetteur
     */
    public TransmetteurFiltreAdapte(int nbEch, float ampMin, float ampMax) {
        super();
        this.nbEch = nbEch;
        this.ampMin = ampMin;
        this.ampMax = ampMax;
        genererFiltreAdapte();
    }

    protected void genererFiltreAdapte() {
        filtreAdapte0 = new float[nbEch];
        filtreAdapte1 = new float[nbEch];

        for (int i = 0; i < nbEch; i++) {
            filtreAdapte0[i] = ampMin;
            filtreAdapte1[i] = ampMax;
        }
    }

    protected float max(float[] tab) {
        float max = tab[0];
        
        for (int i = 1; i < tab.length; i++) {
            if (tab[i] > max)
                max = tab[i];
        }

        return max;
    }

    protected float[] convolution(float[] s, float[] g) {
        float[] output = new float[s.length + g.length - 1];
        
        for (int i = 0; i < output.length; i++) {
            output[i] = 0;
            for (int j = 0; j < s.length; j++) {
                if (i - j >= 0 && i - j < s.length) {
                    output[i] += s[i - j] * g[j];
                }
            }
        }
        
        return output;
    }

    protected void genererSignal() {
        informationEmise = new Information<Boolean>();
        float[] signal = new float[informationRecue.nbElements()];
        
        int i = 0;
        for (Float information : informationRecue) {
            signal[i] = information;
            i++;
        }

        for (i = 0; i < signal.length; i = i + nbEch) {
            float[] symbole = new float[nbEch];
            
            for (int j = 0; j < nbEch; j++) {
                symbole[j] = signal[i + j];
            }
            
            float[] conv0 = convolution(symbole, filtreAdapte0);
            float[] conv1 = convolution(symbole, filtreAdapte1);
            
            float max0 = max(conv0);
            float max1 = max(conv1);
            
            if (max0 > max1) {
                informationEmise.add(false);
            } else {
                informationEmise.add(true);
            }
        }
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

    public float getSNRReel() {
        return 1000f;
    }

    /**
     * émet l'information construite par le transmetteur
     */
    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");

        genererSignal();
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(this.informationEmise);
        }
    }
}
