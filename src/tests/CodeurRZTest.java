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

public class CodeurRZTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    private static Information<Boolean> content = new Information<>(
            new Boolean[] { false, true, true, false, false, true });
    private SourceFixe source = null;
    private CodeurRZ codeur = null;
    private Decodeur decodeur = null;
    private TransmetteurParfait<Float> transmetteurParfait = null;
    private DestinationFinale destinationFinale = null;

    public CodeurRZTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

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
