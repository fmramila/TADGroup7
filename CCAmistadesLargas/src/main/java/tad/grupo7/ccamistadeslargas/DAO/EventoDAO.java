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
import java.util.List;
import org.bson.types.ObjectId;
import tad.grupo7.ccamistadeslargas.modelo.Evento;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author cayetano
 */
public class EventoDAO {

    private static DB dataBase = new MongoClient("localhost", 27017).getDB("CC");
    private static DBCollection eventos = dataBase.getCollection("Evento");

    public static void create(String nombre, String divisa, Usuario creador) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("divisa", divisa);
        document.append("idCreador", creador.getId());
        eventos.insert(document);
        ObjectId o = ParticipanteDAO.readDBObject(creador.getAmigos().get(0).getId()).getObjectId("_id");
        addParticipante(readDBObject(nombre, creador.getId()).getObjectId("_id"), o);
    }

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

    public static Evento read(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) eventos.findOne(whereQuery);
        String nombre = document.getString("nombre");
        String divisa = document.getString("divisa");
        String idCreador = document.getString("idCreador");
        Evento e = new Evento(id, nombre, divisa, idCreador, ParticipanteDAO.readAllFromEvento(id));
        return e;
    }

    public static void update(ObjectId id, String nombre, String divisa) {
        BasicDBObject newEvento = new BasicDBObject();
        BasicDBObject atributos = new BasicDBObject();
        atributos.append("nombre", nombre);
        atributos.append("divisa", divisa);
        newEvento.append("$set", atributos);
        BasicDBObject oldEvento = new BasicDBObject().append("_id", id);
        eventos.update(oldEvento, newEvento);
    }

    public static List<Evento> readAll(ObjectId idUsuario) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("idCreador", idUsuario);
        DBCursor cursor = eventos.find(whereQuery);
        List<Evento> eventos = new ArrayList<>();
        while (cursor.hasNext()) {
            BasicDBObject e = (BasicDBObject) cursor.next();
            eventos.add(new Evento(e.getObjectId("_id"), e.getString("nombre"), e.getString("divisa"), e.getString("idCreador"), ParticipanteDAO.readAllFromEvento(e.getObjectId("_id"))));
        }
        return eventos;
    }

    public static void delete(ObjectId id) {
        eventos.remove(new BasicDBObject().append("_id", id));
    }

    public static BasicDBObject readDBObject(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        return (BasicDBObject) eventos.findOne(whereQuery);
    }

    public static BasicDBObject readDBObject(String nombre, ObjectId idCreador) {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("nombre", nombre));
        obj.add(new BasicDBObject("idCreador", idCreador));
        andQuery.put("$and", obj);
        return (BasicDBObject) eventos.findOne(andQuery);
    }
}
