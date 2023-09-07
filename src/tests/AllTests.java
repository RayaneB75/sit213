package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sources.SourceAleatoireTest;
import sources.SourceFixeTest;

@RunWith(Suite.class)
@SuiteClasses({ SourceAleatoireTest.class, SourceFixeTest.class })
public class AllTests {

}