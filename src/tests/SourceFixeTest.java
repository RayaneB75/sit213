/**
 * Cette classe représente un ensemble de tests JUnit pour la classe SourceFixe.
 * Elle vérifie le comportement de la classe SourceFixe en générant des symboles
 * à partir de séquences de bits fixes et en effectuant différentes vérifications
 * sur la taille de l'information générée et la cohérence entre deux instances de la classe.
 */
package tests;

import static org.junit.Assert.assertTrue;
import sources.SourceFixe;

import org.junit.Test;

/**
 * Cette classe représente un ensemble de tests JUnit pour la classe SourceFixe.
 * Elle vérifie le comportement de la classe SourceFixe en générant des symboles
 * à partir de séquences de bits fixes et en effectuant différentes
 * vérifications sur la taille de l'information générée et la cohérence entre
 * deux instances de la classe.
 * 
 */
public class SourceFixeTest {

    /**
     * Test unitaire pour la classe SourceFixe.
     * Il vérifie si la source fixe génère le bon nombre de symboles
     * à partir d'une séquence binaire fixe en utilisant différents cas de test.
     * Il vérifie également si deux instances de SourceFixe avec la même entrée
     * génèrent des sorties identiques.
     */
    @Test
    public void testSourceFixe() {
        SourceFixe s1 = new SourceFixe("0111010100");
        assertTrue("La source n'a pas généré 10 symboles", s1.getInformationGeneree().nbElements() == 10);
        s1 = new SourceFixe("1000101100");
        assertTrue("La source n'a pas généré 10 symboles", s1.getInformationGeneree().nbElements() == 10);
        SourceFixe s2 = new SourceFixe("1000101100");

        assertTrue("Deux sources avec la même entrée donnent des sorties différentes",
                s1.getInformationGeneree().equals(s2.getInformationGeneree()));

        s1 = new SourceFixe("1000101100");
        assertTrue(s1.getInformationGeneree().nbElements() == 10);
        int diff = 0;
        for (int i = 0; i < 7; i++) {
            if (s1.getInformationGeneree().iemeElement(i) != ("1000101100".charAt(i) == '1')) {
                diff++;
            }
        }
        assertTrue("Le résultat généré par la source ne correspond pas à l'entrée", diff == 0);
    }
}
