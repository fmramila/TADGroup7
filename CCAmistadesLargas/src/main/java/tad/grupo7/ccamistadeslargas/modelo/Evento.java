/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.modelo;

import java.util.List;
import org.bson.types.ObjectId;


public class Evento {

    private ObjectId id;
    private String nombre;
    private String divisa;
    private ObjectId idCreador;
    private List<Participante> participantes;

    public Evento(ObjectId id, String nombre, String divisa, ObjectId idCreador, List<Participante> participantes) {
        this.id = id;
        this.nombre = nombre;
        this.divisa = divisa;
        this.idCreador = idCreador;
        this.participantes = participantes;
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

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public ObjectId getIdCreador() {
        return idCreador;
    }

    public void setIdCreador(ObjectId idCreador) {
        this.idCreador = idCreador;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    public String[] getArray() {
        return new String[]{getNombre(),getDivisa()};
    }
    
    
    

   

    
    
}