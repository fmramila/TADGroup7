/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.modelo;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 *
 * @author cayetano
 */
public class Usuario_has_Gasto {
    @Id
    @Column
    private int Id_UsuarioGasto ;
    @Column
    private int Usuario_idUsuario;
    @Column
    private int Gasto_idGasto;

    public Usuario_has_Gasto(int Usuario_idUsuario, int Gasto_idGasto) {
        this.Usuario_idUsuario = Usuario_idUsuario;
        this.Gasto_idGasto = Gasto_idGasto;
    }

    public int getId_UsuarioGasto() {
        return Id_UsuarioGasto;
    }

    public void setId_UsuarioGasto(int Id_UsuarioGasto) {
        this.Id_UsuarioGasto = Id_UsuarioGasto;
    }

    public int getUsuario_idUsuario() {
        return Usuario_idUsuario;
    }

    public void setUsuario_idUsuario(int Usuario_idUsuario) {
        this.Usuario_idUsuario = Usuario_idUsuario;
    }

    public int getGasto_idGasto() {
        return Gasto_idGasto;
    }

    public void setGasto_idGasto(int Gasto_idGasto) {
        this.Gasto_idGasto = Gasto_idGasto;
    }
    
    
    
}
