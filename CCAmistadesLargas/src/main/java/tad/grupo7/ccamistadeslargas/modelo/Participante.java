/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
nombre
amigoDe:usuario
 */
package tad.grupo7.ccamistadeslargas.modelo;

import org.bson.types.ObjectId;

/**
 *
 * @author naiara
 */
public class Participante {
    private ObjectId id;
    private String nombre;
    private String idAmigoDe;

    public Participante(ObjectId id, String nombre, String idAmigoDe) {
        this.id = id;
        this.nombre = nombre;
        this.idAmigoDe = idAmigoDe;
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

    public String getIdAmigoDe() {
        return idAmigoDe;
    }

    public void setIdAmigoDe(String idAmigoDe) {
        this.idAmigoDe = idAmigoDe;
    }

    public Object[] getArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    

    
}
