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
public class Amistades {
    @Column
    @Id
    private int idAmistades;
    @Column
    private String nombre;
    @Column
    private String icono;
    @Column
    private int Usuario_idUsuario;
    
    public Amistades(){
        
    }

    public Amistades(String nombre, String icono, int Usuario_idUsuario) {
        this.nombre = nombre;
        this.icono = icono;
        this.Usuario_idUsuario = Usuario_idUsuario;
    }

    public int getIdAmistades() {
        return idAmistades;
    }

    public void setIdAmistades(int idAmistades) {
        this.idAmistades = idAmistades;
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
    
    
}
