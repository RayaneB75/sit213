package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import destinations.DestinationFinaleTest;
import sources.SourceAleatoireTest;
import sources.SourceFixeTest;
import transmetteurs.TransmetteurParfaitTest;

@RunWith(Suite.class)
@SuiteClasses({ DestinationFinaleTest.class, SourceAleatoireTest.class, SourceFixeTest.class, TransmetteurParfaitTest.class })
public class AllTests {

}
