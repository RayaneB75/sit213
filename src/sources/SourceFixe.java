package sources;

import information.Information;

public class SourceFixe extends Source<Boolean> {

    public SourceFixe(int nbBitsMess, String messageString) {
        super();
        informationGeneree = new Information<Boolean>();
        for (int i = 0; i < nbBitsMess; i++) {
            if (messageString.charAt(i) == '0') {
                informationGeneree.add(false);
            } else {
                informationGeneree.add(true);
            }
        }
        informationEmise = informationGeneree;
    }

}
