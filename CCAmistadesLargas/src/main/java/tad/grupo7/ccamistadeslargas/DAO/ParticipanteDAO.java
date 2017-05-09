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
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author Naiara
 */
public class ParticipanteDAO {

    private static DB dataBase = new MongoClient("localhost", 27017).getDB("CC");
    private static DBCollection participantes = dataBase.getCollection("Participante");

    public static void create(String nombre, ObjectId idAmigoDe) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("idAmigoDe", idAmigoDe);
        participantes.insert(document);
    }

    public static void update(ObjectId id, String nombre) {
        BasicDBObject newParticipante = new BasicDBObject();
        newParticipante.append("$set", new BasicDBObject().append("nombre", nombre));
        BasicDBObject oldParticipante = new BasicDBObject().append("_id", id);
        participantes.update(oldParticipante, newParticipante);
    }

    public static void delete(ObjectId id) {
        participantes.remove(new BasicDBObject().append("_id", id));
    }

    public static Participante read(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) participantes.findOne(whereQuery);
        String nombre = document.getString("nombre");
        ObjectId idAmigoDe = document.getObjectId("idAmigoDe");
        return new Participante(id, nombre, idAmigoDe);
    }

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

    public static List<Participante> readAllDeudoresFromPago(ObjectId idPago) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", idPago);
        BasicDBObject document = (BasicDBObject) dataBase.getCollection("Pago").findOne(whereQuery);
        BasicDBList participantesDB = (BasicDBList) document.get("deudores");
        Iterator it = participantesDB.iterator();
        List<Participante> participantes = new ArrayList<>();
        while (it.hasNext()) {
            BasicDBObject p = (BasicDBObject) it.next();
            participantes.add(new Participante(p.getObjectId("_id"), p.getString("nombre"), p.getObjectId("idAmigoDe")));
        }
        return participantes;
    }
    
    public static BasicDBObject readDBObject(String nombre, ObjectId idAmigoDe) {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("nombre", nombre));
        obj.add(new BasicDBObject("idAmigoDe", idAmigoDe));
        andQuery.put("$and", obj);
        return (BasicDBObject) participantes.findOne(andQuery);
    }
    public static BasicDBObject readDBObject(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        return (BasicDBObject) participantes.findOne(whereQuery);
    }
}
