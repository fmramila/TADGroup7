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
import java.util.List;
import org.bson.types.ObjectId;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author Naiara
 */
public class UsuarioDAO {

    private static DB dataBase = new MongoClient("localhost", 27017).getDB("CC");
    private static DBCollection usuarios = dataBase.getCollection("Usuario");

    public static void create(String nombre, String password, String email) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("password", password);
        document.append("email", email);
        usuarios.insert(document);
        Usuario u = read(nombre, password);
        addAmigo(nombre, u.getId()); //se añade a él mismo como amigo
    }

    public static void addAmigo(String nombre, String idAmigoDe) {
        BasicDBList amigos = new BasicDBList();
        try { //Si ya tiene amigos
            amigos = (BasicDBList) readDBObject(idAmigoDe).get("amigos");
            BasicDBObject amigo = new BasicDBObject();
            amigo.append("nombre", nombre);
            amigo.append("idAmigoDe", idAmigoDe);
            amigos.add(amigo);
        } catch (NullPointerException ex) { //Si es el primero
            amigos = new BasicDBList();
            BasicDBObject amigo = new BasicDBObject();
            amigo.append("nombre", nombre);
            amigo.append("idAmigoDe", idAmigoDe);
            amigos.add(amigo);
        }
        BasicDBObject newUsuario = new BasicDBObject();
        newUsuario.append("$set", new BasicDBObject().append("amigos", amigos));
        BasicDBObject oldUsuario = new BasicDBObject().append("_id", new ObjectId(idAmigoDe));
        usuarios.update(oldUsuario, newUsuario);
    }

    public static void update(String id, String nombre, String password, String email) {
        BasicDBObject newUsuario = new BasicDBObject();
        newUsuario.append("$set", new BasicDBObject().append("nombre", nombre));
        newUsuario.append("$set", new BasicDBObject().append("password", password));
        newUsuario.append("$set", new BasicDBObject().append("email", email));
        BasicDBObject oldUsuario = new BasicDBObject().append("_id", new ObjectId(id));
        usuarios.update(oldUsuario, newUsuario);
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
        obj.add(new BasicDBObject("password", password));
        andQuery.put("$and", obj);
        BasicDBObject document = (BasicDBObject) usuarios.findOne(andQuery);
        Usuario u = null;
        if (document != null) {
            String email = document.getString("email");
            u = new Usuario(document.getString("_id"), nombre, password, email, ParticipanteDAO.readAllFromUsuario(document.getString("_id")));
        }
        return u;
    }

    public static BasicDBObject readDBObject(String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) usuarios.findOne(whereQuery);
        return document;
    }
}
