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
import modulateurs.ModulateurRZ;
import modulateurs.DemodulateurRZ;
import sources.SourceFixe;
import transmetteurs.TransmetteurParfait;

public class ModulateurRZTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    private static Information<Boolean> content = new Information<>(
            new Boolean[] { false, true, true, false, false, true });
    private SourceFixe source = null;
    private ModulateurRZ modulateur = null;
    private DemodulateurRZ demodulateur = null;
    private TransmetteurParfait<Float> transmetteurParfait = null;
    private DestinationFinale destinationFinale = null;

    public ModulateurRZTest() {
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
     * - Un modulateur RZ de 30 échantillons par symboles (180 échantillons en tout)
     * avec une plage d'amplitude allant de 0 à 1
     * - Un transmetteur parfait (TEB attendu = 0)
     * - Un demodulateur RZ de même caractérisitiques que le modulateur
     * - Une destination finale booléenne
     */
    @Before
    public void setUp() {
        source = new SourceFixe(6, "011001");
        modulateur = new ModulateurRZ(30, 0f, 1f);
        transmetteurParfait = new TransmetteurParfait<Float>();
        demodulateur = new DemodulateurRZ(30, 0f, 1f);
        destinationFinale = new DestinationFinale();
        source.connecter(modulateur);
    }

    @After
    public void tearDown() {
    }

    /**
     * Ici on testes la modulation avec 3 vérifications :
     * - L'information modulée contient bien 180 échantillons
     * - Le 36eme élément est bien à 0f
     * - Le 169eme élément est bien à 1f
     */
    @Test
    public void testModulation() {

        try {
            source.emettre();
        } catch (Exception e) {
            System.out.println(e);
        }
        collector.checkThat("Vérification de l'information reçue", modulateur.getInformationRecue().nbElements(),
                is(6));
        collector.checkThat("Vérification de l'information modulée",
                modulateur.getInformationEmise().nbElements(), is(180));
        collector.checkThat("Vérification d'un premier echantillon spécifique",
                modulateur.getInformationEmise().iemeElement(36), is(0.0f));
        collector.checkThat("Vérification d'un deuxième echantillon spécifique",
                modulateur.getInformationEmise().iemeElement(169), is(1.0f));

    }

    /**
     * Ici on teste que la démodulation (après modulation et transmission) s'est
     * bien déroulée, avec 2 vérifications :
     * - Le nombre d'élément dans l'information finale est bien de 6 bits
     * - L'information reçue est bien "011001"
     */
    @Test
    public void testDemodulation() {
        modulateur.connecter(transmetteurParfait);
        transmetteurParfait.connecter(demodulateur);
        demodulateur.connecter(destinationFinale);

        try {
            source.emettre();
            modulateur.emettre();
            transmetteurParfait.emettre();
            demodulateur.emettre();
        } catch (Exception e) {
            System.out.println(e);
        }
        collector.checkThat("Vérification de la taille de l'information reçue",
                destinationFinale.getInformationRecue().nbElements(), is(6));
        collector.checkThat("Vérification du contenu de l'information reçue",
                destinationFinale.getInformationRecue(), is(content));
    }
}
