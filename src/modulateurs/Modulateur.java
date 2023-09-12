package modulateurs;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.*;
import sources.SourceInterface;

public abstract class Modulateur<R, E> implements DestinationInterface<R>, SourceInterface<E> {
    protected Information<R> informationRecue;
    protected Information<E> informationGeneree;

    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    protected int nbEch = 30;
    protected float ampMin = 0.0f;
    protected float ampMax = 1.0f;

    public Modulateur() {
        this.informationRecue = null;
        this.informationGeneree = null;
        this.destinationsConnectees = new LinkedList<DestinationInterface<E>>();
    }

    public Modulateur(int nbEch, float ampMin, float ampMax) {
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

    public abstract void recevoir(Information<R> information);

    public abstract void emettre() throws InformationNonConformeException;
}
