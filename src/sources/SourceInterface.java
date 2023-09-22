package sources;

import information.*;
import destinations.DestinationInterface;

/**
 * Classe Abstraite d'un composant source d'informations dont les
 * éléments sont de type T.
 *
 * @param <T> Le type d'éléments que cette source émet.
 * @author prou
 */
public interface SourceInterface <T>  {

    /**
     * pour obtenir la dernière information émise par une source.
     * @return une information
     */
    public Information <T>  getInformationEmise();

    /**
     * pour connecter une destination à la source
     * @param destination  la destination à connecter
     */
    public void connecter (DestinationInterface <T> destination);

    /**
     * pour émettre l'information contenue dans une source
     */
    /**
     * pour émettre l'information contenue dans une source
     *
     * @throws InformationNonConformeException si l'information émise n'est pas conforme
     */
    public void emettre() throws InformationNonConformeException;

}
