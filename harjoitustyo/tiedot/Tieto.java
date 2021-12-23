package harjoitustyo.tiedot;

import harjoitustyo.apulaiset.Tietoinen;
import harjoitustyo.apulaiset.Jokerimerkit;

/**Abstrakti Tieto-luokka, Luokka sisältää nimen tiedostoille
*  ja hakemistoille.
*  <p>
*  Olio-ohjelmoinin perusteet 2, kevät 2019
*  <p>
*  @author Erno Oljakka, erno.oljakka@tuni.fi,
*  Luonnontieteiden tiedekunta,
*  Tampereen yliopisto.
*/

public abstract class Tieto implements Comparable<Tieto>, Tietoinen{

    //attribuutit
    
    /** tiedon nimen attribuutti */
    private StringBuilder nimi;
    
    //rakentajat
    
    //parametriton rakentaja

    /**
     *
     */
    public Tieto() {
        nimi = new StringBuilder();
    }
    
    /**Rakentajaan tulee tiedon nimi parametrina.
     * 
     * @param uusiNimi
     * @throws IllegalArgumentException jos nimi on virheellinen.
     */
    public Tieto(StringBuilder uusiNimi) throws IllegalArgumentException {
        boolean nimiKelpaa = oikeanNiminenTiedosto(uusiNimi);
            if (uusiNimi == null || nimiKelpaa == false) {
                throw new IllegalArgumentException();
            } else {
                nimi(uusiNimi);
            }
    }
       
    //aksessorit    

    /**
     *
     * @return
     */

    public StringBuilder nimi() {
      return nimi;
    }
    
    /**Metodia kutsutaan, kun tiedolla halutaan asettaa uusi nimi.
     * 
     * @param uusiNimi, tiedolle asetettava uusi nimi.
     * @throws IllegalArgumentException jos tiedon nimi on virheellinen.
     */
    public void nimi(StringBuilder uusiNimi) throws IllegalArgumentException {
        boolean nimiKelpaa = oikeanNiminenTiedosto(uusiNimi);
            if (uusiNimi != null && nimiKelpaa) {
                nimi = uusiNimi;
            } else {
            throw new IllegalArgumentException();
            }

        } 
    
    //luokkametodit
    
    /**Metodissa tarkastetaan, että tiedoston nimi on oikeanlainen
     * 
     * @param tiedostonNimi, nimi jonka oikeellisuutta tutkitaan.
     * @return true, jos tiedoston nimi on oikeanlainen ja false, jos näin ei ole.
     */
    public boolean oikeanNiminenTiedosto(StringBuilder tiedostonNimi) {
        if (tiedostonNimi == null) {
            return false;
        }
        //apumuuttuja for-silmukkaa varten ja palautettava boolean-tyyppinen muuttuja,
        //sekä muuttuja johon lasketaan pisteiden määrä ja voidaan tarkistaa ettei nimi muodostu pelkästään niistä.
        char apu;
        boolean palautettava = true;
        int pisteidenlkm = 0;

        for (int i = 0; (i < tiedostonNimi.length()) || palautettava == false; i++) {
            apu = tiedostonNimi.charAt(i);
            if (apu == '.') {
                pisteidenlkm++;
            }
            if (('a' <= apu && apu <= 'z') || ('A' <= apu && apu <= 'Z')
                    || ('0' <= apu && apu <= '9') || (apu == '_') || apu == '.') {
                palautettava = true;
            } else {
                return false;
            }
        }
        if (tiedostonNimi.length() == pisteidenlkm) {
            return false;     
        } else {
            return palautettava;  
        } 
    }
       
    //Ilmentymämetodit
    
    //Object-luokan korvatut metodit
    
    
    /**Object-luokan toString-metodi korvattuna, että se palauttaa tiedon
     * merkkijono esityksen.
     * 
     * @return tiedon nimi.
     */
    @Override
    public String toString() {
        return nimi.toString();
    }
    
    /**Object-luokan korvattu equals-metodi tutkii kahden olio yhtäläisyyttä.
     * 
     * @param obj, Object-tyyppinen olio.
     * @return true, jos parametrina olio vastaa verrattua oliota false, jos ei.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof Double) {
            return false;
        } else {
            //muutetaan StringBuilder.tyyppinen nimi string-olioksi ja parametrina annettu olio string-olioksi
            String a = nimi.toString();
            String b = obj.toString();
            
            //apumuuttuja ja uusi String olio, johon for-silmukassa kokoamme olion ilman tavujen määrää
            char apu = 'x';
            String c = "";
            
            //rakennetaan uusi String-olio, josta poistetaan kaikki muu tieto paitsi nimi
            //eli olion ensimmäinen parametri
            for (int i = 0; apu != ' '; i++ ) {
                apu = b.charAt(i);
                if (apu != ' ') {
                    c = c + b.charAt(i);
                }
            }
            return a.equals(c);
        }
    }

    //Comparable-rajapinnan metodien toteutus
    
    
    /**Verrataan tietoja keskenään.
     *
     * @param t, Tieto-tyyppinen muuttuja, jota verrataan toiseen tietoon
     * @return 0, jos tiedot vastaavat toisiaan -1, jos parametrina saatu tieto on
     * suurempi kuin verrattu ja 1, jos parametrina saatu on pienempi
     */
    
    @Override
    public int compareTo(Tieto t) {
        //muutetaan StringBuilder.tyyppinen nimi string-olioksi ja parametrina annettu olio string-olioksi
        String a = nimi.toString();
        String b = t.toString();
        
        //apumuuttuja ja uusi String olio, johon for-silmukassa kokoamme olion ilman tavujen määrää
        char apu = 'x';
        String c = "";
        
        //rakennetaan uusi String-olio, josta poistetaan kaikki muu tieto paitsi nimi eli olion ensimmäinen parametri
        for (int i = 0; apu != ' '; i++ ) {
            apu = b.charAt(i);
            if (apu != ' ') {
                c = c + b.charAt(i);
            }
        }
        if (a.equals(c)) {
            return 0;
        } else if (a.charAt(0) < c.charAt(0)) {
            return -1;
        } else {
            return 1;
        }  
    }
    
    @Override
    public boolean equals(String hakusana) {
        //muutetaan nimi, jota hakusanalla etsitään string muotoon, jotta vertailu on helpompaa
        String a = nimi.toString();
        //verrataan nimiä heti jos hakusana on syötetty ilman jokerimerkkejä
        if (a.equals(hakusana)) {
            return true;
        }
        //annetaan hakusana parametrinä jokerimerkkejä2TaiEnemmän-metodille ja tutkitaan onko jokerimerkkejä liikaa
        boolean oikeatPaikat = Jokerimerkit.jokerimerkitOikeissaPaikoissa(hakusana);
        //jos hakusana on null-arvoinen palautetaan false
        if (hakusana == null || oikeatPaikat == false || hakusana.length() == 0) {
            return false;
        }
        //uusi string-olio johon sijoitetaan paluu arvo jokerimerkkienPoisto-metodista
        //eli hakusana ilman jokerimerkkejä
        String hakusana2 = Jokerimerkit.jokerimerkkienPoisto(hakusana);
        //tutkitaan ensin onko hakusanassa vain yksi merkki, joka on asteriksi(*), jolloin paluu arvo on aina true
        if (hakusana.length() == 1 && hakusana.charAt(0) == '*') {
            return true;
        }
        //verrataan hakusanaa tiedoston nimeen kun jokerimerkit ovat molemmin puolin hakusanaa
        //luodaan int-tyyppinen laskuri joka kertoo mitä hakusanan merkkiä etsitään ja,
        //jos löydetään merkki ja siitä seuraava
        //ei sovi hakusanaan, laskuri nollaantuu.
        int x = 0;
        //luodaan StringBuilder johon sijoitetaan löydetyt merkit nimestä,
        //jotka sopivat hakusanaan ja lopussa verrataan
        //tätä StringBuilderiä hakusanaan ja jos ne ovat samat on paluuarvo true
        StringBuilder verrattava = new StringBuilder(2);
        //luodaan myös boolean-tyyppinen lippuarvo joka määrittää onko edellinen merkki sisältynyt hakusanaan
        boolean edellinenSopi = false;
        //kun jokerimerkit ovat hakusanan molemmilla puolilla
        if (hakusana.charAt(0) == '*' && hakusana.charAt(hakusana.length() - 1) == '*') {
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i) == hakusana2.charAt(x)) {
                    verrattava.append(a.charAt(i));
                    edellinenSopi = true;
                    x++;
                } else if (edellinenSopi) {
                    verrattava.delete(0, x);
                    edellinenSopi = false; 
                    x = 0;
                }
                if (verrattava.toString().equals(hakusana2)) {
                    return true;
                }      
            }
        //kun jokerimerkki on hakusanan alussa
        } else if (hakusana.charAt(0) == '*') {
            //rakennetaan verrattava StringBuilderiin for-silmukalla hakusana2:n pituinen 
            //merkkijono nimen lopusta jota verrataan hakusanaan
            int pituus = hakusana2.length();
            
            for (int i = a.length() - pituus; i < a.length(); i++) {
                verrattava.append(a.charAt(i));
            }
            if (verrattava.toString().equals(hakusana2)) {
                return true;
            }
            
        //jokerimerkki on hakusanan lopussa
        } else {
            for (int i = 0; i < a.length(); i++) {
                if (a.charAt(i) != hakusana2.charAt(x)) {
                    return false;
                } else if (a.charAt(i) == hakusana2.charAt(x)) {
                    verrattava.append(a.charAt(i));
                    x++;
                    if (verrattava.toString().equals(hakusana2)) {
                        return true;
                    }
                } 
            }
        } 
        return false;
    
    }
}

