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

    public static void addAmigo(String nombre, ObjectId idAmigoDe) {
        BasicDBList amigos = null;
        BasicDBObject amigo = new BasicDBObject();
        amigo.append("nombre", nombre);
        amigo.append("idAmigoDe", idAmigoDe);
        amigos = (BasicDBList) readDBObject(idAmigoDe).get("amigos");
        if(amigos==null){ //Si es la primera vez
            amigos = new BasicDBList();
            ParticipanteDAO.create(nombre, idAmigoDe);
        }
        amigos.add(ParticipanteDAO.readDBObject(nombre,idAmigoDe));
        BasicDBObject newUsuario = new BasicDBObject();
        newUsuario.append("$set", new BasicDBObject().append("amigos", amigos));
        BasicDBObject oldUsuario = new BasicDBObject().append("_id", idAmigoDe);
        usuarios.update(oldUsuario, newUsuario);
    }

    public static void update(ObjectId id, String nombre, String password, String email) {
        BasicDBObject newUsuario = new BasicDBObject();
        newUsuario.append("$set", new BasicDBObject().append("nombre", nombre));
        newUsuario.append("$set", new BasicDBObject().append("password", password));
        newUsuario.append("$set", new BasicDBObject().append("email", email));
        BasicDBObject oldUsuario = new BasicDBObject().append("_id", id);
        usuarios.update(oldUsuario, newUsuario);
    }

    public static void delete(ObjectId id) {
        usuarios.remove(new BasicDBObject().append("_id", id));
    }

    public static Usuario read(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) usuarios.findOne(whereQuery);
        String nombre = document.getString("nombre");
        String password = document.getString("password");
        String email = document.getString("email");
        return new Usuario(id, nombre, password, email, ParticipanteDAO.readAllFromUsuario(id));
    }

    public static Usuario read(String email, String password) {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("email", email));
        obj.add(new BasicDBObject("password", password));
        andQuery.put("$and", obj);
        BasicDBObject document = (BasicDBObject) usuarios.findOne(andQuery);
        Usuario u = null;
        if (document != null) {
            u = new Usuario(document.getObjectId("_id"), document.getString("nombre"), password, email, ParticipanteDAO.readAllFromUsuario(document.getObjectId("_id")));
        }
        return u;
    }

    public static BasicDBObject readDBObject(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) usuarios.findOne(whereQuery);
        return document;
    }
}
