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
    private String haPagado;
    private String haGastado;
    
    public ResumenPagoPorPersona(String haPagado, String haGastado) {
        this.haPagado = haPagado;
        this.haGastado = haGastado;
    }

    public String getHaPagado() {
        return haPagado;
    }

    public void setHaPagado(String haPagado) {
        this.haPagado = haPagado;
    }

    public String getHaGastado() {
        return haGastado;
    }

    public void setHaGastado(String haGastado) {
        this.haGastado = haGastado;
    }
    
    public String[] getArray(){
        return new String[]{getHaPagado(),getHaGastado()};
    }

}
