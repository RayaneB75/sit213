package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DestinationFinaleTest.class, SourceAleatoireTest.class, SourceFixeTest.class,
        TransmetteurParfaitTest.class, CodeurRZTest.class, CodeurNRZTest.class, CodeurNRZTTest.class })
public class AllTests {

}
