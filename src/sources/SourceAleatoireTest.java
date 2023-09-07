package sources;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SourceAleatoireTest {
    @Test
    public void testSourceAleatoire() {
        SourceAleatoire s1 = new SourceAleatoire(10);
        assertTrue("La source n'a pas généré 10 symboles", s1.informationGeneree.nbElements() == 10);
        s1 = new SourceAleatoire(10, 10);
        assertTrue("La source n'a pas généré 10 symboles", s1.informationGeneree.nbElements() == 10);
        SourceAleatoire s2 = new SourceAleatoire(10, 10);
        assertTrue("Deux source avec la même entrée donnent des sorties différentes", s1.informationGeneree.equals(s2.informationGeneree));
    }
}
