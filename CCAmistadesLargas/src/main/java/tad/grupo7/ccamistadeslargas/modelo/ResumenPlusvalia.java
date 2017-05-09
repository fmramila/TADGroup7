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
public class ResumenPlusvalia {
    private String nombreParticipante;
    private String debePoner;
    private String debeRecibir;
    private int nGastosPagados;
    private int nGastosRecibidos;

    public ResumenPlusvalia(String nombreParticipante, String debePoner, String debeRecibir, int nGastosPagados, int nGastosRecibidos) {
        this.nombreParticipante = nombreParticipante;
        this.debePoner = debePoner;
        this.debeRecibir = debeRecibir;
        this.nGastosPagados = nGastosPagados;
        this.nGastosRecibidos = nGastosRecibidos;
    }

    public String getNombreParticipante() {
        return nombreParticipante;
    }

    public void setNombreParticipante(String nombreParticipante) {
        this.nombreParticipante = nombreParticipante;
    }

    public String getDebePoner() {
        return debePoner;
    }

    public void setDebePoner(String debePoner) {
        this.debePoner = debePoner;
    }

    public String getDebeRecibir() {
        return debeRecibir;
    }

    public void setDebeRecibir(String debeRecibir) {
        this.debeRecibir = debeRecibir;
    }

    public int getnGastosPagados() {
        return nGastosPagados;
    }

    public void setnGastosPagados(int nGastosPagados) {
        this.nGastosPagados = nGastosPagados;
    }

    public int getnGastosRecibidos() {
        return nGastosRecibidos;
    }

    public void setnGastosRecibidos(int nGastosRecibidos) {
        this.nGastosRecibidos = nGastosRecibidos;
    }

    public Object[] getArray(){
        return new Object[]{getNombreParticipante(),getDebePoner(),getDebeRecibir(),getnGastosPagados(),getnGastosRecibidos()};
    }
    
}
