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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import tad.grupo7.ccamistadeslargas.modelo.Evento;

/**
 *
 * @author cayetano
 */
public class EventoDAO {

    private static DB dataBase = null;
    private static DBCollection eventos = null;

    public EventoDAO() throws UnknownHostException {
        dataBase = new MongoClient("localhost", 27017).getDB("CC");
        eventos = dataBase.getCollection("Evento");
    }

    public static void create(String nombre, String divisa, String idCreador) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("divisa", divisa);
        document.append("idCreador", idCreador);
        eventos.insert(document);
    }
    
    public static Evento read(String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) eventos.findOne(whereQuery);
        String nombre = document.getString("nombre");
        String divisa = document.getString("divisa");
        String idCreador = document.getString("idCreador");
        Evento e = new Evento(id, nombre, divisa,idCreador,ParticipanteDAO.readAllFromEvento(id));
        return e;
    }

    public static void update(String id, String nombre, String divisa) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) eventos.findOne(whereQuery);
        document.append("$set", new BasicDBObject().append("nombre", nombre));
        document.append("$set", new BasicDBObject().append("divisa", divisa));
    }
    
    public static List<Evento> readAll(String idUsuario){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("creador", idUsuario);
        DBCursor cursor = eventos.find(whereQuery);
        List<Evento> eventos = new ArrayList<>();
        while(cursor.hasNext()){
            BasicDBObject e = (BasicDBObject) cursor.next();
            eventos.add(new Evento(e.getString("_id"),e.getString("nombre"),e.getString("divisa"),e.getString("idCreador"),ParticipanteDAO.readAllFromEvento(e.getString("_id"))));
        }
        return eventos;
    }

    public static void delete(String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("i_d", id);
        BasicDBObject document = (BasicDBObject) eventos.findOne(whereQuery);
        eventos.remove(document);
    }
}
