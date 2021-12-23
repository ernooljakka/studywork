package harjoitustyo;

import harjoitustyo.tiedot.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**Tulkki-luokka toimii yhteistyössä OopeHT2-luokan kanssa
 ** ja kutsuu muissa luokissa olevia metodeja. 
 * <p>
 * Olio-ohjelmoinin perusteet 2, kevät 2019
 * <p>
 * @author Erno Oljakka
 * Luonnontieteiden tiedekunta,
 * Tampereen yliopisto.
 */
public class Tulkki {
    
    //attribuutit
    
    /** käyttäjälle luodaan automaattisesti hakemistojuuri */
    private Hakemisto hakemistonJuuri;
    
    /** Kertoo, missä hakemistossa käyttäjä navigoi tietyllä hetkellä */
    private Hakemisto nykyinenHakemisto;
    
    
    //rakentajat
    
    public Tulkki() {
        Hakemisto juuri = new Hakemisto();
        hakemistonJuuri(juuri);
        nykyinenHakemisto(juuri);
    }
    
    //aksessorit
    
    //palauttaa hakemiston juuren
    public Hakemisto hakemistonJuuri() {
        return hakemistonJuuri;
    }
    
    public void hakemistonJuuri(Hakemisto uusiJuuri) {
        hakemistonJuuri = uusiJuuri;
    }
    
    //palauttaa tällä hetkellä tarkastelussa olevan hakemiston
    public Hakemisto nykyinenHakemisto() {
        return nykyinenHakemisto;
    }
    
    public void nykyinenHakemisto(Hakemisto uusiHakemisto) {
        nykyinenHakemisto = uusiHakemisto;
    }
    
    
    //luokka-metodit
    
    /**tulostaHakemistopolku-metodissa tulostetaan käyttäjän sen hetkinen
     * sijainti hakemistoissa.
     * 
     * @return hakemistopolku, kertoo missä kohtaa hakemistoja käyttäjä 
     * navigoi sillä hetkellä.
     */
    public String tulostaHakemistopolku() {
        //luodaan arraylist johon while-silmukassa lisätään hakemistojen nimet
        //ja niiden väliin kenoviivat "/", jotka lopuksi for-silmukassa lisätään 
        //palautettava-muuttujaan.
        ArrayList<String> hakemistoLista = new ArrayList<>();
        String palautettava = "/";
        //lippumuuttuja while-silmukalle
        boolean jatketaanko = true;
        //apuhakemisto, jonka avulla saamme tietoon mikä on nykyinen hakemisto
        //ja sen avulla etenemme kunnes apuhakemiston ylihakemisto on null 
        //eli olemme saavuttaneet juurihakemiston
        Hakemisto apuHakemisto = nykyinenHakemisto;
        while(jatketaanko) {    
            if (apuHakemisto.ylihakemisto() == null) {
                jatketaanko = false;
            } else {
                hakemistoLista.add(apuHakemisto.nimi().toString() + "/");
                apuHakemisto = apuHakemisto.ylihakemisto();
            }
        }
        //lisätään listalta alkiot palauttavaan muuttujaan
        if (hakemistoLista.size() > 0) {
            for (int i = hakemistoLista.size() - 1; i >= 0; i--) {
                palautettava += hakemistoLista.get(i);
            }
        }
        return palautettava;
    }
    
    
    /**Md-komennolla Luodaan käsiteltävään hakemistoon uusi alihakemisto.
     * 
     * @param uusiAlihakemisto
     * @return true, jos hakemiston luonti onnistuu ja false jos ei.
     */
    public boolean alihakemistonLuonti(String uusiAlihakemisto) {
        try {
            //luodaan hakemisto-olio
            Hakemisto hakemisto = new Hakemisto(new StringBuilder(uusiAlihakemisto), nykyinenHakemisto);
            //tutkitaan että hakemistoon johon ollaan luomassa uutta hakemistoa 
            //ei sisällä saman nimistä hakemisto tai tiedostoa.
            boolean voidaanLisata = true;
            if (nykyinenHakemisto.sisalto().isEmpty()) {    
                nykyinenHakemisto.lisaa(hakemisto);
            } else {        
                for (int i = 0; i < nykyinenHakemisto.sisalto().size(); i++) {
                    if (nykyinenHakemisto.sisalto().get(i) instanceof Hakemisto) {
                        if (nykyinenHakemisto.sisalto().get(i).nimi().equals(uusiAlihakemisto)) {
                            voidaanLisata = false;
                            return false;
                        }
                    } else {
                        if (nykyinenHakemisto.sisalto().get(i).equals(uusiAlihakemisto)) {
                        voidaanLisata = false;
                        return false;
                        }
                    }
                }
                if (voidaanLisata) {
                    nykyinenHakemisto.lisaa(hakemisto);
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
        
    }
    
    /**Cd-komennolla vaihdetaan tarkasteltavaa hakemistoa, joko alihakemistoon tai
     * siirrytään ylihakemistoon antamalla parametriksi "..".
     * 
     * @param vaihdettava, vaihdettava sisältää nimen johon hakemistoa vaihdetaan.
     * @return true jos hakemiston vaihto onnistuu ja false, jos ei onnistu.
     */
    public boolean hakemistonVaihto(String vaihdettava) {
        //tutkitaan löytyykö alihakemisto johon vaihdetaan
        boolean alihakemistoloytyy = false;
        //int-tyyppinen muuttuja johon sijoitetaan indeksi, jossa samanniminen tieto on
        int samaTieto = 0;
        for (int i = 0; i < nykyinenHakemisto.sisalto().size(); i++) {
            if (vaihdettava.equals(nykyinenHakemisto.sisalto().get(i).nimi().toString())) {
                alihakemistoloytyy = true;
                samaTieto = i;
                //System.out.println("2");
            }
        }
        if (nykyinenHakemisto == hakemistonJuuri && vaihdettava.equals("..")) {
            return false;
        } else if (vaihdettava.equals("..")) {
            nykyinenHakemisto = nykyinenHakemisto.ylihakemisto();
            return true;
        //"." tarkoittaa että siirrytään juuri hakemistoon.    
        } else if (vaihdettava.equals(".")) {
            nykyinenHakemisto = hakemistonJuuri;
            return true;
        } else if (alihakemistoloytyy == true && nykyinenHakemisto.sisalto().get(samaTieto) instanceof Hakemisto) {
            nykyinenHakemisto = (Hakemisto) nykyinenHakemisto.sisalto().get(samaTieto);
            return true;
        } else {
            return false;
        }
    }
 
    /** Mf-komennolla luodaan hakemistoon uusi tiedosto.
     * 
     * @param tiedostonNimi, nimi joka annetaan luotavalla tiedostolle.
     * @param tiedostonKoko, kertoo tiedoston koon tavuina.
     * @return true, jos tiedoston luonti onnistuu ja false, jos ei.
     */
    public boolean luoTiedosto(String tiedostonNimi, int tiedostonKoko) {
        try {
            //luodaan tiedosto-olio
            Tiedosto tiedosto = new Tiedosto(new StringBuilder(tiedostonNimi), tiedostonKoko);
            boolean voidaanLisata = true;
            if (nykyinenHakemisto.sisalto().isEmpty()) {    
                nykyinenHakemisto.lisaa(tiedosto);
            } else {        
                for (int i = 0; i < nykyinenHakemisto.sisalto().size(); i++) {
                    if (nykyinenHakemisto.sisalto().get(i).equals(tiedostonNimi)) {
                        voidaanLisata = false;
                        return false;
                    }
                }
                if (voidaanLisata) {
                    nykyinenHakemisto.lisaa(tiedosto);
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    /**Mv-komennolla parametrina annettun tiedoston nimeä muutetaan.
     * 
     * @param nimettava, hakemiston tai tiedoston nimi joka muutetaan.
     * @param uusiNimi, tiedoston uusi nimi.
     * @return true, jos tiedoston uudelleen nimeäminen onnistuu ja false, jos ei.
     */
    public boolean tiedonUudelleenNimeaminen(String nimettava, String uusiNimi) {
        //tarkastetaan ettei uutta nimeä löydy hakemistosta ja asetetaan sille lippumuuttuja(true tai false)
        boolean eiLoydy = true;
        for (int i = 0; i < nykyinenHakemisto.sisalto().size(); i++) {
            if (nykyinenHakemisto.sisalto().get(i).equals(uusiNimi)) {
                eiLoydy = false;
            }
        }
        //tarkastetaan että uudelleen nimettävä löytyy hakemistosto ja asetetaan sille lippumuuttuja
        boolean nimettavaLoytyy = false;
        for (int i = 0; i < nykyinenHakemisto.sisalto().size(); i++ ) {
            if (nykyinenHakemisto.sisalto().get(i).equals(nimettava)) {
                nimettavaLoytyy = true;
            }
        }
        if (eiLoydy && nimettavaLoytyy) {
            try {
                //luodaan Tieto-tyyppinen LinkedList johon haetaan uudelleen nimettävä tiedosto
                LinkedList<Tieto> hakuTulokset = new LinkedList<>();
                hakuTulokset = nykyinenHakemisto.hae(nimettava);
                //jos samannimistä tiedostoa ei löydy palautetaan false
                if (hakuTulokset.isEmpty()) {
                    return false;
                //muuten nimetään tiedosto 
                } else {
                    hakuTulokset.get(0).nimi(new StringBuilder(uusiNimi));
                    nykyinenHakemisto.poista(hakuTulokset.get(0));
                    nykyinenHakemisto.lisaa(hakuTulokset.get(0));
                }
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
    
    /**Ls-komennolla käyttäjä voi antaa listattavaksi hakemiston, yhden tai useamman
     * tiedoston.
     * 
     * metodi tulostaa parametrina annetun hakemiston tai tiedoston tiedot aakkosjärjestyksessä
     * ruudulle
     * 
     * @param hakusana, kertoo minkälaisia tiedostoja/hakemistoja listataan eli tulostetaan
     * näytölle.
     * @return true, jos hakemiston/tiedoston listaus onnistuu ja false, jos ei.
     */
    public boolean listaaHakemisto(String hakusana) {
        //käytetään Tieto-luokan equals(String hakusana)-metodia, jolla etsimme,
        //mitkä hakemiston hakemistoista/tiedostoista sopivat hakusanaan kun hakusanassa on
        //jokerimerkkejä, jos niitä ei ole käytetään silmukkaa, tässä metodissa.
        //tutkitaan ensin onko hakusanassa jokerimerkkejä
        int jokerimerkit = 0;
        for (int i = 0; i < hakusana.length(); i++) {
            char apu = hakusana.charAt(i);
            if (apu == '*') {
                jokerimerkit++;
            }
        }
        try {
        //jos jokerimerkkejä on nolla voi hakusanalle olla vain yksi pari kyseisessä hakemistossa
        //joten etsitään tämä pari ja tulostetaan se.
        if (jokerimerkit == 0 && !hakusana.equals(".")) {
            //boolean-tyyppinen lippumuuttuja että vastinpari on löytynyt
            boolean pariLoydetty = false;
            for (int i = 0; i < nykyinenHakemisto.sisalto().size();i++) {
                if (hakusana.equals(nykyinenHakemisto.sisalto().get(i).nimi().toString())) {
                    System.out.println(nykyinenHakemisto.sisalto().get(i).toString());
                    pariLoydetty = true;
                }
            }
            if (!pariLoydetty) {
                return false;
            }
        //Ohjelma antaa parametrina pisteen(.) kun käyttäjän syötteessä ei ollut parametria,
        //jolloin koko hakemiston sisältö tulostetaan.
        } else if(hakusana.equals(".")) {
            for (int i = 0; i < nykyinenHakemisto.sisalto().size(); i++) {
                System.out.println(nykyinenHakemisto.sisalto().get(i).toString());
            }

        } else if (hakusana.equals("*") && jokerimerkit == 1) {
            for (int i = 0; i < nykyinenHakemisto.sisalto().size();i++) {
                System.out.println(nykyinenHakemisto.sisalto().get(i).toString());
            }
            
        } else if (jokerimerkit > 0) {
            //boolean-tyyppinen lippumuuttuja että listattavaa on löytynyt
            boolean listattavaaLoytynyt = false;
            //kertoo, kun hakusana jokerimerkkien kanssa käy tiedon nimeen
            //ja tämä tieto silloin tulostetaan.
            boolean tulostetaanko = false;
            for (int i = 0; i < nykyinenHakemisto.sisalto().size();i++) {
                tulostetaanko = nykyinenHakemisto.sisalto().get(i).equals(hakusana);
                if (tulostetaanko) {
                    System.out.println(nykyinenHakemisto.sisalto().get(i).toString());
                    listattavaaLoytynyt = true;
                }
            }
            
            if (!listattavaaLoytynyt) {
                return false;
            }
        } else {
            return false;
        }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**Rm-komennolla käyttäjä voi poistaa hakemiston tai tiedoston
     * 
     * @param poistettava, kertoo minkä nimistä tietoa hakemistosta poistetaan, jokerimerkkien
     * avulla voi myös poistua useampia tiedostoja.
     * @return true, jos hakemiston/tiedoston poistaminen onnistuu ja false, jos ei.
     */
    public boolean poisto(String poistettava) {
        try {
            LinkedList<Tieto> hakuTulokset = new LinkedList<>();
            hakuTulokset = nykyinenHakemisto.hae(poistettava);
            if (hakuTulokset.isEmpty()) {
                return false;
            }
            for (int i = 0; i < hakuTulokset.size(); i++) {
                nykyinenHakemisto.poista(hakuTulokset.get(i));
            }
            } catch (Exception e) {
                return false;
            }
        return true;
    }
}
