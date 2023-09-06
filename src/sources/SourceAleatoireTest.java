package sources;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SourceAleatoireTest {
    @Test
    public void testSourceAleatoire() {
        SourceAleatoire s1 = new SourceAleatoire(10);
        assertTrue(s1.informationGeneree.nbElements() == 10);
        s1 = new SourceAleatoire(10, 10);
        assertTrue(s1.informationGeneree.nbElements() == 10);
        SourceAleatoire s2 = new SourceAleatoire(10, 10);

        int diff = 0;
        for (Boolean b1 : s1.informationGeneree) {
            for (Boolean b2 : s2.informationGeneree) {
                if (b1 != b2) {
                    diff++;
                }
            }
        }
        assertTrue(diff > 0);
    }
}
