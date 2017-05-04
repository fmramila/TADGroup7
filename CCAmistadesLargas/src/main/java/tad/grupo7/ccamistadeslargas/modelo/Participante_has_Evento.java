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
public class Participante_has_Evento {

    @Id
    @Column
    private int idParticipante_Has_Evento;
    @Column
    private int Participante_idParticipante;
    @Column
    private int Evento_idEvento;

    public Participante_has_Evento() {

    }

    public Participante_has_Evento(int Participante_idParticipante, int Evento_idEvento) {
        this.Participante_idParticipante = Participante_idParticipante;
        this.Evento_idEvento = Evento_idEvento;
    }

    public int getIdParticipante_Has_Evento() {
        return idParticipante_Has_Evento;
    }

    public void setIdParticipante_Has_Evento(int idParticipante_Has_Evento) {
        this.idParticipante_Has_Evento = idParticipante_Has_Evento;
    }

    public int getParticipante_idParticipante() {
        return Participante_idParticipante;
    }

    public void setParticipante_idParticipante(int Participante_idParticipante) {
        this.Participante_idParticipante = Participante_idParticipante;
    }

    public int getEvento_idEvento() {
        return Evento_idEvento;
    }

    public void setEvento_idEvento(int Evento_idEvento) {
        this.Evento_idEvento = Evento_idEvento;
    }

   
    
    

}
