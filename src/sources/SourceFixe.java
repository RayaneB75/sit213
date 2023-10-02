package sources;

import information.Information;

/**
 * Classe qui définit une source fixe de type Boolean le contenu que
 * doit envoyer la source est défini par une chaine de caractères
 * donnée à la construction de la source.
 *
 * @see Source
 */
public class SourceFixe extends Source<Boolean> {

    /**
     * constructeur permettant de pour construire une source fixe de Booléens
     *
     * @param messageString le message à générer
     */
    public SourceFixe(String messageString) {
        super();
        int nbBitsMess = messageString.length();
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

    /**
     * Permet de récupérer l'information générée par la source
     * 
     * @return l'information générée par la source
     */
    public Information<Boolean> getInformationGeneree() {
        return this.informationGeneree;
    }

}
