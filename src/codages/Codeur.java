package codages;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.*;
import sources.SourceInterface;

public abstract class Codeur<R, E> implements DestinationInterface<R>, SourceInterface<E> {
    protected Information<R> informationRecue;
    protected Information<E> informationGeneree;

    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    protected int nbEch = 30;
    protected float ampMin = 0.0f;
    protected float ampMax = 1.0f;

    /**
     * Constructeur d'un modulateur
     *
     * @param nbEch  le nombre d'échantillons par symbole
     * @param ampMin l'amplitude minimale
     * @param ampMax l'amplitude maximale
     */
    public Codeur(int nbEch, float ampMin, float ampMax) {
        this.destinationsConnectees = new LinkedList<DestinationInterface<E>>();
        this.informationRecue = null;
        this.informationGeneree = null;
        this.nbEch = nbEch;
        this.ampMin = ampMin;
        this.ampMax = ampMax;
    }

    /**
     * connecte une destination au modulateur
     *
     * @param destination la destination à connecter
     */
    public void connecter(DestinationInterface<E> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * déconnecte une destination du modulateur
     *
     * @param destination la destination à déconnecter
     */
    public void deconnecter(DestinationInterface<E> destination) {
        destinationsConnectees.remove(destination);
    }

    /**
     * permet de recevoir et traiter une information (dans notre cas, la moduler)
     */
    public abstract void recevoir(Information<R> information) throws InformationNonConformeException;

    /**
     * permet d'émettre l'information générée par le modulateur (l'envoyer aux
     * destinations connectées)
     */
    public abstract void emettre() throws InformationNonConformeException;
}
