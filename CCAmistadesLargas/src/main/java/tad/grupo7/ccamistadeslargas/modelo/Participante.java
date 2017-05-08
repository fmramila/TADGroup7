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
    private ObjectId idAmigoDe;

    public Participante(ObjectId id, String nombre, ObjectId idAmigoDe) {
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

    public ObjectId getIdAmigoDe() {
        return idAmigoDe;
    }

    public void setIdAmigoDe(ObjectId idAmigoDe) {
        this.idAmigoDe = idAmigoDe;
    }

    public String[] getArray() {
        return new String[]{getNombre()};
    }
    
    

    

    
}
