package simulateur;

/**
 * Exception personnalisée utilisée pour gérer les erreurs liées aux arguments.
 */
public class ArgumentsException extends Exception {

    private static final long serialVersionUID = 1789L;

    /**
     * Constructeur de la classe ArgumentsException.
     *
     * @param s La description de l'erreur.
     */
    public ArgumentsException(String s) {
        super(s);
    }
}
