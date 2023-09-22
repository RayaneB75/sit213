package information;

import java.util.*;

/**
 * Classe représentant une information contenant des éléments de type T.
 *
 * @param <T> Le type d'éléments contenus dans cette information.
 * @author prou
 */
public class Information<T> implements Iterable<T> {

    private LinkedList<T> content;

    /**
     * Constructeur par défaut pour créer une information vide.
     */
    public Information() {
        this.content = new LinkedList<T>();
    }

    /**
     * Constructeur pour créer une information à partir d'un tableau de T.
     *
     * @param content le tableau d'éléments pour initialiser l'information.
     */
    public Information(T[] content) {
        this.content = new LinkedList<T>();
        for (int i = 0; i < content.length; i++) {
            this.content.addLast(content[i]);
        }
    }

    /**
     * Retourne le nombre d'éléments dans cette information.
     *
     * @return le nombre d'éléments de l'information.
     */
    public int nbElements() {
        return this.content.size();
    }

    /**
     * Renvoie le ième élément de cette information.
     *
     * @param i l'indice de l'élément à récupérer.
     * @return le ième élément de l'information.
     */
    public T iemeElement(int i) {
        return this.content.get(i);
    }

    /**
     * Modifie le ième élément de cette information.
     *
     * @param i l'indice de l'élément à modifier.
     * @param v la nouvelle valeur de l'élément.
     */
    public void setIemeElement(int i, T v) {
        this.content.set(i, v);
    }

    /**
     * Ajoute un élément à la fin de cette information.
     *
     * @param valeur l'élément à ajouter.
     */
    public void add(T valeur) {
        this.content.add(valeur);
    }

    /**
     * Compare cette information avec une autre information.
     *
     * @param o l'information avec laquelle se comparer.
     * @return "true" si les 2 informations contiennent les mêmes
     *         éléments aux mêmes places; "false" dans les autres cas.
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof Information))
            return false;
        Information<T> information = (Information<T>) o;
        if (this.nbElements() != information.nbElements())
            return false;
        for (int i = 0; i < this.nbElements(); i++) {
            if (!this.iemeElement(i).equals(information.iemeElement(i)))
                return false;
        }
        return true;
    }

    /**
     * Renvoie une représentation textuelle de cette information.
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < this.nbElements(); i++) {
            s += " " + this.iemeElement(i);
        }
        return s;
    }

    /**
     * Permet l'utilisation du "for each" avec cette information.
     */
    public Iterator<T> iterator() {
        return content.iterator();
    }
}
