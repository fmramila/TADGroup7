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
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bson.types.ObjectId;
import tad.grupo7.ccamistadeslargas.modelo.Participante;

/**
 *
 * @author Naiara
 */
public class ParticipanteDAO {

    private static DB dataBase = new MongoClient("localhost", 27017).getDB("CC");
    private static DBCollection participantes = dataBase.getCollection("Participante");

    /**
     * Crear en la BD un participante.
     * @param nombre String nombre del participante.
     * @param idAmigoDe ObjectId del usuario creador del participante.
     */
    public static void create(String nombre, ObjectId idAmigoDe) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("idAmigoDe", idAmigoDe);
        participantes.insert(document);
    }

    /**
     * Actualizar el participante en la BD.
     * @param id ObjectId del participante a actualizar.
     * @param nombre String nombre nuevo del participante.
     */
    public static void update(ObjectId id, String nombre) {
        BasicDBObject newParticipante = new BasicDBObject();
        newParticipante.append("$set", new BasicDBObject().append("nombre", nombre));
        BasicDBObject oldParticipante = new BasicDBObject().append("_id", id);
        participantes.update(oldParticipante, newParticipante);
    }

    /**
     * Elimina un participante de la BD.
     * @param id ObjectId del participante a eliminar.
     */
    public static void delete(ObjectId id) {
        participantes.remove(new BasicDBObject().append("_id", id));
    }

    /**
     * Obtiene un Participante de la BD.
     * @param id ObjectId del participante a obtener.
     * @return Participante
     */
    public static Participante read(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) participantes.findOne(whereQuery);
        String nombre = document.getString("nombre");
        ObjectId idAmigoDe = document.getObjectId("idAmigoDe");
        return new Participante(id, nombre, idAmigoDe);
    }
    
    /**
     * Devuelve un participante de la BD.
     * @param nombre String nombre del participante.
     * @return Participante.
     */
    public static Participante read(String nombre, ObjectId idAmigoDe){
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<>();
        obj.add(new BasicDBObject("nombre", nombre));
        obj.add(new BasicDBObject("idAmigoDe", idAmigoDe));
        andQuery.put("$and", obj);
        BasicDBObject document = (BasicDBObject) participantes.findOne(andQuery);
        Participante p = null;
        if(document!=null){
            p = new Participante(document.getObjectId("_id"), nombre, idAmigoDe);
        }
        return p;
    }

    /**
     * Obtiene una lista de todos los participantes de un evento.
     * @param idEvento ObjectId del evento.
     * @return List
     */
    public static List<Participante> readAllFromEvento(ObjectId idEvento) {
        List<Participante> participantes = new ArrayList<>();
        try {
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("_id", idEvento);
            BasicDBObject document = (BasicDBObject) dataBase.getCollection("Evento").findOne(whereQuery);
            BasicDBList participantesDB = (BasicDBList) document.get("participantes");
            Iterator it = participantesDB.iterator();

            while (it.hasNext()) {
                BasicDBObject p = (BasicDBObject) it.next();
                participantes.add(new Participante(p.getObjectId("_id"), p.getString("nombre"), p.getObjectId("idAmigoDe")));
            }
        } catch (NullPointerException ex) {
        }
        return participantes;
    }

    /**
     * Obtiene todos los participantes(amigos) de un Usuario.
     * @param idUsuario ObjectId del usuario.
     * @return List
     */
    public static List<Participante> readAllFromUsuario(ObjectId idUsuario) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", idUsuario);
        BasicDBObject document = (BasicDBObject) dataBase.getCollection("Usuario").findOne(whereQuery);
        List<Participante> participantes = new ArrayList<>();
        try {
            BasicDBList participantesDB = (BasicDBList) document.get("amigos");
            Iterator it = participantesDB.iterator();

            while (it.hasNext()) {
                BasicDBObject p = (BasicDBObject) it.next();
                participantes.add(new Participante(p.getObjectId("_id"), p.getString("nombre"), p.getObjectId("idAmigoDe")));
            }
        } catch (NullPointerException ex) {
        }

        return participantes;
    }

    /**
     * Obtiene un listado de todos los deudores de un pago de un evento.
     * @param idGasto ObjectId del gasto.
     * @return List
     */
    public static List<Participante> readAllDeudoresFromPago(ObjectId idGasto) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", idGasto);
        BasicDBObject document = (BasicDBObject) dataBase.getCollection("Gasto").findOne(whereQuery);
        BasicDBList participantesDB = (BasicDBList) document.get("deudores");
        Iterator it = participantesDB.iterator();
        List<Participante> participantes = new ArrayList<>();
        while (it.hasNext()) {
            BasicDBObject p = (BasicDBObject) it.next();
            participantes.add(new Participante(p.getObjectId("_id"), p.getString("nombre"), p.getObjectId("idAmigoDe")));
        }
        return participantes;
    }
    
    /**
     * Obtiene un BasicDBObject de la BD.
     * @param nombre String nombre del participante.
     * @param idAmigoDe ObjectId del usuario creador del participante.
     * @return Participante
     */
    public static BasicDBObject readDBObject(String nombre, ObjectId idAmigoDe) {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("nombre", nombre));
        obj.add(new BasicDBObject("idAmigoDe", idAmigoDe));
        andQuery.put("$and", obj);
        return (BasicDBObject) participantes.findOne(andQuery);
    }
    
    /**
     * Obtener un BasicDBObject de la BD.
     * @param id ObjectId del participante.
     * @return BasicDBObject
     */
    public static BasicDBObject readDBObject(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        return (BasicDBObject) participantes.findOne(whereQuery);
    }
}
