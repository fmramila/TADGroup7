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
    private int Usuario_idUsuario;
    @Column
    private int Evento_idEvento;
    
    public Gasto(){
        
    }

    public Gasto(String nombre, Integer precio, int Usuario_idUsuario, int Evento_idEvento) {
        this.nombre = nombre;
        this.precio = precio;
        this.Usuario_idUsuario = Usuario_idUsuario;
        this.Evento_idEvento = Evento_idEvento;
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

    public int getUsuario_idUsuario() {
        return Usuario_idUsuario;
    }

    public void setUsuario_idUsuario(int Usuario_idUsuario) {
        this.Usuario_idUsuario = Usuario_idUsuario;
    }

    public int getEvento_idEvento() {
        return Evento_idEvento;
    }

    public void setEvento_idEvento(int Evento_idEvento) {
        this.Evento_idEvento = Evento_idEvento;
    }
    
    public Object[] getArray(){
        Usuario u = UsuarioDAO.read(Usuario_idUsuario);
        return new Object[]{getNombre(),getPrecio(),u.getNombre()};
    }
    
    
}
