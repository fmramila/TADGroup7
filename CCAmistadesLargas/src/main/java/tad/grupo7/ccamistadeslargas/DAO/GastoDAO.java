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
import tad.grupo7.ccamistadeslargas.modelo.Gasto;
import tad.grupo7.ccamistadeslargas.modelo.Participante;

/**
 *
 * @author cayetano
 */
public class GastoDAO {

    private static DB dataBase = null;
    private static DBCollection gastos = null;

    public GastoDAO() throws UnknownHostException {
        dataBase = new MongoClient("localhost", 27017).getDB("CC");
        gastos = dataBase.getCollection("Gasto");
    }

    public static void create(String nombre, Double precio, String idEvento, String idPagador, List<Participante> deudores) {
        BasicDBObject document = new BasicDBObject();
        document.append("nombre", nombre);
        document.append("precio", precio);
        document.append("idEvento", idEvento);
        document.append("idPagador", idPagador);
        BasicDBList deudoresDB = new BasicDBList();
        for (Participante p : deudores) {
            BasicDBObject d = new BasicDBObject();
            d.append("nombre", p.getNombre());
            d.append("idAmigoDe", p.getIdAmigoDe());
            deudoresDB.add(d);
        }
        document.append("deudores", deudoresDB);
        gastos.insert(document);
    }

    public static void update(String id, String nombre, Double precio, String idEvento, String idPagador, List<Participante> deudores) {
        delete(id);
        create(nombre, precio, idEvento, idPagador, deudores);
    }

    public static Gasto read(String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) gastos.findOne(whereQuery);
        String nombre = document.getString("nombre");
        Double precio = (Double) document.get("precio");
        String evento = document.getString("idEvento");
        String pagador = document.getString("idPagador");
        List<Participante> deudores = new ArrayList<>();
        BasicDBList deudoresDB = (BasicDBList) document.get("deudores");
        Iterator it = deudoresDB.iterator();
        while(it.hasNext()){
            BasicDBObject b = (BasicDBObject) it.next();
            Participante p = new Participante(b.getString("_id"), b.getString("nombre"), b.getString("idAmigoDe"));
            deudores.add(p);
        }
        return new Gasto(id, nombre, precio, evento, pagador, deudores);
    }
    
    public static List<Gasto> readAll(String idEvento){
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("idEvento", idEvento);
        DBCursor cursor = gastos.find(whereQuery);
        List<Gasto> gastos = new ArrayList<>();
        while(cursor.hasNext()){
            BasicDBObject g = (BasicDBObject) cursor.next();
            gastos.add(new Gasto(g.getString("_id"), g.getString("nombre"), g.getDouble("precio"), g.getString("idEvento"), g.getString("idPagador"), ParticipanteDAO.readAllDeudoresFromPago(g.getString("_id"))));
        }
        return gastos;
    }

    
    public static void delete(String id) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("_id", id);
        BasicDBObject document = (BasicDBObject) gastos.findOne(whereQuery);
        gastos.remove(document);
    }
    
   
}
