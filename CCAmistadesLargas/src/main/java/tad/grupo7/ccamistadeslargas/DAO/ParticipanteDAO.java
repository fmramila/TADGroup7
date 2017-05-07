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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tad.grupo7.ccamistadeslargas.modelo.Participante;

/**
 *
 * @author Naiara
 */
public class ParticipanteDAO {

    private static DB dataBase = null;
    private static DBCollection participantes = dataBase.getCollection("Participante");

    public ParticipanteDAO() throws UnknownHostException {
        dataBase = new MongoClient("localhost", 27017).getDB("CC");
    }

    public static void create(String nombre, String idAmigoDe) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("idAmigoDe", idAmigoDe);
        participantes.insert(document);
    }

    public static void update(String id, String nombre) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) participantes.findOne(whereQuery);
        document.append("$set", new BasicDBObject().append("nombre", nombre));
    }

    public static void delete(String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) participantes.findOne(whereQuery);
        participantes.remove(document);
    }

    public static Participante read(String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) participantes.findOne(whereQuery);
        String nombre = document.getString("nombre");
        String idAmigoDe = document.getString("idAmigoDe");
        return new Participante(id, nombre, idAmigoDe);
    }
    
    public static List<Participante> readAllFromEvento(String idEvento) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", idEvento);
        BasicDBObject document = (BasicDBObject) dataBase.getCollection("Evento").findOne(whereQuery);
        BasicDBList participantesDB = (BasicDBList)document.get("participantes");
        Iterator it = participantesDB.iterator();
        List<Participante> participantes = new ArrayList<>();
        while(it.hasNext()){
            BasicDBObject p = (BasicDBObject) it.next();
            participantes.add(new Participante(p.getString("_id"), p.getString("nombre"), p.getString("idAmigoDe")));
        }
        return participantes;
    }
    
    public static List<Participante> readAllFromUsuario(String idUsuario) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", idUsuario);
        BasicDBObject document = (BasicDBObject) dataBase.getCollection("Usuario").findOne(whereQuery);
        BasicDBList participantesDB = (BasicDBList)document.get("amigos");
        Iterator it = participantesDB.iterator();
        List<Participante> participantes = new ArrayList<>();
        while(it.hasNext()){
            BasicDBObject p = (BasicDBObject) it.next();
            participantes.add(new Participante(p.getString("_id"), p.getString("nombre"), p.getString("idAmigoDe")));
        }
        return participantes;
    }
    
    public static List<Participante> readAllDeudoresFromPago(String idPago) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", idPago);
        BasicDBObject document = (BasicDBObject) dataBase.getCollection("Pago").findOne(whereQuery);
        BasicDBList participantesDB = (BasicDBList)document.get("deudores");
        Iterator it = participantesDB.iterator();
        List<Participante> participantes = new ArrayList<>();
        while(it.hasNext()){
            BasicDBObject p = (BasicDBObject) it.next();
            participantes.add(new Participante(p.getString("_id"), p.getString("nombre"), p.getString("idAmigoDe")));
        }
        return participantes;
    }
    
    
//    public static BasicDBObject readDBObject(String id) {
//        BasicDBObject whereQuery = new BasicDBObject();
//       whereQuery.put("_id", id);
//        BasicDBObject document = (BasicDBObject) participantes.findOne(whereQuery);
//        return document;
//    }


}
