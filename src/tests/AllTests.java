package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DestinationFinaleTest.class, SourceAleatoireTest.class, SourceFixeTest.class,
        TransmetteurParfaitTest.class, ModulateurRZTest.class, ModulateurNRZTest.class, ModulateurNRZTTest.class })
public class AllTests {

}
