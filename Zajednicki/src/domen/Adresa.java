/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;

/**
 *
 * @author student1
 */
public class Adresa implements Serializable{
    private String ulica;
    private String broj;
    private Mesto mesto;

    public Adresa(String ulica, String broj, Mesto mesto) {
        this.ulica = ulica;
        this.broj = broj;
        this.mesto = mesto;
    }

    public Adresa() {
    }
    
    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @Override
    public String toString() {
        return ulica + " " + broj + ", " + mesto;
    }

    @Override
    public boolean equals(Object obj) {
        Adresa a = (Adresa) obj;
        if (ulica.equals(a.ulica) && broj.equals(a.broj) && mesto.equals(a.mesto)) {
            return true;
        }
        return false;
    }

}
