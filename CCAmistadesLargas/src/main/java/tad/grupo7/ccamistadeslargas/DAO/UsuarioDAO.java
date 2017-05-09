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
    
    public static void updateAmigo(String nombre, ObjectId idAmigoDe,ObjectId idUsuario) {
        BasicDBList amigos;
        ObjectId idAmigoDe2;
        BasicDBList newAmigos = new BasicDBList();
        BasicDBObject amigo = new BasicDBObject();
        amigo.append("nombre", nombre);
        amigo.append("idAmigoDe",idUsuario );
        amigos = (BasicDBList) readDBObject(idUsuario).get("amigos");
        Iterator it= amigos.iterator();
       while(it.hasNext()){
            BasicDBObject amigoL=(BasicDBObject) it.next();
            idAmigoDe2=(ObjectId) amigoL.get("_id");
            if(idAmigoDe2.equals(idAmigoDe)){
                amigo.append("_id", idAmigoDe2);
                newAmigos.add(amigo);
            }else{
                newAmigos.add(amigoL);
            }
        }
        BasicDBObject newUsuario = new BasicDBObject();
        newUsuario.append("$set", new BasicDBObject().append("amigos", newAmigos));
        BasicDBObject oldUsuario = new BasicDBObject().append("_id", idUsuario);
        usuarios.update(oldUsuario, newUsuario);
    }
    
    public static void removeAmigo(String nombre, ObjectId idAmigoDe) {
        BasicDBList amigos;
        String nombre2;
        ObjectId idAmigoDe2;
        BasicDBList newAmigos = new BasicDBList();
        BasicDBObject amigo = new BasicDBObject();
        amigo.append("nombre", nombre);
        amigo.append("idAmigoDe", idAmigoDe);
        amigos = (BasicDBList) readDBObject(idAmigoDe).get("amigos");
        Iterator it= amigos.iterator();
       while(it.hasNext()){
            BasicDBObject amigoL=(BasicDBObject) it.next();
            nombre2=(String) amigoL.get("nombre");
            idAmigoDe2=(ObjectId) amigoL.get("idAmigoDe");
            if(nombre2.equals(nombre) && idAmigoDe2.equals(idAmigoDe)){
            }else{
                newAmigos.add(amigoL);
            }
        }
        //amigos.remove(ParticipanteDAO.readDBObject(nombre,idAmigoDe));
        BasicDBObject newUsuario = new BasicDBObject();
        newUsuario.append("$set", new BasicDBObject().append("amigos", newAmigos));
        BasicDBObject oldUsuario = new BasicDBObject().append("_id", idAmigoDe);
        usuarios.update(oldUsuario, newUsuario);
    }

    public static void update(ObjectId id, String nombre, String password, String email) {
        BasicDBObject newUsuario = new BasicDBObject();
        BasicDBObject atributos = new BasicDBObject();
        atributos.append("nombre", nombre);
        atributos.append("password", password);
        atributos.append("email", email);
        newUsuario.append("$set", atributos);
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
        List<BasicDBObject> obj = new ArrayList<>();
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
