package transmetteurs;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import destinations.DestinationFinale;
import information.InformationNonConformeException;
import sources.SourceAleatoire;

public class TransmetteurParfaitTest {
    @Test
    public void testEmettre() {
        SourceAleatoire s = new SourceAleatoire(10);
        TransmetteurParfait t = new TransmetteurParfait();
        DestinationFinale d = new DestinationFinale();
        t.connecter(d);
        t.informationRecue = s.getInformationEmise();
        try {
            t.emettre();
        } catch (InformationNonConformeException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(t.getInformationEmise().equals(d.getInformationRecue()));
    }

    @Test
    public void testRecevoir() {
        SourceAleatoire s = new SourceAleatoire(10);
        TransmetteurParfait t = new TransmetteurParfait();
        try {
            t.recevoir(s.getInformationEmise());
        } catch (InformationNonConformeException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(s.getInformationEmise().equals(t.getInformationRecue()));
    }
}
