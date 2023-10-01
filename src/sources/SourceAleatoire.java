package sources;

import information.Information;
import java.util.Random;

/**
 * Classe permettant de générer de l'information de type Boolean
 * de manière aléatoire
 *
 * @see Source
 */
public class SourceAleatoire extends Source<Boolean> {

    /**
     * un constructeur permettant de générer une information binaire
     * aléatoire, d'une certaine longueur de bits
     *
     * @param nbBitsMess le nombre de bits du message généré
     */
    public SourceAleatoire(int nbBitsMess) {
        super();
        this.informationGeneree = new Information<Boolean>();
        Random rd = new Random(System.currentTimeMillis()); // creating Random object
        for (int i = 0; i < nbBitsMess; i++) {
            this.informationGeneree.add(rd.nextBoolean());
        }
        this.informationEmise = this.informationGeneree;
    }

    /**
     * un constructeur permettant de générer une information binaire
     * aléatoire, en fixant le seed du générateur aléatoire
     *
     * @param taille le nombre de bits du message généré
     * @param seed   la graine du générateur aléatoire
     */
    public SourceAleatoire(int taille, int seed) {
        super();
        this.informationGeneree = new Information<Boolean>();
        Random rd = new Random(seed); // creating Random object
        for (int i = 0; i < taille; i++) {
            this.informationGeneree.add(rd.nextBoolean());
        }
        this.informationEmise = this.informationGeneree;
    }

    /**
     * Permet de récupérer l'information générée par la source
     * 
     * @return l'information générée par la source
     */
    public Information<Boolean> getInformationGeneree() {
        return this.informationGeneree;
    }

}
