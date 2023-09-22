package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Cette classe représente une suite de tests regroupant plusieurs classes de tests JUnit.
 * Elle utilise JUnit pour exécuter les tests définis dans les classes spécifiées dans l'annotation @SuiteClasses.
 * Les classes de tests incluses sont : DestinationFinaleTest, SourceAleatoireTest, SourceFixeTest et TransmetteurParfaitTest.
 * Les résultats des tests sont regroupés et rapportés par JUnit.
 */
@RunWith(Suite.class)
@SuiteClasses({ DestinationFinaleTest.class, SourceAleatoireTest.class, SourceFixeTest.class,
        TransmetteurParfaitTest.class, CodeurRZTest.class, CodeurNRZTest.class, CodeurNRZTTest.class })
public class AllTests {

    /**
     * Constructeur par défaut de la classe AllTests.
     * Ce constructeur n'a pas de fonction particulière et est utilisé par défaut par JUnit.
     */
    public AllTests() {
        // Constructeur par défaut
    }
}
