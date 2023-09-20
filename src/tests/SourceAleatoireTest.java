/**
 * Cette classe représente un ensemble de tests JUnit pour la classe SourceAleatoire.
 * Elle vérifie le comportement de la classe SourceAleatoire en générant des symboles aléatoires
 * et en vérifiant la taille de l'information générée ainsi que la cohérence entre deux instances de la classe.
 */
package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sources.SourceAleatoire;

public class SourceAleatoireTest {

    /**
     * Test unitaire pour la classe SourceAleatoire.
     * Il vérifie si la source aléatoire génère le bon nombre de symboles
     * en utilisant le constructeur par défaut et le constructeur avec une graine spécifiée.
     * Il vérifie également si deux instances de SourceAleatoire avec la même graine génèrent des sorties identiques.
     */
    @Test
    public void testSourceAleatoire() {
        SourceAleatoire s1 = new SourceAleatoire(10);
        assertTrue("La source n'a pas généré 10 symboles", s1.getInformationGeneree().nbElements() == 10);
        s1 = new SourceAleatoire(10, 10);
        assertTrue("La source n'a pas généré 10 symboles", s1.getInformationGeneree().nbElements() == 10);
        SourceAleatoire s2 = new SourceAleatoire(10, 10);
        assertTrue("Deux sources avec la même entrée donnent des sorties différentes",
                s1.getInformationGeneree().equals(s2.getInformationGeneree()));
    }
}
