/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

import org.hibernate.Query;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import tad.grupo7.ccamistadeslargas.modelo.Amistades;

/**
 *
 * @author cayetano
 */
public class AmistadesDAO {
    static final AnnotationConfiguration configuration = new AnnotationConfiguration().addPackage("net.srirangan.packt.maven.TestHibernateApp.domain").addAnnotatedClass(Amistades.class);
    static Session session = null;
    
    public static void create(Amistades a){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(a);
        tx.commit();
        session.close();
    }
    
    public static void update(int id, String nombre, String icono, int Usuario_idUsuario){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Amistades WHERE idAmistades = "+id);
        Amistades a = (Amistades) q.uniqueResult();
        a.setNombre(nombre);
        a.setIcono(icono);
        a.setUsuario_idUsuario(Usuario_idUsuario);
        session.update(a);
        tx.commit();
        session.close();
    }
    
    public static Amistades read(int id){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Amistades WHERE idAmistades = "+id);
        Amistades a = (Amistades) q.uniqueResult();
        tx.commit();
        session.close();
        return a;
    }
    
    public static void delete(int id){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Amistades WHERE idAmistades = "+id);
        Amistades a = (Amistades) q.uniqueResult();
        session.delete(a);
        tx.commit();
        session.close();
    }
}
