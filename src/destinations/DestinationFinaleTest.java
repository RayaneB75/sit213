package destinations;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import information.InformationNonConformeException;
import sources.SourceAleatoire;

public class DestinationFinaleTest {
    @Test
    public void testRecevoir() {
        SourceAleatoire s = new SourceAleatoire(10);
        DestinationFinale d = new DestinationFinale();
        try {
            d.recevoir(s.getInformationEmise());
        } catch (InformationNonConformeException e) {
            System.out.println(e.getMessage());
        }
        assertTrue("L'information reçue ne correspond pas à l'information émise", s.getInformationEmise().equals(d.getInformationRecue()));
    }
}
