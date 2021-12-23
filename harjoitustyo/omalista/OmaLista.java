package harjoitustyo.omalista;

import harjoitustyo.apulaiset.Ooperoiva;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**Omalista-luokka toimii ohjelman pää tietojärjestelmänä
*  sen kautta lisätään sisältöä.
*  <p>
*  Olio-ohjelmoinin perusteet 2, kevät 2019
*  <p>
*  @author Erno Oljakka (erno.oljakka@tuni.fi,
*  Luonnontieteiden tiedekunta,
*  Tampereen yliopisto.
 * @param <E>
*/

public class OmaLista<E> extends LinkedList<E> implements Ooperoiva<E> {

    @SuppressWarnings("unchecked")
    @Override
    public boolean lisaa(E uusi) {
        if (uusi instanceof Comparable && this.isEmpty()) {
            this.add(uusi);
            return true;
        } else if (uusi instanceof Comparable) {
            for (int i = 0; i < this.size(); i++) {
                int x = ((Comparable) uusi.toString()).compareTo(this.get(i).toString());
                if (x < 0) {
                    this.add(i, uusi);
                    return true;
                } else if (i == this.size() - 1) {
                    this.addLast(uusi);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int poista(E poistettava) {
        //laskuri sille kuinka monta alkiota poistetaan
        int poistetutlkm = 0;
        //etsitään alkiot jotka ovat samat kuin parametrinä saatu arvo
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) == poistettava) {
                this.remove(i);
                poistetutlkm++;
                //vähennetään i:tä yhdelle, koska lista pienenee, 
                //jotta for-silmukka ei skippaa yhtä alkiota poistetun jälkeen
                i--;
            }
        }
        return poistetutlkm;

    }

    

    
    
    
}
