/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

import org.hibernate.Query;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import tad.grupo7.ccamistadeslargas.modelo.Usuario_has_Gasto;

/**
 *
 * @author cayetano
 */
public class Usuario_has_GastoDAO {
    static final AnnotationConfiguration configuration = new AnnotationConfiguration().addPackage("net.srirangan.packt.maven.TestHibernateApp.domain").addAnnotatedClass(Usuario_has_Gasto.class);
    static Session session = null;
    
    public static void create(Usuario_has_Gasto u){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(u);
        tx.commit();
        session.close();
    }
    
    public static Usuario_has_Gasto read(int idUsuario, int idGasto){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Usuario_has_Gasto WHERE Usuario_idUsuario = "+idUsuario+" AND Gasto_idGasto = "+idGasto);
        Usuario_has_Gasto u = (Usuario_has_Gasto) q.uniqueResult();
        tx.commit();
        session.close();
        return u;
    }
    
    public static void delete(int idUsuario, int idGasto){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Usuario_has_Gasto WHERE Usuario_idUsuario = "+idUsuario+" AND Gasto_idGasto = "+idGasto);
        Usuario_has_Gasto u = (Usuario_has_Gasto) q.uniqueResult();
        session.delete(u);
        tx.commit();
        session.close();
    }
}
