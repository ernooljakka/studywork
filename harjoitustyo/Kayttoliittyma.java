package harjoitustyo;

import harjoitustyo.apulaiset.*;

/**
 * Kayttoliittyma-luokka sisältää pääsilmukan,
 * joka vastaa ohjelman pyörittämisestä.
 * <p>
 * Olio-ohjelmoinin perusteet 2, kevät 2019
 * <p>
 * @author Erno Oljakka erno.oljakka@tuni.fi,
 * Luonnontieteiden tiedekunta
 * Tampereen yliopisto.
 */
public class Kayttoliittyma {
    
    //luokkavakiot
    
    /** vakioitu muuttuja virheelle */
    final String VIRHE = "Error!";
    
    //vakiot komennoille
    
    /** vakioitu komento hakemistonluontiin. */
    final String HAKEMISTONLUONTI = "md";
    
    /** vakioitu komento hakemiston vaihtoon. */
    final String VAIHTO = "cd";
    
    /** vakioitu komento tiedostonluontiin. */
    final String TIEDOSTONLUONTI = "mf";
    
    /** vakioitu komento tiedoston tai hakemiston uudelleen nimeämiseen. */
    final String UUDELLEENNIMEAMINEN = "mv";
    
    /** vakioitu komento hakemiston sisällön listaukseen. */
    final String LISTAUS = "ls";
    
    /** vakioitu komento tiedoston tai hakemiston poistoon. */
    final String POISTO = "rm";
    
    /** vakioitu komento ohjelman lopetukselle. */
    final String LOPETUS = "exit";
    
    //attribuutit
    
    private Tulkki tulkki;
     
    //rakentajat
    
    public Kayttoliittyma() {    
    }
    
    //aksessorit
    
    public Tulkki tulkki() {
        return tulkki;
    }
    
    public void tulkki(Tulkki uusiTulkki) {
        tulkki = uusiTulkki;
    }
    
    
    //luokkametodit
    
    
    /** 
     * Metodikäynnistää ohjelman ja jatkaa sitä kunnes
     * käyttäjä sammuttaa sen komennolla "exit"
     */
    public void kaynnistaOhjelma() {
        
        //lippumuuttuja sille jatketaanko ohjelman suorittamista
        boolean jatketaanko = true;
        
        //muuttuja käyttäjen syotteelle
        String syote = "";
        
        //tulostetaan tervehdys käyttäjälle ja luodaan juurihakemisto
        System.out.println("Welcome to SOS.");
        //juurihakemisto luodaan Tulkki-olioon Tulkki-luokan rakentajalla
        Tulkki omaTulkki = new Tulkki();
        
        //boolean muuttuja sille onko virhe tapahtunut
        boolean onnistuiko;
        
        //luodaan silmukka, joka jatkaa pyörimistä kunnes käyttäjä lopettaa
        //ohjelman käyttämisen exit-komennolla
        while (jatketaanko) {

            System.out.print(omaTulkki.tulostaHakemistopolku() + ">");
            //kutsutaan Tulkki-luokasta tulostaHakemistopolku-metodia
            
            //luetaan käyttäjältä komento ja mahdolliset parametrit
            syote = In.readString();
            
            //muuttujat kahdelle mahdolliselle parametrille
            String ekaParametri = "";
            String tokaParametri = "";
            //käytetään String-luokan split-metodia ja paloitellaan string, jotta saamme eroteltua 
            //komennot ja parametrit toisistaan. Sijoitetaan saadut arvot taulukkoon.
            String [] syoteTaulu = syote.split(" ");
            
            //muutetaan taulukon ensimmäinen alkio String-tyyppiseksi
            //näin voimme tutkia onko komento tunnettu vai ei
            //mahdollinen ensimmäinen parametri
            String komento = syoteTaulu[0];
            if (syoteTaulu.length > 1) {
                ekaParametri = syoteTaulu[1];
            }
            //mahdollinen toinen parametri
            if (syoteTaulu.length > 2) {
                tokaParametri= syoteTaulu[2];
            }
            
            //tarkastetaan että syote on oikeellinen
            onnistuiko = valilyonnitOikein(syote);
            
            if (syoteTaulu.length > 3) {
                onnistuiko = false;
            }
            else if (komento.equals(LOPETUS)) {
                System.out.println("Shell terminated.");
                jatketaanko = false;
            } else if (komento.equals(HAKEMISTONLUONTI) || komento.equals(VAIHTO) || komento.equals(TIEDOSTONLUONTI) ||
                komento.equals(UUDELLEENNIMEAMINEN) || komento.equals(LISTAUS) || komento.equals(POISTO)) {

                switch (komento) {
                    case HAKEMISTONLUONTI:
                        onnistuiko = omaTulkki.alihakemistonLuonti(ekaParametri);
                        break;
                    case VAIHTO:
                        if (ekaParametri.isEmpty()) {
                            omaTulkki.hakemistonVaihto(".");
                        } else {
                            onnistuiko = omaTulkki.hakemistonVaihto(ekaParametri);
                        }   break;
                    case TIEDOSTONLUONTI:
                        try {
                            onnistuiko = omaTulkki.luoTiedosto(ekaParametri, Integer.parseInt(tokaParametri));
                        } catch (NumberFormatException e) {
                            onnistuiko = false;
                        }   break;
                    case UUDELLEENNIMEAMINEN:
                        onnistuiko = omaTulkki.tiedonUudelleenNimeaminen(ekaParametri, tokaParametri);
                        break;
                    case LISTAUS:
                        if (syoteTaulu.length > 2) {
                            onnistuiko = false;
                        } else if (ekaParametri.isEmpty()) {
                            omaTulkki.listaaHakemisto(".");
                        } else {
                            onnistuiko = omaTulkki.listaaHakemisto(ekaParametri);
                        }   break;
                    case POISTO:
                        if (syoteTaulu.length > 2) {
                            onnistuiko = false;
                        } else if (ekaParametri.isEmpty()) {
                            onnistuiko = false;
                        } else {
                            onnistuiko = omaTulkki.poisto(ekaParametri);
                        }   break;
                    default:
                        break;
                }
                       
            } else {
                onnistuiko = false;
            }
                if (onnistuiko == false) {
                System.out.println(VIRHE);
            }
        }
    }
    
    
    /**
    * Metodissa tutkitaan onko käyttäjän antamassa syotteessä virheellistä
    * välilyönnin käyttöä.
    * 
    * @param syote, käyttäjän antama syöte.
    * @return true, kun käyttäjän antama syöte on oikeanlainen 
    * ja false, jos syöte on virheellinen.
    */
    public static boolean valilyonnitOikein(String syote) {

        //tutkitaan ensin onko komennon ja/tai parametrien välissä ylimääräisiä välilyöntejä.
        //myös ennen komentoa oleva välilöynti on virhe.
        if (syote.charAt(0) == ' ') {
            return false;
        }
        //tutkitaan for-silmukalla onko syotteessä useampia välilyöntejä peräkkäin
        for (int i = 0; i < syote.length() - 1; i++) {
            char apu = syote.charAt(i);
            if (apu == ' ' && syote.charAt(i + 1) == ' ') {
                return false;
            }
        }
        return true;
    }
    
    
}
