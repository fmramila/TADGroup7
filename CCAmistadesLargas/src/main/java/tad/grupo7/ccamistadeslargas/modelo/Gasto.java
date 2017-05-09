/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.modelo;

import java.util.List;
import org.bson.types.ObjectId;
import tad.grupo7.ccamistadeslargas.DAO.ParticipanteDAO;


/**
 *
 * @author cayetano
 */
public class Gasto {

    private ObjectId id;
    private String nombre;
    private Double precio;
    private ObjectId idEvento;
    private ObjectId idPagador;
    private List<Participante> deudores;

    public Gasto(ObjectId id, String nombre, Double precio, ObjectId idEvento, ObjectId idPagador, List<Participante> deudores) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.idEvento = idEvento;
        this.idPagador = idPagador;
        this.deudores = deudores;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public ObjectId getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(ObjectId idEvento) {
        this.idEvento = idEvento;
    }

    public ObjectId getIdPagador() {
        return idPagador;
    }

    public void setIdPagador(ObjectId idPagador) {
        this.idPagador = idPagador;
    }

    public List<Participante> getDeudores() {
        return deudores;
    }

    public void setDeudores(List<Participante> deudores) {
        this.deudores = deudores;
    }

    public Object[] getArray() {
        String deudores = "";
        for(Participante p : getDeudores()){
            deudores += p.getNombre() + " ";
        }
        return new Object[]{getNombre(),getPrecio(),ParticipanteDAO.read(getIdPagador()).getNombre(),deudores};
    }
}
