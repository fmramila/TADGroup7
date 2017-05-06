/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
nombre
amigoDe:usuario
 */
package tad.grupo7.ccamistadeslargas.modelo;

/**
 *
 * @author naiara
 */
public class Participante {
    private String id;
    private String nombre;
    private String idAmigoDe;

    public Participante(String id, String nombre, String idAmigoDe) {
        this.id = id;
        this.nombre = nombre;
        this.idAmigoDe = idAmigoDe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    
    

    

    
}
