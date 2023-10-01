package visualisations;

/**
 * @author B. Prou
 * Updated by E. Cousin - 2021
 *
 */

import java.util.*;
import javax.swing.*;

/**
 * La classe Vue représente une fenêtre pour afficher une valeur.
 * Cette classe peut être utilisée pour afficher des valeurs booléennes ou des
 * valeurs flottantes.
 * 
 */
public class Vue extends JFrame {
    /**
     * Liste des vues créées
     */
    private static LinkedList<Vue> lesVues = new LinkedList<Vue>();
    /**
     * Numéro de version de la classe (pour la sérialisation).
     */
    private static final long serialVersionUID = 1917L;
    /**
     * Position en x de la prochaine vue
     */
    protected static int xPosition = 0;
    /**
     * Position en y de la prochaine vue
     */
    protected static int yPosition = 0;
    /**
     * Décalage en y entre les vues
     */
    private static int yDecalage = 200;

    /**
     * Permet de récupérer la position en x de la prochaine vue
     *
     * @return la position en x de la prochaine vue
     */
    public static int getXPosition() {
        xPosition += 0;
        return xPosition - 0;
    }

    /**
     * Permet de récupérer la position en y de la prochaine vue
     *
     * @return la position en y de la prochaine vue
     */
    public static int getYPosition() {
        yPosition += yDecalage;
        return yPosition - yDecalage;
    }

    /**
     * Constructeur pour afficher une valeur.
     *
     * @param nom Le nom de la fenêtre.
     */
    public Vue(String nom) {
        super(nom);
        lesVues.add(this);
    }

    /**
     * Permet de réinitialiser la position en y de la prochaine vue
     *
     */
    public static void resetPosition() {
        yPosition = 0;
    }

    /**
     * Permet de modifier la position en x de la prochaine vue
     *
     * @param x la nouvelle position en x de la prochaine vue
     */
    public static void setXPosition(int x) {
        xPosition = x;
    }

    /**
     * Permet de modifier la position en y de la prochaine vue
     *
     */
    public static void kill() {
        for (Vue v : lesVues)
            v.dispose();
        lesVues.clear();
        resetPosition();
    }

}
