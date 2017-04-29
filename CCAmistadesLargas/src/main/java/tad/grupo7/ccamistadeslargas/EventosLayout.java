/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;


import static com.vaadin.server.Sizeable.UNITS_PERCENTAGE;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import tad.grupo7.ccamistadeslargas.DAO.EventoDAO;
import tad.grupo7.ccamistadeslargas.modelo.Evento;


/**
 *
 * @author cayetano
 */
class EventosLayout extends VerticalLayout {

    public EventosLayout() {
        setMargin(true);
        setSpacing(true);
        Table table = new Table("");
        table.addContainerProperty("Nombre", String.class, null);
        table.addContainerProperty("Divisa", String.class, null);
//        for(Evento e : eventos){
//            table.addItem(e.getArray(), null);
//        }
        table.setPageLength(table.size());
        table.setWidth(100, UNITS_PERCENTAGE);

        VerticalLayout col1 = new VerticalLayout(table);
        col1.setComponentAlignment(table, Alignment.TOP_CENTER);
        col1.setMargin(true);
        addComponent(col1);
    }
}
