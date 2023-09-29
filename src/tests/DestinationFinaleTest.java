/**
 * Cette classe représente un ensemble de tests JUnit pour la classe DestinationFinale.
 * Elle vérifie si l'information émise par une source aléatoire peut être correctement reçue
 * par une destination finale, et si elle correspond à l'information reçue.
 */
package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import information.InformationNonConformeException;
import destinations.DestinationFinale;
import sources.SourceAleatoire;

/**
 * Cette classe représente un ensemble de tests JUnit pour la classe
 * DestinationFinale.
 * Elle vérifie si l'information émise par une source aléatoire peut être
 * correctement reçue par une destination finale, et si elle correspond à
 * l'information reçue.
 * 
 */
public class DestinationFinaleTest {

    /**
     * Test unitaire pour la méthode recevoir de la classe DestinationFinale.
     * Il vérifie si l'information émise par une source aléatoire peut être
     * correctement reçue
     * par une destination finale, et si elle correspond à l'information reçue.
     */
    @Test
    public void testRecevoir() {
        SourceAleatoire s = new SourceAleatoire(10);
        DestinationFinale d = new DestinationFinale();
        try {
            d.recevoir(s.getInformationEmise());
        } catch (InformationNonConformeException e) {
            System.out.println(e.getMessage());
        }
        assertTrue("L'information reçue ne correspond pas à l'information émise",
                s.getInformationEmise().equals(d.getInformationRecue()));
    }
}
