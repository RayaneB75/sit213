/**
 * Cette classe représente une suite de tests regroupant plusieurs classes de tests JUnit.
 * Elle utilise JUnit pour exécuter les tests définis dans les classes spécifiées dans l'annotation @SuiteClasses.
 * Les classes de tests incluses sont : DestinationFinaleTest, SourceAleatoireTest, SourceFixeTest et TransmetteurParfaitTest.
 * Les résultats des tests sont regroupés et rapportés par JUnit.
 */
package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DestinationFinaleTest.class, SourceAleatoireTest.class, SourceFixeTest.class,
        TransmetteurParfaitTest.class, ModulateurRZTest.class, ModulateurNRZTest.class, ModulateurNRZTTest.class })
public class AllTests {

}
