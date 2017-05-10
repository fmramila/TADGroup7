/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.modelo;

/**
 *
 * @author cayetano
 */
public class ResumenPagoPorPersona {
    private String debePagar;
    private String debePoner;
    
    public ResumenPagoPorPersona(String debePagar, String debePoner) {
        this.debePagar = debePagar;
        this.debePoner = debePoner;
    }

    public String getDebePagar() {
        return debePagar;
    }

    public void setDebePagar(String debePagar) {
        this.debePagar = debePagar;
    }

    public String getDebePoner() {
        return debePoner;
    }

    public void setDebePoner(String debePoner) {
        this.debePoner = debePoner;
    }
    
    public String[] getArray(){
        return new String[]{getDebePagar(),getDebePoner()};
    }

}
