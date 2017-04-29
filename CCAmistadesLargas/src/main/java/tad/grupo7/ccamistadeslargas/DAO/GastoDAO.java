/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

import org.hibernate.Query;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import tad.grupo7.ccamistadeslargas.modelo.Gasto;

/**
 *
 * @author cayetano
 */
public class GastoDAO {
    static final AnnotationConfiguration configuration = new AnnotationConfiguration().addPackage("net.srirangan.packt.maven.TestHibernateApp.domain").addAnnotatedClass(Gasto.class);
    static Session session = null;
    
    public static void create(Gasto g){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(g);
        tx.commit();
        session.close();
    }
    
    public static void update(int id, String nombre, Integer precio, int Usuario_idUsuario, int Evento_idEvento){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Gasto WHERE idGasto = "+id);
        Gasto g = (Gasto) q.uniqueResult();
        g.setNombre(nombre);
        g.setPrecio(precio);
        g.setUsuario_idUsuario(Usuario_idUsuario);
        g.setEvento_idEvento(Evento_idEvento);
        session.update(g);
        tx.commit();
        session.close();
    }
    
    public static Gasto read(int id){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Gasto WHERE idGasto = "+id);
        Gasto g = (Gasto) q.uniqueResult();
        tx.commit();
        session.close();
        return g;
    }
    
    public static void delete(int id){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Gasto WHERE idGasto = "+id);
        Gasto g = (Gasto) q.uniqueResult();
        session.delete(g);
        tx.commit();
        session.close();
    }
}
