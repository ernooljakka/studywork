package harjoitustyo.tiedot;

import harjoitustyo.apulaiset.Sailova;
import harjoitustyo.apulaiset.Jokerimerkit;
import harjoitustyo.omalista.OmaLista;
import java.util.LinkedList;

/**Hakemisto-luokka sisältää tietoja hakemisto-olioista.
*  <p>
*  Olio-ohjelmoinin perusteet 2, kevät 2019
*  <p>
*  @author Erno Oljakka erno.oljakka@tuni.fi,
*  Luonnontieteiden tiedekunta,
*  Tampereen yliopisto.
*/

public class Hakemisto extends Tieto implements Sailova<Tieto> {
    
    //attribuutit
    
    /** sisältää hakemiston tiedot */
    private OmaLista<Tieto> sisalto;
    
    /** Hakemistolla on aina ylihakemisto, jos se ei ole juurihakemisto */
    private Hakemisto ylihakemisto;
    
    //rakentajat
    
    public Hakemisto() {
        sisalto = new OmaLista<>();
        ylihakemisto = null;
    }
    

    /**Parametrillinen rakentaja uudelle hakemistolle.
     * 
     * @param uusiNimi, hakemiston nimi.
     * @param uusiYlihakemisto, luodun hakemiston ylihakemisto.
     * @throws IllegalArgumentException jos jompikumpi parametreistä on virheellinen.
     */
    public Hakemisto(StringBuilder uusiNimi, Hakemisto uusiYlihakemisto) throws IllegalArgumentException {
        super(uusiNimi);
        ylihakemisto(uusiYlihakemisto);
        sisalto = new OmaLista<>();
    }
    
  
    
    
    //aksessorit
    
    public Hakemisto ylihakemisto() {
        return ylihakemisto;
    }
    
    public void ylihakemisto(Hakemisto uusiYlihakemisto) {
        ylihakemisto = uusiYlihakemisto;
    }      
    
    public OmaLista<Tieto> sisalto() {
        return sisalto;
    }
    
    public void sisalto(OmaLista<Tieto> uusiSisalto) {
        sisalto = uusiSisalto;
    }
    
    
    //ilmentymämetodit
    
    
    //Object-luokan korvatut metodit
    
    /**Object-luokan korvattu toString-metodi
     * 
     * @return Tieto-luokan toString-metodin antama merkkijono sekä hakemistossa
     * olevien alkioiden määrä. 
     */
    @Override
    public String toString() {
        return super.toString() + "/ " + sisalto.size();
    }
    
    //Sailova-rajapinnan metodien toteutus

    @Override
    public LinkedList<Tieto> hae(String hakusana) {
        //tutkitaan onko jokerimerkit oikeissa paikoissa
        boolean jatketaan = Jokerimerkit.jokerimerkitOikeissaPaikoissa(hakusana);
        //palautettava lista
        LinkedList<Tieto> palautettava = new LinkedList<>();
        if (jatketaan == false) {

            return palautettava;
        } else {
            for (int i = 0; i < sisalto.size(); i++) {
                if (sisalto.get(i).equals(hakusana)) {
                    palautettava.add(sisalto.get(i));
                }
            }
            return palautettava;    
        }
    }

    
    
    @Override
    public boolean lisaa(Tieto lisattava) {
        //boolean arvo sille jos lisäys onnistuu
        boolean onnistuuko = sisalto.lisaa(lisattava);
        if(onnistuuko) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean poista(Tieto poistettava) {
        //int-tyyppinen muuttuja jolla tutkitaan poistettiinko alkio
        //toisin sanoen OmaListan poista operaatio palauttaa isomman luvun kuin 0
        int poistettu = sisalto.poista(poistettava);
        
        //jos poistettu on suurempi kuin 0 palautetaan true
        if (poistettu > 0) {
            return true;
        } else {
            return false;
        }
    }

}
