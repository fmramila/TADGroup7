/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import tad.grupo7.ccamistadeslargas.modelo.Participante_has_Evento;

/**
 *
 * @author cayetano
 */
public class Participante_has_EventoDAO {
    static final AnnotationConfiguration configuration = new AnnotationConfiguration().addPackage("net.srirangan.packt.maven.TestHibernateApp.domain").addAnnotatedClass(Participante_has_Evento.class);
    static Session session = null;
    
    public static void create(Participante_has_Evento u){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(u);
        tx.commit();
        session.close();
    }
    
    public static List<Integer> readAll(int idEvento){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Participante_has_Evento WHERE Evento_idEvento="+idEvento);
        List<Integer> participantesID = (List<Integer>) q.list();
        tx.commit();
        session.close();
        return participantesID;
    }
}
