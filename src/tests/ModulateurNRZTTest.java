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
import modulateurs.ModulateurNRZT;
import modulateurs.DemodulateurNRZT;
import sources.SourceFixe;
import transmetteurs.TransmetteurParfait;

public class ModulateurNRZTTest {
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    private static Information<Boolean> content = new Information<>(
            new Boolean[] { false, true, true, false, false, true });
    private SourceFixe source = null;
    private ModulateurNRZT modulateur = null;
    private DemodulateurNRZT demodulateur = null;
    private TransmetteurParfait<Float> transmetteurParfait = null;
    private DestinationFinale destinationFinale = null;

    public ModulateurNRZTTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        source = new SourceFixe(6, "011001");
        modulateur = new ModulateurNRZT(30, -1f, 1f);
        transmetteurParfait = new TransmetteurParfait<Float>();
        demodulateur = new DemodulateurNRZT(30, -1f, 1f);
        destinationFinale = new DestinationFinale();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testModulation() {
        source.connecter(modulateur);

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
                (float) modulateur.getInformationEmise().iemeElement(36), is((float) 0.6));
        collector.checkThat("Vérification d'un deuxième echantillon spécifique",
                (float) modulateur.getInformationEmise().iemeElement(143), is((float) -0.7));
    }

    @Test
    public void testDemodulation() {
        source.connecter(modulateur);
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