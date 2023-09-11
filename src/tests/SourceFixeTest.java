package tests;

import static org.junit.Assert.assertTrue;
import sources.SourceFixe;

import org.junit.Test;

public class SourceFixeTest {
    @Test
    public void testSourceFixe() {
        SourceFixe s1 = new SourceFixe(10, "0111010100");
        assertTrue("La source n'a pas généré 10 symboles", s1.getInformationGeneree().nbElements() == 10);
        s1 = new SourceFixe(10, "1000101100");
        assertTrue("La source n'a pas généré 10 symboles", s1.getInformationGeneree().nbElements() == 10);
        SourceFixe s2 = new SourceFixe(10, "1000101100");

        assertTrue("Deux source avec la même entrée donnent des sorties différentes",
                s1.getInformationGeneree().equals(s2.getInformationGeneree()));
        s1 = new SourceFixe(7, "1000101100");
        assertTrue(s1.getInformationGeneree().nbElements() == 7);
        int diff = 0;
        for (int i = 0; i < 7; i++) {
            if (s1.getInformationGeneree().iemeElement(i) != ("1000101100".charAt(i) == '1')) {
                diff++;
            }
        }
        assertTrue("Le résultat généré par la source ne correspond pas à l'entrée", diff == 0);
    }
}