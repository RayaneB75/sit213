package tests;

import static org.hamcrest.core.Is.is;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import destinations.DestinationFinale;
import information.Information;
import codages.CodeurRZ;
import codages.Decodeur;
import sources.SourceFixe;
import transmetteurs.TransmetteurParfait;

/**
 * Cette classe représente un ensemble de tests JUnit pour la classe CodeurRZ.
 * Elle vérifie le comportement de la classe CodeurRZ en générant des symboles
 * à partir de séquences de bits fixes et en effectuant différentes
 * vérifications sur la taille de l'information générée et la cohérence entre
 * deux instances de la classe.
 * 
 */
public class CodeurRZTest {
    /**
     * Permet de gérer les erreurs récoltées pendant les tests
     */
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    /**
     * Attribut contenant l'information attendue après décodage
     */
    private static Information<Boolean> content = new Information<>(
            new Boolean[] { false, true, true, false, false, true });
    /**
     * Attributs utilisés par les tests
     */
    private SourceFixe source = null;
    /**
     * Codeur RZ utilisé par les tests
     */
    private CodeurRZ codeur = null;
    /**
     * Decodeur utilisé par les tests
     */
    private Decodeur decodeur = null;
    /**
     * Transmetteur parfait utilisé par les tests
     */
    private TransmetteurParfait<Float> transmetteurParfait = null;
    /**
     * Destination finale utilisée par les tests
     */
    private DestinationFinale destinationFinale = null;

    /**
     * Constructeur de la classe de test
     * 
     */
    public CodeurRZTest() {
    }

    /**
     * Instanciation des attributs utilisés par les tests
     * - Une source fixe avec un message de 6 bits "011001"
     * - Un codeur RZ de 30 échantillons par symboles (180 échantillons en tout)
     * 
     * @throws Exception si une erreur survient pendant l'instanciation
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     * Instanciation des attributs utilisés par les tests
     * - Une source fixe avec un message de 6 bits "011001"
     * - Un codeur RZ de 30 échantillons par symboles (180 échantillons en tout)
     * 
     * @throws Exception si une erreur survient pendant l'instanciation
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Instanciation des attributs utilisés par les tests
     * - Une source fixe avec un message de 6 bits "011001"
     * - Un codeur RZ de 30 échantillons par symboles (180 échantillons en tout)
     * avec une plage d'amplitude allant de 0 à 1
     * - Un transmetteur parfait (TEB attendu = 0)
     * - Un decodeur de même caractérisitiques que le codeur
     * - Une destination finale booléenne
     */
    @Before
    public void setUp() {
        source = new SourceFixe(6, "011001");
        codeur = new CodeurRZ(30, 0f, 1f);
        transmetteurParfait = new TransmetteurParfait<Float>();
        decodeur = new Decodeur(30, 0f, 1f);
        destinationFinale = new DestinationFinale();
        source.connecter(codeur);
    }

    /**
     * On ne fait rien ici car on ne fait pas de modifications sur les attributs
     * instanciés dans setUp()
     * 
     */
    @After
    public void tearDown() {
    }

    /**
     * Ici on testes le codage avec 3 vérifications :
     * - L'information codée contient bien 180 échantillons
     * - Le 36eme élément est bien à 0f
     * - Le 169eme élément est bien à 1f
     */
    @Test
    public void testCodage() {

        try {
            source.emettre();
        } catch (Exception e) {
            System.out.println(e);
        }
        collector.checkThat("Vérification de l'information reçue", codeur.getInformationRecue().nbElements(),
                is(6));
        collector.checkThat("Vérification de l'information modulée",
                codeur.getInformationEmise().nbElements(), is(180));
        collector.checkThat("Vérification d'un premier echantillon spécifique",
                codeur.getInformationEmise().iemeElement(36), is(0.0f));
        collector.checkThat("Vérification d'un deuxième echantillon spécifique",
                codeur.getInformationEmise().iemeElement(169), is(1.0f));

    }

    /**
     * Ici on teste que le decodage (après codage et transmission) s'est
     * bien déroulée, avec 2 vérifications :
     * - Le nombre d'élément dans l'information finale est bien de 6 bits
     * - L'information reçue est bien "011001"
     */
    @Test
    public void testDecodage() {
        codeur.connecter(transmetteurParfait);
        transmetteurParfait.connecter(decodeur);
        decodeur.connecter(destinationFinale);

        try {
            source.emettre();
            codeur.emettre();
            transmetteurParfait.emettre();
            decodeur.emettre();
        } catch (Exception e) {
            System.out.println(e);
        }
        collector.checkThat("Vérification de la taille de l'information reçue",
                destinationFinale.getInformationRecue().nbElements(), is(6));
        collector.checkThat("Vérification du contenu de l'information reçue",
                destinationFinale.getInformationRecue(), is(content));
    }
}
