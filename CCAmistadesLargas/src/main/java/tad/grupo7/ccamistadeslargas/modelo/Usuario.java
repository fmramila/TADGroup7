
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
nombre
password
email
amigos:{participante1, particpante2,...}
*/
package tad.grupo7.ccamistadeslargas.modelo;

import java.util.List;
import org.bson.types.ObjectId;


/**
 *
 * @author cayetano
 */
public class Usuario {

    private ObjectId id;
    private String nombre;
    private String password;
    private String email;
    private List<Participante> amigos;

    public Usuario(ObjectId id, String nombre, String password, String email, List<Participante> amigos) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.amigos = amigos;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Participante> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<Participante> amigos) {
        this.amigos = amigos;
    }

     public String[] getArray() {
        return new String[]{getNombre(),getPassword(),getEmail()};
    }

    
}
