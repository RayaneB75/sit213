package sources;

import information.Information;

public class SourceFixe extends Source<Boolean> {

    public SourceFixe() {
        super();
        informationGeneree = new Information<Boolean>();
        informationGeneree.add(true);
        informationGeneree.add(false);
        informationGeneree.add(true);
        informationGeneree.add(true);
        informationGeneree.add(false);
        informationGeneree.add(true);
        informationEmise = informationGeneree;
    }

}
