/**
 * Cette classe représente un ensemble de tests JUnit pour la classe TransmetteurParfait.
 * Elle vérifie le comportement des méthodes emettre et recevoir de la classe TransmetteurParfait
 * en connectant une source aléatoire à une destination finale.
 */
package tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import destinations.DestinationFinale;
import information.InformationNonConformeException;
import sources.SourceAleatoire;
import transmetteurs.TransmetteurParfait;

/**
 * Cette classe représente un ensemble de tests JUnit pour la classe
 * TransmetteurParfait.
 * Elle vérifie le comportement des méthodes emettre et recevoir de la classe
 * TransmetteurParfait en connectant une source aléatoire à une destination
 * finale.
 */
public class TransmetteurParfaitTest {

    /**
     * Test unitaire pour la méthode emettre de la classe TransmetteurParfait.
     * Il vérifie si l'information émise par un transmetteur parfait correspond
     * à l'information reçue par une destination finale.
     */
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
        assertTrue("L'information émise ne correspond pas à l'information reçue",
                t.getInformationEmise().equals(d.getInformationRecue()));
    }

    /**
     * Test unitaire pour la méthode recevoir de la classe TransmetteurParfait.
     * Il vérifie si l'information émise par une source aléatoire correspond
     * à l'information reçue par un transmetteur parfait.
     */
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
