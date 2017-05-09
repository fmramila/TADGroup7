/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.modelo;

import java.util.List;

/**
 *
 * @author cayetano
 */
public class Deuda {
    private String nombre;
    private List<Tupla> debePoner;
    private List<Tupla> debeRecibir;

    public Deuda(String nombre, List<Tupla> debePoner, List<Tupla> debeRecibir) {
        this.nombre = nombre;
        this.debePoner = debePoner;
        this.debeRecibir = debeRecibir;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Tupla> getDebePoner() {
        return debePoner;
    }

    public void setDebePoner(List<Tupla> debePoner) {
        this.debePoner = debePoner;
    }

    public List<Tupla> getDebeRecibir() {
        return debeRecibir;
    }

    public void setDebeRecibir(List<Tupla> debeRecibir) {
        this.debeRecibir = debeRecibir;
    }

    
    
}
