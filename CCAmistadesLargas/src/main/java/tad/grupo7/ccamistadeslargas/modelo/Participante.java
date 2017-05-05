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

/**
 *
 * @author cayetano
 */
@Table
@Entity
public class Participante {
    @Column
    @Id
    private int idParticipante;
    @Column
    private String nombre;
    @Column
    private String icono;
    @Column
    private int Usuario_idUsuario;
    
    public Participante(){
        
    }

    public Participante(String nombre, String icono, int Usuario_idUsuario) {
        this.nombre = nombre;
        this.icono = icono;
        this.Usuario_idUsuario = Usuario_idUsuario;
    }

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }
   
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public int getUsuario_idUsuario() {
        return Usuario_idUsuario;
    }

    public void setUsuario_idUsuario(int Usuario_idUsuario) {
        this.Usuario_idUsuario = Usuario_idUsuario;
    }
    
    public String[] getArray(){
        return new String[]{getNombre()};
    }
    
}
