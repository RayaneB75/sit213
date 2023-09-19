package transmetteurs;

import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruite<T> extends Transmetteur<T, T> {
    private float snrdb;

    /**
     * un constructeur factorisant les initialisations communes aux
     * réalisations de la classe abstraite Transmetteur
     */
    public TransmetteurBruite(float snrdb) {
        super();
        this.snrdb = snrdb;
    }

    public void setInformationRecue(Information<T> informationRecue) {
        this.informationRecue = informationRecue;
    }

    /**
     * reçoit une information. Cette méthode, en fin d'exécution, appelle la
     * méthode emettre.
     *
     * @param information l'information reçue
     */
    public void recevoir(Information<T> information) throws InformationNonConformeException {
        if (information == null)
            throw new InformationNonConformeException("Erreur : Information non conforme");
        this.informationRecue = information;
        this.emettre();
    }

    /**
     * émet l'information construite par le transmetteur à l'ensemble
     * des composants connectés à sa sortie.
     */
    public void emettre() throws InformationNonConformeException {
    }

    /**
     * retourne le rapport signal a bruit en décibel (originelement)
     * 
     * @return
     */
    public float getSNRdb() {
        return snrdb;
    }

}
