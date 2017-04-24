/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

import java.util.List;
import tad.grupo7.ccamistadeslargas.modelo.Evento;
import org.hibernate.Query;
import org.hibernate.Session;
import tad.grupo7.ccamistadeslargas.modelo.HibernateUtil;

/**
 *
 * @author Naiara
 */
public class EventoDAO {

    private Session sesion = null;

    public Evento get(String id) {
        sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = sesion.beginTransaction();
        Query q = sesion.createQuery("from Evento WHERE idEvento = '" + id + "'");
        Evento evento = (Evento) q.uniqueResult();
        tx.commit();
        return evento;
    }

    public List<Evento> getAll() {
        sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = sesion.beginTransaction();
        Query q = sesion.createQuery("from Evento");
        List<Evento> eventos = (List<Evento>) q.list();
        tx.commit();
        return eventos;
    }

    public void add(Evento e) {
         
        sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = sesion.beginTransaction();
        sesion.save(e);
        tx.commit();
    }

    public void delete(String id) {
        sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = sesion.beginTransaction();
        Query q = sesion.createQuery("from Evento where idEvento='"+id+"'");
        Evento e = (Evento)q.uniqueResult();
        sesion.delete(e);
        tx.commit();
    }

    public Evento update(String id, String nombre, String divisa) {
        sesion = HibernateUtil.getSessionFactory().getCurrentSession();
        org.hibernate.Transaction tx = sesion.beginTransaction();
        //Evento e = (Evento) sesion.load(Evento.class, id);
        Query q = sesion.createQuery("from Evento WHERE idEvento = '" + id + "'");
        Evento e = (Evento) q.uniqueResult();
        e.setNombre(nombre);
        e.setDivisa(divisa);
        sesion.update(e);
        tx.commit();
        return e;
    }
}
