package harjoitustyo.tiedot;

import harjoitustyo.apulaiset.Syvakopioituva;

/**Tiedosto-luokka sisältää tiedosto-olioille tiedot.
*  <p>
*  Olio-ohjelmoinin perusteet 2, kevät 2019
*  <p>
*  @author Erno Oljakka (erno.oljakka@tuni.fi,
*  Luonnontieteiden tiedekunta,
*  Tampereen yliopisto.
*/

public class Tiedosto extends Tieto implements Syvakopioituva<Tiedosto> {
    
    //attribuutit
    
    //attribuutti koko kertoo tiedoston koon tavuina
    /** muuttuja tiedoston koolle (tavuina). */
    private int koko;
    
    
    //rakentajat
        
    public Tiedosto() {
        koko = 0;
    }
    
    public Tiedosto(StringBuilder uusiNimi, int uusiKoko) throws IllegalArgumentException {
        super(uusiNimi);
        koko(uusiKoko); 
    }
    
    
    //aksessorit
    
    public int koko() {
        return koko;
    }
    
    /**
     * 
     * @param uusiKoko, tiedostolle asetettava uusi koko.
     * @throws IllegalArgumentException jos tiedoston koko on virheellinen.
     * eli pienempi kuin 0.
     */
    public void koko(int uusiKoko) throws IllegalArgumentException {
        if (uusiKoko < 0) {
            throw new IllegalArgumentException();
        } else {
            koko = uusiKoko;
        }
    }
    
    
    //ilmentymämetodit
    
    
    //object-luokan korvatut metodit
    
    /**Object-luokan korvattu toString-metodi.
     * 
     * @return Tieto-luokan toString-metodin palauttavan merkkijonon sekä
     * tiedoston koon.
     */
    @Override 
    public String toString() {
        return super.toString() + " " + koko;
    }

    
    //Syvakopioituva-rajapinnan metodien toteutus

    @Override
    public Tiedosto kopioi() {
        return null;
        
    }
    
}
    
