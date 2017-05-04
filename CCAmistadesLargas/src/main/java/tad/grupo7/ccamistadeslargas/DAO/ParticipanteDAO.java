/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import static tad.grupo7.ccamistadeslargas.DAO.EventoDAO.session;
import tad.grupo7.ccamistadeslargas.modelo.Evento;
import tad.grupo7.ccamistadeslargas.modelo.Participante;

/**
 *
 * @author cayetano
 */
public class ParticipanteDAO {

    static final AnnotationConfiguration configuration = new AnnotationConfiguration().addPackage("net.srirangan.packt.maven.TestHibernateApp.domain").addAnnotatedClass(Participante.class);
    static Session session = null;

    public static void create(Participante p) {
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(p);
        tx.commit();
        session.close();
    }

    public static void update(int id, String nombre, String icono, int Usuario_idUsuario) {
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Participante WHERE idParticipante = " + id);
        Participante p = (Participante) q.uniqueResult();
        p.setNombre(nombre);
        p.setIcono(icono);
        p.setUsuario_idUsuario(Usuario_idUsuario);
        session.update(p);
        tx.commit();
        session.close();
    }

    public static Participante read(int id) {
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Participante WHERE idParticipante = " + id);
        Participante p = (Participante) q.uniqueResult();
        tx.commit();
        session.close();
        return p;
    }

    public static List<Participante> readAll(int idEvento) {
        List<Integer> participantesID = Participante_has_EventoDAO.readAll(idEvento);
        List<Participante> participantes = new ArrayList<>();
        session = configuration.buildSessionFactory().openSession();
        for (Integer id : participantesID) {
            org.hibernate.Transaction tx = session.beginTransaction();
            Query q = session.createQuery("from Participante WHERE idParticipante = " + id);
            Participante p = (Participante) q.uniqueResult();
            participantes.add(p);
            tx.commit();
        }
        session.close();
        return participantes;
    }

    public static void delete(int id) {
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Participante WHERE idParticipante = " + id);
        Participante p = (Participante) q.uniqueResult();
        session.delete(p);
        tx.commit();
        session.close();
    }
}
