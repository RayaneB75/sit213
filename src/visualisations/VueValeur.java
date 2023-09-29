package visualisations;

/** 
 * @author B. Prou
 * Updated by E. Cousin - 2021
 *
 */

import javax.swing.*;

/**
 * La classe VueValeur représente une fenêtre pour afficher une valeur.
 * Cette classe peut être utilisée pour afficher des valeurs booléennes ou des
 * valeurs flottantes.
 * 
 */
public class VueValeur extends Vue {

    private static final long serialVersionUID = 1917L;

    /**
     * jLabel correspondant à la valeur à afficher
     */
    private JLabel jLabel;

    /**
     * Constructeur pour afficher une valeur.
     *
     * @param valeur La valeur à afficher.
     * @param nom    Le nom de la fenêtre.
     */
    public VueValeur(Object valeur, String nom) {

        super(nom);
        String s = " " + valeur;
        jLabel = new JLabel(s);

        int xPosition = Vue.getXPosition();
        int yPosition = Vue.getYPosition();
        setLocation(xPosition, yPosition);

        add(jLabel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 100);
        setVisible(true);
        repaint();
    }

}
