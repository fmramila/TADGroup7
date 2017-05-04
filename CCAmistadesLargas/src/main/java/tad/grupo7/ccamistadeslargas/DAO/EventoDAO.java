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
import tad.grupo7.ccamistadeslargas.modelo.Evento;

/**
 *
 * @author cayetano
 */
public class EventoDAO {
    
    static final AnnotationConfiguration configuration =  new AnnotationConfiguration().addPackage("net.srirangan.packt.maven.TestHibernateApp.domain").addAnnotatedClass(Evento.class);
    static Session session;
    
    public static void create(Evento e){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(e);
        tx.commit();
        session.close();
    }
    
    public static void update(int id, String nombre, String divisa, int Usuario_idUsuario){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Evento WHERE idEvento = "+id);
        Evento e = (Evento) q.uniqueResult();
        e.setNombre(nombre);
        e.setDivisa(divisa);
        e.setUsuario_idUsuario(Usuario_idUsuario);
        session.update(e);
        tx.commit();
        session.close();
    }
    
    public static Evento read(int id){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Evento WHERE idEvento = "+id);
        Evento e = (Evento) q.uniqueResult();
        tx.commit();
        session.close();
        return e;
    }
    public static List<Evento> readAll(int idUsuario){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Evento WHERE Usuario_idUsuario="+idUsuario);
        List<Evento> eventos = (List<Evento>) q.list();
        tx.commit();
        session.close();
        return eventos;
    }
    
    public static void delete(int id){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Evento WHERE idEvento = "+id);
        Evento e = (Evento) q.uniqueResult();
        session.delete(e);
        tx.commit();
        session.close();
    }
}
