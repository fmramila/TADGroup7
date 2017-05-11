/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bson.types.ObjectId;
import tad.grupo7.ccamistadeslargas.modelo.Evento;
import tad.grupo7.ccamistadeslargas.modelo.Gasto;
import tad.grupo7.ccamistadeslargas.modelo.Participante;
import tad.grupo7.ccamistadeslargas.modelo.ResumenPagoPorPersona;
import tad.grupo7.ccamistadeslargas.modelo.ResumenPlusvalia;
import tad.grupo7.ccamistadeslargas.modelo.Tupla;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author cayetano
 */
public class EventoDAO {

    private static DB dataBase = new MongoClient("localhost", 27017).getDB("CC");
    private static DBCollection eventos = dataBase.getCollection("Evento");

    /**
     * Crea un evento en la BD.
     *
     * @param nombre String nombre del evento.
     * @param divisa String divisa.
     * @param creador Usuario creador.
     */
    public static void create(String nombre, String divisa, Usuario creador) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("divisa", divisa);
        document.append("idCreador", creador.getId());
        eventos.insert(document);
        ObjectId o = ParticipanteDAO.readDBObject(creador.getAmigos().get(0).getId()).getObjectId("_id");
        addParticipante(readDBObject(nombre, creador.getId()).getObjectId("_id"), o);
    }

    /**
     * Añade un participante al evento
     *
     * @param idEvento ObjectId del evento.
     * @param idParticipante ObjectId del participante.
     */
    public static void addParticipante(ObjectId idEvento, ObjectId idParticipante) {
        BasicDBList participantes = null;
        BasicDBObject participante = ParticipanteDAO.readDBObject(idParticipante);
        participantes = (BasicDBList) readDBObject(idEvento).get("participantes");
        if (participantes == null) { //Si es la primera vez
            participantes = new BasicDBList();
        }
        participantes.add(participante);
        BasicDBObject newEvento = new BasicDBObject();
        newEvento.append("$set", new BasicDBObject().append("participantes", participantes));
        BasicDBObject oldEvento = new BasicDBObject().append("_id", idEvento);
        eventos.update(oldEvento, newEvento);
    }

    /**
     * Devuelve un Evento que coincida con un ID.
     *
     * @param id ObjectId del evento.
     * @return Evento
     */
    public static Evento read(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) eventos.findOne(whereQuery);
        String nombre = document.getString("nombre");
        String divisa = document.getString("divisa");
        ObjectId idCreador = document.getObjectId("idCreador");
        Evento e = new Evento(id, nombre, divisa, idCreador, ParticipanteDAO.readAllFromEvento(id));
        return e;
    }

    /**
     * Actualiza un evento en la BD.
     *
     * @param id ObjectId del evento a actualizar.
     * @param nombre String nuevo nombre del evento.
     * @param divisa String nueva divisa del evento.
     */
    public static void update(ObjectId id, String nombre, String divisa) {
        BasicDBObject newEvento = new BasicDBObject();
        BasicDBObject atributos = new BasicDBObject();
        atributos.append("nombre", nombre);
        atributos.append("divisa", divisa);
        newEvento.append("$set", atributos);
        BasicDBObject oldEvento = new BasicDBObject().append("_id", id);
        eventos.update(oldEvento, newEvento);
    }

    /**
     * Devuelve un listado con todos los eventos del usuario.
     *
     * @param idUsuario ObjectId del usuario.
     * @return List<Evento>
     */
    public static List<Evento> readAll(ObjectId idUsuario) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("idCreador", idUsuario);
        DBCursor cursor = eventos.find(whereQuery);
        List<Evento> eventos = new ArrayList<>();
        while (cursor.hasNext()) {
            BasicDBObject e = (BasicDBObject) cursor.next();
            eventos.add(new Evento(e.getObjectId("_id"), e.getString("nombre"), e.getString("divisa"), e.getObjectId("idCreador"), ParticipanteDAO.readAllFromEvento(e.getObjectId("_id"))));
        }
        return eventos;
    }

    /**
     * Elimina de la BD un evento.
     *
     * @param id ObjectId del evento a eliminar.
     */
    public static void delete(ObjectId id) {
        eventos.remove(new BasicDBObject().append("_id", id));
    }

    /**
     * Devuelve un BasicDBObject de la BD.
     *
     * @param id ObjectId del evento a devolver.
     * @return BasicDBObject
     */
    public static BasicDBObject readDBObject(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        return (BasicDBObject) eventos.findOne(whereQuery);
    }

    /**
     * Devuelve un BasicDBObject de la BD.
     *
     * @param nombre String nombre del evento.
     * @param idCreador ObjectId del creador del evento.
     * @return BasicDBObject
     */
    public static BasicDBObject readDBObject(String nombre, ObjectId idCreador) {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("nombre", nombre));
        obj.add(new BasicDBObject("idCreador", idCreador));
        andQuery.put("$and", obj);
        return (BasicDBObject) eventos.findOne(andQuery);
    }

    private static Boolean esDeudor(String nombre, List<Participante> deudores) {
        for (Participante d : deudores) {
            if (nombre.equals(d.getNombre())) {
                return true;
            }
        }
        return false;
    }

    public static List<ResumenPlusvalia> getResumenPlusvalia(Evento e) {
        Double plusvalia = 0.0;
        int hePagado = 0, heParticipado = 0;
        List<ResumenPlusvalia> resumen = new ArrayList<>();
        List<Gasto> gastos = GastoDAO.readAll(e.getId());
        List<Participante> participantes = ParticipanteDAO.readAllFromEvento(e.getId());
        List<Tupla> positivo = new ArrayList<>();
        List<Tupla> negativo = new ArrayList<>();
        for (Participante p : participantes) {
            for (Gasto g : gastos) {
                if (g.getIdPagador().equals(p.getId()) && esDeudor(p.getNombre(), g.getDeudores())) { //pertenezco a los dos
                    plusvalia += g.getPrecio() - (g.getPrecio() / g.getDeudores().size());
                    hePagado++;
                    heParticipado++;
                } else if ((g.getIdPagador().equals(p.getId()) && !esDeudor(p.getNombre(), g.getDeudores()))) { //he pagado pero no participo
                    plusvalia += g.getPrecio();
                    hePagado++;
                } else if ((!g.getIdPagador().equals(p.getId()) && esDeudor(p.getNombre(), g.getDeudores()))) { //no he pagado y participo
                    plusvalia -= (g.getPrecio() / g.getDeudores().size());
                    heParticipado++;
                }
            }
            if (plusvalia > 0) {
                positivo.add(new Tupla(plusvalia, p.getNombre()));
            } else if (plusvalia < 0) {
                negativo.add(new Tupla(plusvalia, p.getNombre()));
            }

            resumen.add(new ResumenPlusvalia(p.getNombre(), "", "", hePagado, heParticipado));
            plusvalia = 0.0;
            hePagado = 0;
            heParticipado = 0;
        }
        Iterator<Tupla> itPos = positivo.iterator();
        Iterator<Tupla> itNeg = negativo.iterator();

        Tupla pos = null;
        Tupla neg = null;
        int flag = 0; //0: aumentar los dos, 1: aumentar Pos, 2: aumentar Neg
        int posicion;
        ResumenPlusvalia persona;
        while (itPos.hasNext() || itNeg.hasNext()) {
            switch (flag) {
                case 0:
                    pos = itPos.next();
                    neg = itNeg.next();
                    break;
                case 1:
                    pos = itPos.next();
                    break;
                case 2:
                    neg = itNeg.next();
                    break;
            }
            if (pos.getX() > Math.abs(neg.getX())) {
                posicion = estaIncluida(pos.getY(), resumen);

                persona = resumen.get(posicion);
                persona.setDebeRecibir(persona.getDebeRecibir() + " " + neg.getY() + " " + Math.abs(neg.getX()) + " " + e.getDivisa());
                resumen.set(posicion, persona);

                posicion = estaIncluida(neg.getY(), resumen);

                persona = resumen.get(posicion);
                persona.setDebePoner(persona.getDebePoner() + " " + pos.getY() + " " + Math.abs(neg.getX()) + " " + e.getDivisa());
                resumen.set(posicion, persona);

                flag = 2;
                pos.setX(pos.getX() + neg.getX());
            } else if (pos.getX() == Math.abs(neg.getX())) {
                posicion = estaIncluida(pos.getY(), resumen);

                persona = resumen.get(posicion);
                persona.setDebeRecibir(persona.getDebeRecibir() + " " + neg.getY() + " " + pos.getX() + " " + e.getDivisa());
                resumen.set(posicion, persona);

                posicion = estaIncluida(neg.getY(), resumen);

                persona = resumen.get(posicion);
                persona.setDebePoner(persona.getDebePoner() + " " + pos.getY() + " " + pos.getX() + " " + e.getDivisa());
                resumen.set(posicion, persona);

                flag = 0;
                pos.setX(pos.getX() + neg.getX());
                neg.setX(pos.getX() + neg.getX());
            } else {
                posicion = estaIncluida(pos.getY(), resumen);

                persona = resumen.get(posicion);
                persona.setDebeRecibir(persona.getDebeRecibir() + " " + neg.getY() + " " + pos.getX() + " " + e.getDivisa());
                resumen.set(posicion, persona);

                posicion = estaIncluida(neg.getY(), resumen);
                persona = resumen.get(posicion);
                persona.setDebePoner(persona.getDebePoner() + " " + pos.getY() + " " + pos.getX() + " " + e.getDivisa());
                resumen.set(posicion, persona);

                flag = 1;
                neg.setX(pos.getX() + neg.getX());
            }
        }

        return resumen;
    }

    private static int estaIncluida(String nombre, List<ResumenPlusvalia> resumen) {
        Iterator<ResumenPlusvalia> it = resumen.iterator();
        ResumenPlusvalia resu;
        int i = 0;
        while (it.hasNext()) {
            resu = it.next();
            if (resu.getNombreParticipante().equals(nombre)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static List<ResumenPagoPorPersona> getResumenGastosPorPersona(Evento e, Participante p) {
        List<ResumenPagoPorPersona> resumen = new ArrayList<>();
        //TODO
        return resumen;
    }

    public static boolean esParticipante(Evento evento, Participante participante) {
        for(Participante p : evento.getParticipantes()){
            if(p.getNombre().equals(participante.getNombre())){
                return true;
            }
        }
        return false;
    }

}
