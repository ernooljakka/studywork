
package harjoitustyo.apulaiset;

/**Jokerimerkit-luokka Luokka sisältää kaksi metodia, joita kutsutaan
 ** abstraktissa Tieto-luokassa
 * <p>
 * Olio-ohjelmoinin perusteet 2, kevät 2019
 * <p>
 * @author Erno Oljakka, erno.oljakka@tuni.fi,
 * Luonnontieteiden tiedekunta,
 * Tampereen yliopisto. 
 */

public class Jokerimerkit {
    

    /**Jokerimerkkimetodi, poistetaan jokerimerkit saadusta string-oliosta,
     * jotta sitä voidaan vertailla equals(String hakusana)-metodissa.
     * 
     * @param m, merkkijono, joka saattaa sisältää jokerimerkkejä(*).
     * @return merkkijonon, josta on poistettu jokerimerkit.
     */
    public static String jokerimerkkienPoisto(String m) {
        //String-olio, joka rakennetaan parametrina saaduista merkeistä paitsi, että jokerimerkki/merkit on poistettu
        String m2 = "";
        if (m.charAt(0) == '*' && m.charAt(m.length() - 1) == '*') {
            for (int i = 1; i < m.length() - 1; i++) {
                m2 += m.charAt(i);
            }
        } else if (m.charAt(m.length() - 1) == '*') {
            for (int i = 0; i < m.length() - 1; i++) {
                m2 += m.charAt(i);
            }
        } else if (m.charAt(0) == '*') {
            for (int i = 1; i < m.length(); i++) {
                m2 += m.charAt(i);
            }
        } else {
            return m;
        }
        return m2;
    }
    
    
    /**Tutkitaan, että merkkijonona saadussa merkkijonossa ovat jokerimerkit oikeilla paikoilla.
     * 
     * @param m, merkkijono, josta tarkastetaan että jokerimerkit(*) ovat oikeissa paikoissa
     * @return true, jos jokermerkit ovat oikeissa paikoissa ja false, jos eivät ole.
     */
    public static boolean jokerimerkitOikeissaPaikoissa(String m) {
        //jos parametri on null voidaan heri palauttaa false
        if (m == null) {
            return false;
        }
        //boolean-tyyppinen muuttuja, jos jokerimerkkejä vierekkäin
        boolean vierekkain = false;
        //lippumuuttuja
        boolean x;
        //tutkitaan onko jokerimerkkejä vierekkäin
        for (int i = 0; i < m.length() - 1; i++) {
            x = true;
            while(x) {
                if (m.charAt(i) == '*' && m.charAt(i + 1) == '*') {
                    vierekkain = true;
                }
                x = false;
            }
        }
        if (vierekkain) {
            return false;
        }
        //int-tyyppinen muuttuja jokerimerkkien määrälle
        int jokerimerkkienlkm = 0;
        //lasketaan jokerimerkkien määrä (jos yli 2 niin palautetaan false)
        for (int i = 0; i < m.length(); i++) {
            if (m.charAt(i) == '*') {
                jokerimerkkienlkm++;
            }
        }
        if (jokerimerkkienlkm == 2 && !(m.charAt(0) == '*' && m.charAt(m.length() - 1) == '*')) {
            return false;
        } else if (jokerimerkkienlkm == 1 && (m.charAt(0) == '*' || m.charAt(m.length() - 1) == '*')) {
            return true;
        } else {
            return true;
        } 
    }
}
