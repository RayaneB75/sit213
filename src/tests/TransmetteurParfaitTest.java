package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import destinations.DestinationFinale;
import information.InformationNonConformeException;
import sources.SourceAleatoire;
import transmetteurs.TransmetteurParfait;

public class TransmetteurParfaitTest {
    @Test
    public void testEmettre() {
        SourceAleatoire s = new SourceAleatoire(10);
        TransmetteurParfait<Boolean> t = new TransmetteurParfait<Boolean>();
        DestinationFinale d = new DestinationFinale();
        t.connecter(d);
        t.setInformationRecue(s.getInformationEmise());
        try {
            t.emettre();
        } catch (InformationNonConformeException e) {
            System.out.println(e.getMessage());
        }
        assertTrue("L'information émise ne correspond pas à 'information reçue",
                t.getInformationEmise().equals(d.getInformationRecue()));
    }

    @Test
    public void testRecevoir() {
        SourceAleatoire s = new SourceAleatoire(10);
        TransmetteurParfait<Boolean> t = new TransmetteurParfait<Boolean>();
        try {
            t.recevoir(s.getInformationEmise());
        } catch (InformationNonConformeException e) {
            System.out.println(e.getMessage());
        }
        assertTrue("L'information émise ne correspond pas à l'information reçue",
                s.getInformationEmise().equals(t.getInformationRecue()));
    }
}
