package simulateur;

import destinations.Destination;
import sources.Source;
import sources.SourceAleatoire;
import sources.SourceFixe;
import destinations.DestinationFinale;
import transmetteurs.Transmetteur;
import transmetteurs.TransmetteurParfait;
import transmetteurs.TransmetteurGaussien;
import transmetteurs.TransmetteurMultiTrajets;
import visualisations.SondeAnalogique;
import visualisations.SondeLogique;

import java.util.*;

import codages.Codeur;
import codages.Decodeur;
import codages.DecodeurNRZ;
import codages.CodeurRZ;
import codages.CodeurNRZ;
import codages.CodeurNRZT;
import codages.CodeurCanal;
import codages.DecodeurCanal;

/**
 * La classe Simulateur permet de construire et simuler une chaîne de
 * transmission composée d'une Source, d'un nombre variable de
 * Transmetteur(s) et d'une Destination.
 *
 * @author cousin
 * @author prou
 *
 */
public class Simulateur {

    /** indique si le Simulateur utilise des sondes d'affichage */
    private boolean affichage = false;

    /**
     * indique si le Simulateur utilise un message généré de manière aléatoire
     * (message imposé sinon)
     */
    private boolean messageAleatoire = true;

    /**
     * indique si le Simulateur utilise un germe pour initialiser les générateurs
     * aléatoires
     */
    private boolean aleatoireAvecGerme = false;

    /** la valeur de la semence utilisée pour les générateurs aléatoires */
    private Integer seed = null; // pas de semence par défaut

    /**
     * la longueur du message aléatoire à transmettre si un message n'est pas imposé
     */
    private int nbBitsMess = 100;

    /** la chaîne de caractères correspondant à m dans l'argument -mess m */
    private String messageString = "100";

    /** le composant Source de la chaine de transmission */
    private Source<Boolean> source = null;

    /** le composant Transmetteur parfait logique de la chaine de transmission */
    private Transmetteur<Boolean, Boolean> transmetteurLogique = null;
    private Transmetteur<Float, Float> transmetteurAnalogique = null;
    private Transmetteur<Float, Float> transmetteurMultiTrajets = null;

    /** le composant Destination de la chaine de transmission */
    private Destination<Boolean> destination = null;

    /** la forme du signal */
    private String form = "RZ";

    /** le codeur */
    private Codeur<Boolean, Float> codeur = null;

    /** le decodeur */
    private Codeur<Float, Boolean> decodeur = null;

    /** le codeur du canal et son décodeur */
    private Codeur<Boolean, Boolean> codeurCanal = null;
    private Codeur<Boolean, Boolean> decodeurCanal = null;

    /** l'amplitude minimale */
    private float amplitudeMin = 0.0f;

    /** l'amplitude maximale */
    private float amplitudeMax = 1.0f;

    /** le nombre d'échantillons par bit */
    private int nbEch = 30;

    /** le rapport signal sur bruit par bit <em> Eb/N0 </em> */
    private float snrpb = -1000;

    /** décalage temporel (en nombre d’échantillons) */
    private float[][] ti = null;

    /** activation du codage canal ou non */
    private boolean codage = false;

    /**
     * Le constructeur de Simulateur construit une chaîne de
     * transmission composée d'une Source 'Boolean', d'une Destination
     * 'Boolean' et de Transmetteur(s) [voir la méthode
     * analyseArguments]... <br>
     * Les différents composants de la chaîne de transmission (Source,
     * Transmetteur(s), Destination, Sonde(s) de visualisation) sont créés et
     * connectés.
     *
     * @param args le tableau des différents arguments.
     *
     * @throws ArgumentsException si un des arguments est incorrect
     *
     */
    public Simulateur(String[] args) throws ArgumentsException {
        // analyser et récupérer les arguments
        analyseArguments(args);

        if (messageAleatoire) {
            if (aleatoireAvecGerme) {
                source = new SourceAleatoire(nbBitsMess, seed);
            } else {
                source = new SourceAleatoire(nbBitsMess);
            }
        } else {
            source = new SourceFixe(messageString);
        }
        destination = new DestinationFinale();
        if (codage) {
            codeurCanal = new CodeurCanal();
            source.connecter(codeurCanal);
            decodeurCanal = new DecodeurCanal();
        }

        if (form != "") {
            switch (form) {
                case "NRZT":
                    codeur = new CodeurNRZT(nbEch, amplitudeMin, amplitudeMax);
                    decodeur = new DecodeurNRZ(nbEch, amplitudeMin, amplitudeMax);
                    break;
                case "NRZ":
                    codeur = new CodeurNRZ(nbEch, amplitudeMin, amplitudeMax);
                    decodeur = new DecodeurNRZ(nbEch, amplitudeMin, amplitudeMax);
                    break;
                default:
                    codeur = new CodeurRZ(nbEch, amplitudeMin, amplitudeMax);
                    decodeur = new Decodeur(nbEch, amplitudeMin, amplitudeMax);
                    break;
            }
            // On vérifie que le paramètre snrpb est bien défini
            if (snrpb != -1000)
                if (aleatoireAvecGerme)
                    transmetteurAnalogique = new TransmetteurGaussien(snrpb, nbEch, seed);
                else
                    transmetteurAnalogique = new TransmetteurGaussien(snrpb, nbEch);
            else
                transmetteurAnalogique = new TransmetteurParfait<Float>();
            if (codage) {
                codeurCanal.connecter(codeur);
            } else
                source.connecter(codeur);
            if (ti != null) {
                transmetteurMultiTrajets = new TransmetteurMultiTrajets(ti);
                codeur.connecter(transmetteurMultiTrajets);
                transmetteurMultiTrajets.connecter(transmetteurAnalogique);
            } else {
                codeur.connecter(transmetteurAnalogique);
            }
            transmetteurAnalogique.connecter(decodeur);
            if (codage) {
                decodeur.connecter(decodeurCanal);
                decodeurCanal.connecter(destination);
            } else
                decodeur.connecter(destination);
        } else {
            transmetteurLogique = new TransmetteurParfait<Boolean>();
            if (codage) {
                source.connecter(codeurCanal);
                codeurCanal.connecter(transmetteurLogique);
            } else
                source.connecter(transmetteurLogique);
            transmetteurLogique.connecter(destination);
        }
        if (affichage) {
            SondeLogique sondeLogS = new SondeLogique("Source Logique", 30);
            SondeLogique sondeLogD = new SondeLogique("Destination Logique", 30);
            SondeAnalogique sondeAnaS = null;
            SondeAnalogique sondeAnaD = null;

            // Dans tous les cas on connecte une sonde logique à la source logique
            source.connecter(sondeLogS);

            if (form != "") {
                // Si on utilise une forme de signal, on connecte une sonde analogique à la
                // sortie du modulateur (float) et une sonde logique à la sortie du démodulateur
                // (bool)
                sondeAnaS = new SondeAnalogique("Source analogique");
                sondeAnaD = new SondeAnalogique("Sortie transmetteur analogique");
                codeur.connecter(sondeAnaS);
                transmetteurAnalogique.connecter(sondeAnaD);
                if (codage)
                    decodeurCanal.connecter(sondeLogD);
                else
                    decodeur.connecter(sondeLogD);
            } else {
                // Sinon on connecte uniquement une sonde logique à la sortie du transmetteur
                // logique
                transmetteurLogique.connecter(sondeLogD);
            }

        }
    }

    /**
     * La méthode analyseArguments extrait d'un tableau de chaînes de
     * caractères les différentes options de la simulation. <br>
     * Elle met
     * à jour les attributs correspondants du Simulateur.
     *
     * @param args le tableau des différents arguments.
     *             <br>
     *             <br>
     *             Les arguments autorisés sont :
     *             <br>
     *             <dl>
     *             <dt>-mess m</dt>
     *             <dd>m (String) constitué de 7 ou plus digits à 0 | 1, le message
     *             à transmettre</dd>
     *             <dt>-mess m</dt>
     *             <dd>m (int) constitué de 1 à 6 digits, le nombre de bits du
     *             message "aléatoire" à transmettre</dd>
     *             <dt>-s</dt>
     *             <dd>pour demander l'utilisation des sondes d'affichage</dd>
     *             <dt>-seed v</dt>
     *             <dd>v (int) d'initialisation pour les générateurs aléatoires</dd>
     *             <dt>-form f</dt>
     *             <dd>f (String) NRZT, NRZ ou RZ</dd>
     *             <dt>-ampl min max</dt>
     *             <dd>Amplitude maximum et minimum a utiliser</dd>
     *             <dt>-ti dt ar</dt>
     *             <dd>dt (int) décalage temporel en nombre d'échantillons, ar
     *             (float) amplitude relative du trajet</dd>
     *             </dl>
     *
     * @throws ArgumentsException si un des arguments est incorrect.
     *
     */
    public void analyseArguments(String[] args) throws ArgumentsException {

        for (int i = 0; i < args.length; i++) { // traiter les arguments 1 par 1

            if (args[i].matches("-s")) {
                affichage = true;
            }

            else if (args[i].matches("-seed")) {
                messageAleatoire = true;
                aleatoireAvecGerme = true;
                i++;
                // traiter la valeur associee
                try {
                    seed = Integer.valueOf(args[i]);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur du parametre -seed invalide :" + args[i]);
                }
            }

            else if (args[i].matches("-mess")) {
                i++;
                // traiter la valeur associee
                messageString = args[i];
                if (args[i].matches("[0,1]{7,}")) { // au moins 7 digits
                    messageAleatoire = false;
                } else if (args[i].matches("[0-9]{1,6}")) { // de 1 à 6 chiffres
                    messageAleatoire = true;
                    nbBitsMess = Integer.valueOf(args[i]);
                    if (nbBitsMess < 1)
                        throw new ArgumentsException("Valeur du parametre -mess invalide : " + nbBitsMess);
                } else
                    throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
            } else if (args[i].matches("-form")) {
                i++;
                if (args[i].matches("(NRZT)|(NRZ)|(RZ)")) {
                    form = args[i];
                } else
                    throw new ArgumentsException("Forme invalide :" + args[i]);
            } else if (args[i].matches("-ampl")) {
                i++;
                try {
                    amplitudeMin = Float.valueOf(args[i]);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur min du parametre -ampl invalide :" + args[i]);
                }
                i++;
                try {
                    amplitudeMax = Float.valueOf(args[i]);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur max du parametre -ampl invalide :" + args[i]);
                }
            } else if (args[i].matches("-nbEch")) {
                i++;
                try {
                    nbEch = Integer.valueOf(args[i]);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur du parametre -nbEch invalide :" + args[i]);
                }
            } else if (args[i].matches("-snrpb")) {
                i++;
                try {
                    snrpb = Float.valueOf(args[i]);
                } catch (Exception e) {
                    throw new ArgumentsException("Valeur du parametre -snrpb invalide :" + args[i]);
                }
            } else if (args[i].matches("-ti")) {
                int cpt = 0;
                while (args.length > i + 1 && args[i + 1].matches("[0-9]{1,10}") && cpt < 5) {
                    i += 2;
                    cpt++;
                }
                i = i - (cpt * 2);
                ti = new float[cpt][2];
                for (int j = 0; j < cpt; j++) {
                    i++;
                    try {
                        ti[j][0] = Integer.valueOf(args[i]);
                    } catch (Exception e) {
                        throw new ArgumentsException("Valeur du parametre -ti invalide :" + args[i]);
                    }
                    i++;
                    try {
                        if (Float.valueOf(args[i]) < 0 || Float.valueOf(args[i]) > 1)
                            throw new ArgumentsException("Valeur du parametre -ti invalide :" + args[i]);
                        ti[j][1] = Float.valueOf(args[i]);
                    } catch (Exception e) {
                        throw new ArgumentsException("Valeur du parametre -ti invalide :" + args[i]);
                    }
                }
            } else if (args[i].matches("-codeur")) {
                this.codage = true;
            } else
                throw new ArgumentsException("Option invalide :" + args[i]);
        }

    }

    /**
     * La méthode execute effectue un envoi de message par la source
     * de la chaîne de transmission du Simulateur.
     *
     * @throws Exception si un problème survient lors de l'exécution
     *
     */
    public void execute() throws Exception {

        source.emettre();

    }

    /**
     * La méthode qui calcule le taux d'erreur binaire en comparant
     * les bits du message émis avec ceux du message reçu.
     *
     * @return La valeur du Taux d'Erreur Binaire.
     */
    public float calculTauxErreurBinaire() {

        Iterator<Boolean> src = source.getInformationEmise().iterator();
        Iterator<Boolean> dest = destination.getInformationRecue().iterator();
        int nbBits = 0;
        int error = 0;

        while (src.hasNext() && dest.hasNext()) {
            if (src.next() != dest.next()) {
                error++;
            }
            nbBits++;
        }

        return (float) error / nbBits;
    }

    /**
     * La fonction main instancie un Simulateur à l'aide des
     * arguments paramètres et affiche le résultat de l'exécution
     * d'une transmission.
     *
     * @param args les différents arguments qui serviront à l'instanciation du
     *             Simulateur.
     */
    public static void main(String[] args) {

        Simulateur simulateur = null;

        try {
            simulateur = new Simulateur(args);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }

        try {
            simulateur.execute();
            String s = "java  Simulateur  ";
            for (int i = 0; i < args.length; i++) { // copier tous les paramètres de simulation
                s += args[i] + "  ";
            }
            Float teb = simulateur.calculTauxErreurBinaire();
            if (teb < 0.0f || teb.isNaN()) {
                throw new Exception("TEB incorrect (" + teb + ") : TEB < 0 ou NaN");
            }
            System.out.println(s + "  =>   TEB : " + teb);
            if (simulateur.snrpb != -1000) {
                System.out.println(
                        "Eb/N0 demandé vs réel : " + simulateur.snrpb + " / "
                                + simulateur.transmetteurAnalogique.getSNRReel());
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(-2);
        }
    }
}
