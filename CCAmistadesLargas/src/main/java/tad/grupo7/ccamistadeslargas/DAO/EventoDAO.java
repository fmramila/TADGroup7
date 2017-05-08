/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import tad.grupo7.ccamistadeslargas.modelo.Evento;

/**
 *
 * @author cayetano
 */
public class EventoDAO {

    private static DB dataBase =  new MongoClient("localhost", 27017).getDB("CC");;
    private static DBCollection eventos = dataBase.getCollection("Evento");


    public static void create(String nombre, String divisa, ObjectId idCreador) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("divisa", divisa);
        document.append("idCreador", idCreador);
        eventos.insert(document);
    }
    
    public static Evento read(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) eventos.findOne(whereQuery);
        String nombre = document.getString("nombre");
        String divisa = document.getString("divisa");
        String idCreador = document.getString("idCreador");
        Evento e = new Evento(id, nombre, divisa,idCreador,ParticipanteDAO.readAllFromEvento(id));
        return e;
    }

    public static void update(ObjectId id, String nombre, String divisa) {
        BasicDBObject newEvento = new BasicDBObject();
        newEvento.append("$set", new BasicDBObject().append("nombre", nombre));
        newEvento.append("$set", new BasicDBObject().append("divisa", divisa));
        BasicDBObject oldEvento = new BasicDBObject().append("_id", id);
        eventos.update(oldEvento, newEvento);
    }
    
    public static List<Evento> readAll(ObjectId idUsuario){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("idCreador", idUsuario);
        DBCursor cursor = eventos.find(whereQuery);
        List<Evento> eventos = new ArrayList<>();
        while(cursor.hasNext()){
            BasicDBObject e = (BasicDBObject) cursor.next();
            eventos.add(new Evento(e.getObjectId("_id"),e.getString("nombre"),e.getString("divisa"),e.getString("idCreador"),ParticipanteDAO.readAllFromEvento(e.getObjectId("_id"))));
        }
        return eventos;
    }

    public static void delete(ObjectId id) {
        eventos.remove(new BasicDBObject().append("_id", id));
    }
}
