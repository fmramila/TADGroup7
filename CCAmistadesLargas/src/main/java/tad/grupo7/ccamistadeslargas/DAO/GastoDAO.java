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
import java.util.Iterator;
import java.util.List;
import org.bson.types.ObjectId;
import tad.grupo7.ccamistadeslargas.modelo.Gasto;
import tad.grupo7.ccamistadeslargas.modelo.Participante;

/**
 *
 * @author cayetano
 */
public class GastoDAO {

    private static DB dataBase = new MongoClient("localhost", 27017).getDB("CC");
    private static DBCollection gastos = dataBase.getCollection("Gasto");

    /**
     * Crea un gasto en la BD.
     * @param nombre String nombre del gasto.
     * @param precio Double precio del gasto.
     * @param idEvento ObjectId del evento al que pertenece el gasto.
     * @param idPagador ObjectId del encargado de pagar el gasto.
     * @param deudores  List participantes del evento que se han beneficiado del pagador.
     */
    public static void create(String nombre, Double precio, ObjectId idEvento, ObjectId idPagador, List<Participante> deudores) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("precio", precio);
        document.append("idEvento", idEvento);
        document.append("idPagador", idPagador);
        BasicDBList deudoresDB = new BasicDBList();
        for (Participante p : deudores) {
            deudoresDB.add(ParticipanteDAO.readDBObject(p.getNombre(), p.getIdAmigoDe()));
        }
        document.append("deudores", deudoresDB);
        gastos.insert(document);
    }

    /**
     * Actualiza un gasto en la BD.
     * @param id ObjectId del gasto.
     * @param nombre String nombre del gasto.
     * @param precio Double precio del gasto.
     * @param idEvento ObjectId del evento.
     * @param idPagador ObjectId del pagador.
     * @param deudores List deudores del gasto.
     */
    public static void update(ObjectId id, String nombre, Double precio, ObjectId idEvento, ObjectId idPagador, List<Participante> deudores) {
        delete(id);
        create(nombre, precio, idEvento, idPagador, deudores);
    }

    /**
     * Obtiene un Gasto coincidente con un ID.
     * @param id ObjectId del gasto.
     * @return Gasto
     */
    public static Gasto read(ObjectId id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) gastos.findOne(whereQuery);
        String nombre = document.getString("nombre");
        Double precio = (Double) document.get("precio");
        ObjectId evento = document.getObjectId("idEvento");
        ObjectId pagador = document.getObjectId("idPagador");
        List<Participante> deudores = new ArrayList<>();
        BasicDBList deudoresDB = (BasicDBList) document.get("deudores");
        Iterator it = deudoresDB.iterator();
        while(it.hasNext()){
            BasicDBObject b = (BasicDBObject) it.next();
            Participante p = new Participante(b.getObjectId("_id"), b.getString("nombre"), b.getObjectId("idAmigoDe"));
            deudores.add(p);
        }
        return new Gasto(id, nombre, precio, evento, pagador, deudores);
    }
    
    /**
     * Obtiene todos los gastos pertenecientes a un evento.
     * @param idEvento ObjectId del evento.
     * @return List
     */
    public static List<Gasto> readAll(ObjectId idEvento){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("idEvento", idEvento);
        DBCursor cursor = gastos.find(whereQuery);
        List<Gasto> gastos = new ArrayList<>();
        while(cursor.hasNext()){
            BasicDBObject g = (BasicDBObject) cursor.next();
            ObjectId id = g.getObjectId("_id");
            String nombre = g.getString("nombre");
            Double precio = g.getDouble("precio");
            ObjectId idPagador = g.getObjectId("idPagador");
            List<Participante> deudores = ParticipanteDAO.readAllDeudoresFromPago(id);
            gastos.add(new Gasto(id,nombre,precio,idEvento,idPagador,deudores));
        }
        return gastos;
    }

    /**
     * Elimina un gasto de la BD.
     * @param id ObjectId del gasto a eliminar.
     */
    public static void delete(ObjectId id) {
        gastos.remove(new BasicDBObject().append("_id", id));
    }
    
   
}
