package sources;

import java.util.Random;

public class SourceAleatoire extends Source<Boolean> {

    public SourceAleatoire(int nbBitsMess) {
        super();
        Random rd = new Random(); // creating Random object
        for (int i = 0; i < nbBitsMess; i++) {
            this.informationGeneree.add(rd.nextBoolean());
        }
        this.informationEmise = this.informationGeneree;
    }

    public SourceAleatoire(int taille, int seed) {
        super();
        Random rd = new Random(seed); // creating Random object
        for (int i = 0; i < taille; i++) {
            this.informationGeneree.add(rd.nextBoolean());
        }
        this.informationEmise = this.informationGeneree;
    }

}
