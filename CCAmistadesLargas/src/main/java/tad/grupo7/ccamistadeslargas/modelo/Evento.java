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

@Table
@Entity
public class Evento {

    @Column
    @Id
    private int idEvento;
    @Column
    private String nombre;
    @Column
    private String divisa;
    @Column
    private int Usuario_idUsuario;

    public Evento() {
    }

    public Evento(String nombre, String divisa, int Usuario_idUsuario) {
        this.nombre = nombre;
        this.divisa = divisa;
        this.Usuario_idUsuario = Usuario_idUsuario;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public int getUsuario_idUsuario() {
        return Usuario_idUsuario;
    }

    public void setUsuario_idUsuario(int Usuario_idUsuario) {
        this.Usuario_idUsuario = Usuario_idUsuario;
    }

    public String[] getArray() {
        return new String[]{getNombre(),getDivisa()};
    }

    
    
}