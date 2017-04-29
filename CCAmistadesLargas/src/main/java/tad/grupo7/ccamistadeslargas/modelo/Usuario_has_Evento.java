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
public class Usuario_has_Evento {

    @Id
    @Column
    private int Id_UsuarioEvento;
    @Column
    private int Usuario_idUsuario;
    @Column
    private int Evento_idEvento;

    public Usuario_has_Evento() {

    }

    public Usuario_has_Evento(int Usuario_idUsuario, int Evento_idEvento) {
        this.Usuario_idUsuario = Usuario_idUsuario;
        this.Evento_idEvento = Evento_idEvento;
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

    public int getId_UsuarioEvento() {
        return Id_UsuarioEvento;
    }

    public void setId_UsuarioEvento(int Id_UsuarioEvento) {
        this.Id_UsuarioEvento = Id_UsuarioEvento;
    }
    
    

}
