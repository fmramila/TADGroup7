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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tad.grupo7.ccamistadeslargas.modelo.Participante;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author Naiara
 */
public class UsuarioDAO {

    private static DB dataBase = null;
    private static DBCollection usuarios = null;

    public UsuarioDAO() throws UnknownHostException {
        dataBase = new MongoClient("localhost", 27017).getDB("CC");
        usuarios = dataBase.getCollection("Usuario");
    }

    public static void create(String nombre, String password, String email) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("password", password);
        document.append("email", email);
        usuarios.insert(document);
    }

    public static void update(String id, String nombre, String password, String email) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) usuarios.findOne(whereQuery);
        document.append("$set", new BasicDBObject().append("nombre", nombre));
        document.append("$set", new BasicDBObject().append("password", password));
        document.append("$set", new BasicDBObject().append("email", email));
    }

    public static void delete(String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) usuarios.findOne(whereQuery);
        usuarios.remove(document);
    }

    public static Usuario read(String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) usuarios.findOne(whereQuery);
        String nombre = document.getString("nombre");
        String password = document.getString("password");
        String email = document.getString("email");
        return new Usuario(id, nombre, password, email, ParticipanteDAO.readAllFromUsuario(id));
    }

    public static Usuario read(String nombre, String password) {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("nombre", nombre));
        obj.add(new BasicDBObject("password", "password"));
        andQuery.put("$and", obj);
        BasicDBObject document = (BasicDBObject) usuarios.findOne(andQuery);
        String email = document.getString("email");
        return new Usuario(document.getString("_id"), nombre, password, email, ParticipanteDAO.readAllFromUsuario(document.getString("_id")));
    }

//    public static BasicDBObject readDBObject(String id) {
//        BasicDBObject whereQuery = new BasicDBObject();
//        whereQuery.put("_id", id);
//        BasicDBObject document = (BasicDBObject) usuarios.findOne(whereQuery);
//        return document;
//    }
}
