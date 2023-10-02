package codages;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.*;
import sources.SourceInterface;

/**
 * Classe abstraite d'un codeur.
 * Un codeur reçoit une information (de type R) et génère une information (de
 * type E).
 * Il est caractérisé par le nombre d'échantillons par symbole, l'amplitude
 * minimale et l'amplitude maximale.
 * Il est connecté à des composants destination.
 * Il possède une information reçue et une information générée.
 * Il possède une liste de composants destination connectés.
 * Il possède une méthode pour connecter une destination.
 * Il possède une méthode pour déconnecter une destination.
 * Il possède une méthode pour recevoir et traiter une information (dans notre
 * cas, la moduler).
 * Il possède une méthode pour émettre l'information générée par le codeur
 * (l'envoyer aux destinations connectées).
 * 
 * @param <R> Le type d'éléments en entrée du codeur.
 * @param <E> Le type d'éléments en sortie du codeur.
 */
public abstract class Codeur<R, E> implements DestinationInterface<R>, SourceInterface<E> {
    /**
     * L'information reçue par le codeur.
     */
    protected Information<R> informationRecue;

    /**
     * L'information générée par le codeur.
     */
    protected Information<E> informationGeneree;

    /**
     * La liste des composants destination connectés au codeur.
     */
    protected LinkedList<DestinationInterface<E>> destinationsConnectees;

    /**
     * Le nombre d'échantillons par symbole.
     */
    protected int nbEch = 30;

    /**
     * L'amplitude minimale.
     */
    protected float ampMin = 0.0f;

    /**
     * L'amplitude maximale.
     */
    protected float ampMax = 1.0f;

    public Codeur() {
        this.destinationsConnectees = new LinkedList<DestinationInterface<E>>();
        this.informationRecue = null;
        this.informationGeneree = null;
    }

    /**
     * Constructeur d'un codeur.
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
     * Connecte une destination au codeur.
     *
     * @param destination la destination à connecter
     */
    public void connecter(DestinationInterface<E> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * Déconnecte une destination du codeur.
     *
     * @param destination la destination à déconnecter
     */
    public void deconnecter(DestinationInterface<E> destination) {
        destinationsConnectees.remove(destination);
    }

    /**
     * Permet de recevoir et traiter une information (dans notre cas, la moduler).
     */
    public abstract void recevoir(Information<R> information) throws InformationNonConformeException;

    /**
     * Permet d'émettre l'information générée par le codeur (l'envoyer aux
     * destinations connectées).
     */
    public abstract void emettre() throws InformationNonConformeException;
}
