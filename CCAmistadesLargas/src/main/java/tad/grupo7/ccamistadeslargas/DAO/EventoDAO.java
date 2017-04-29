/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

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
    
    public static void update(int id, String nombre, String divisa){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Evento WHERE idEvento = "+id);
        Evento e = (Evento) q.uniqueResult();
        e.setNombre(nombre);
        e.setDivisa(divisa);
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
