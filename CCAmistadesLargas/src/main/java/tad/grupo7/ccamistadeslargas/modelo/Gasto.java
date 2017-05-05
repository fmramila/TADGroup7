/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import tad.grupo7.ccamistadeslargas.DAO.UsuarioDAO;

/**
 *
 * @author cayetano
 */
@Table
@Entity
public class Gasto {

    @Column
    @Id
    private int idGasto;
    @Column
    private String nombre;
    @Column
    private Integer precio;
    @Column
    private int Evento_idEvento;
    @Column
    private int Participante_idParticipante;
    
    public Gasto(){
        
    }

    public Gasto(String nombre, Integer precio, int Evento_idEvento, int Participante_idParticipante) {
        this.nombre = nombre;
        this.precio = precio;
        this.Evento_idEvento = Evento_idEvento;
        this.Participante_idParticipante = Participante_idParticipante;
    }

    public int getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public int getEvento_idEvento() {
        return Evento_idEvento;
    }

    public void setEvento_idEvento(int Evento_idEvento) {
        this.Evento_idEvento = Evento_idEvento;
    }

    public int getParticipante_idParticipante() {
        return Participante_idParticipante;
    }

    public void setParticipante_idParticipante(int Participante_idParticipante) {
        this.Participante_idParticipante = Participante_idParticipante;
    }

    
    public Object[] getArray(){
        Usuario u = UsuarioDAO.read(Participante_idParticipante);
        return new Object[]{getNombre(),getPrecio(),u.getNombre()};
    }
    
    
}
