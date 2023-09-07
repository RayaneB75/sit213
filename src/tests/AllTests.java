package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import destinations.DestinationFinale;
import sources.SourceAleatoireTest;
import sources.SourceFixeTest;
import transmetteurs.TransmetteurParfaitTest;

@RunWith(Suite.class)
@SuiteClasses({ DestinationFinale.class, SourceAleatoireTest.class, SourceFixeTest.class, TransmetteurParfaitTest.class })
public class AllTests {

}
