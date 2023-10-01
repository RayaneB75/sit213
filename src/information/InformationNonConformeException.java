package information;

/**
 * Classe d'exception levée lorsque l'information reçue n'est pas conforme
 * 
 */
public class InformationNonConformeException extends Exception {

    /**
     * Serial UID (pour Serializable)
     * 
     */
    private static final long serialVersionUID = 1917L;

    /**
     * Exception levée lorsque l'information reçue n'est pas conforme (constructeur)
     */
    public InformationNonConformeException() {
        super();
    }

    /**
     * Exception levée lorsque l'information reçue n'est pas conforme
     * 
     * @param motif le motif de l'exception
     */
    public InformationNonConformeException(String motif) {
        super(motif);
    }
}
