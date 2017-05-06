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
public class Gasto {

    private String id;
    private String nombre;
    private Double precio;
    private String idEvento;
    private String idPagador;
    private List<Participante> deudores;

    public Gasto(String id, String nombre, Double precio, String idEvento, String idPagador, List<Participante> deudores) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.idEvento = idEvento;
        this.idPagador = idPagador;
        this.deudores = deudores;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdPagador() {
        return idPagador;
    }

    public void setIdPagador(String idPagador) {
        this.idPagador = idPagador;
    }

    public List<Participante> getDeudores() {
        return deudores;
    }

    public void setDeudores(List<Participante> deudores) {
        this.deudores = deudores;
    }

    

    
    

    
    
}
