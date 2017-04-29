/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

import org.hibernate.Query;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import tad.grupo7.ccamistadeslargas.modelo.Usuario_has_Evento;

/**
 *
 * @author cayetano
 */
public class Usuario_has_EventoDAO {
    static final AnnotationConfiguration configuration = new AnnotationConfiguration().addPackage("net.srirangan.packt.maven.TestHibernateApp.domain").addAnnotatedClass(Usuario_has_Evento.class);
    static Session session = null;
    
    public static void create(Usuario_has_Evento u){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(u);
        tx.commit();
        session.close();
    }
    
    public static Usuario_has_Evento read(int idUsuario, int idEvento){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Usuario_has_Evento WHERE Usuario_idUsuario = "+idUsuario+" AND Evento_idEvento = "+idEvento);
        Usuario_has_Evento u = (Usuario_has_Evento) q.uniqueResult();
        tx.commit();
        session.close();
        return u;
    }
    
    public static void delete(int idUsuario, int idEvento){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Usuario_has_Evento WHERE Usuario_idUsuario = "+idUsuario+" AND Evento_idEvento = "+idEvento);
        Usuario_has_Evento u = (Usuario_has_Evento) q.uniqueResult();
        session.delete(u);
        tx.commit();
        session.close();
    }
}
