package codages;

import java.util.*;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe d'un codeur NRZT (Non Return to Zero T).
 * 
 * Un codeur NRZ reçoit une information (de type booléen) et génère une
 * information (de type float).
 * Cette information de type float correspond à un signal NRZT, c'est-à-dire un
 * signal dont les valeurs sont comprises entre ampMin et ampMax et dont la
 * valeur est constante pendant nbEch/3 par symbole et dont la valeur augmente,
 * se stabilise, puis diminue de nbEch/3 dans les trois cas pour chaque
 * échantillon.
 * 
 */
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
     * @param information l'information reçue dans le codeur NRZT
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
     * Permet de générer un symbole dans le codeur NRZT
     *
     * @param precedent le booléen précédent
     * @param current   le booléen courant
     * @param next      le booléen suivant
     *
     */
    protected void genererSymbole(Boolean precedent, Boolean current, Boolean next) {
        int delta = nbEch / 3;
        if (nbEch % 3 != 0) {
            if (current) {
                for (int j = 0; j < delta; j++) {
                    if (precedent != null && precedent)
                        informationGeneree.add(ampMax);
                    else
                        informationGeneree.add((float) j / delta * ampMax);
                }
                for (int j = 0; j <= delta + 1; j++) {
                    informationGeneree.add(ampMax);
                }
                for (int j = 0; j < delta; j++) {
                    if (next)
                        informationGeneree.add(ampMax);
                    else
                        informationGeneree.add((float) (delta - j) / delta * ampMax);
                }
            } else {
                for (int j = 0; j < delta; j++) {
                    if (precedent != null && !precedent)
                        informationGeneree.add(ampMin);
                    else
                        informationGeneree.add((float) j / delta * ampMin);
                }
                for (int j = 0; j <= delta + 1; j++) {
                    informationGeneree.add(ampMin);
                }
                for (int j = 0; j < delta; j++) {
                    if (!next)
                        informationGeneree.add(ampMin);
                    else
                        informationGeneree.add((float) (delta - j) / delta * ampMin);
                }
            }
            return;
        }
        if (current) {
            for (int j = 0; j < delta; j++) {
                if (precedent != null && precedent)
                    informationGeneree.add(ampMax);
                else
                    informationGeneree.add((float) j / delta * ampMax);
            }
            for (int j = 0; j < delta; j++) {
                informationGeneree.add(ampMax);
            }
            for (int j = 0; j < delta; j++) {
                if (next)
                    informationGeneree.add(ampMax);
                else
                    informationGeneree.add((float) (delta - j) / delta * ampMax);
            }
        } else {
            for (int j = 0; j < delta; j++) {
                if (precedent != null && !precedent)
                    informationGeneree.add(ampMin);
                else
                    informationGeneree.add((float) j / delta * ampMin);
            }
            for (int j = 0; j < delta; j++) {
                informationGeneree.add(ampMin);
            }
            for (int j = 0; j < delta; j++) {
                if (!next)
                    informationGeneree.add(ampMin);
                else
                    informationGeneree.add((float) (delta - j) / delta * ampMin);
            }
        }
    }

    /**
     * Permet de coder (transformer un booléen en float) l'information reçue dans
     * le codeur NRZT
     *
     */
    protected void coder() {
        informationGeneree = new Information<Float>();

        Iterator<Boolean> it = informationRecue.iterator();
        Iterator<Boolean> itDecale = informationRecue.iterator();

        Boolean precedent = null;
        Boolean current = null;
        Boolean next = null;

        if (itDecale.hasNext())
            itDecale.next();

        while (it.hasNext()) {
            current = it.next();
            if (itDecale.hasNext())
                next = itDecale.next();
            genererSymbole(precedent, current, next);
            precedent = current;
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
