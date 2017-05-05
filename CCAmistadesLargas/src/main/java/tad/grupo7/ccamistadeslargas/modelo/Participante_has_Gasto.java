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
public class Participante_has_Gasto {
    @Id
    @Column
    private int idParticipante_Has_Gasto ;
    @Column
    private int Participante_idParticipante;
    @Column
    private int Gasto_idGasto;

    public Participante_has_Gasto() {
    }

    public Participante_has_Gasto(int Participante_idParticipante, int Gasto_idGasto) {
        this.Participante_idParticipante = Participante_idParticipante;
        this.Gasto_idGasto = Gasto_idGasto;
    }

    public int getIdParticipante_Has_Gasto() {
        return idParticipante_Has_Gasto;
    }

    public void setIdParticipante_Has_Gasto(int idParticipante_Has_Gasto) {
        this.idParticipante_Has_Gasto = idParticipante_Has_Gasto;
    }

    public int getParticipante_idParticipante() {
        return Participante_idParticipante;
    }

    public void setParticipante_idParticipante(int Participante_idParticipante) {
        this.Participante_idParticipante = Participante_idParticipante;
    }

    public int getGasto_idGasto() {
        return Gasto_idGasto;
    }

    public void setGasto_idGasto(int Gasto_idGasto) {
        this.Gasto_idGasto = Gasto_idGasto;
    }

    
    
    
    
}
