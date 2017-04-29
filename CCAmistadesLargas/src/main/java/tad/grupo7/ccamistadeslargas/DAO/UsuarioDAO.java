/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

import org.hibernate.Query;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author cayetano
 */
public class UsuarioDAO {
    static final AnnotationConfiguration configuration = new AnnotationConfiguration().addPackage("net.srirangan.packt.maven.TestHibernateApp.domain").addAnnotatedClass(Usuario.class);
    static Session session = null;
    
    public static void create(Usuario u){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(u);
        tx.commit();
        session.close();
    }
    
    public static void update(int id, String nombre, String password, String email, String icono){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Usuario WHERE idUsuario = "+id);
        Usuario u = (Usuario) q.uniqueResult();
        u.setNombre(nombre);
        u.setPassword(password);
        u.setEmail(email);
        u.setIcono(icono);
        session.update(u);
        tx.commit();
        session.close();
    }
    
    public static Usuario read(int id){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Usuario WHERE idUsuario = "+id);
        Usuario u = (Usuario) q.uniqueResult();
        tx.commit();
        session.close();
        return u;
    }
    
    public static void delete(int id){
        session = configuration.buildSessionFactory().openSession();
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from Usuario WHERE idUsuario = "+id);
        Usuario u = (Usuario) q.uniqueResult();
        session.delete(u);
        tx.commit();
        session.close();
    }
}
