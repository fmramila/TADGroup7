/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.data.Property;
import com.vaadin.server.Page;
import static com.vaadin.server.Sizeable.UNITS_PERCENTAGE;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import tad.grupo7.ccamistadeslargas.DAO.EventoDAO;
import tad.grupo7.ccamistadeslargas.DAO.GastoDAO;
import tad.grupo7.ccamistadeslargas.modelo.Evento;
import tad.grupo7.ccamistadeslargas.modelo.Gasto;

/**
 *
 * @author cayetano
 */
class EventosLayout extends HorizontalSplitPanel {

    public EventosLayout() {
        mostrarEventos();
    }

    public void mostrarEventos() {
        removeAllComponents();
        Button addEventoBoton = new Button("Añadir Evento");
        addEventoBoton.addClickListener(clickEvent -> {
            mostrarFormulario();
        });
        List<Evento> eventos = EventoDAO.readAll();
        Table table = new Table("");
        table.addContainerProperty("Nombre", String.class, null);
        table.addContainerProperty("Divisa", String.class, null);
        for (Evento e : eventos) {
            table.addItem(e.getArray(), null);
        }
        table.setPageLength(table.size());
        table.setWidth(100, UNITS_PERCENTAGE);
        table.setSelectable(true);
        table.setImmediate(true);

        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Evento e = eventos.get(((int) table.getValue()) - 1);
                mostrarEvento(e);
            }
        });

        VerticalLayout col1 = new VerticalLayout(addEventoBoton, table);
        col1.setMargin(true);
        setFirstComponent(col1);
    }

    public void mostrarEvento(Evento e) {
        TextField nombre = new TextField("Nombre");
        nombre.setValue(e.getNombre());
        TextField divisa = new TextField("Divisa");
        divisa.setValue(e.getDivisa());
        final Button actualizar = new Button("Actualizar");
        actualizar.addClickListener(clickEvent -> {
            EventoDAO.update(e.getIdEvento(), nombre.getValue(), divisa.getValue());
            Notification n = new Notification("Evento actualizado", Notification.Type.ASSISTIVE_NOTIFICATION);
            n.setPosition(Position.TOP_CENTER);
            n.show(Page.getCurrent());
        });
        FormLayout form = new FormLayout(nombre, divisa, actualizar);
        List<Gasto> gastos = GastoDAO.readAll(e.getIdEvento());
        Table table = new Table("");
        table.addContainerProperty("Nombre", String.class, null);
        table.addContainerProperty("Precio", Integer.class, null);
        table.addContainerProperty("Usuario", String.class, null);
        for (Gasto g : gastos) {
            table.addItem(g.getArray(), null);
        }
        table.setPageLength(table.size());
        /*
        table.setWidth(100, UNITS_PERCENTAGE);
        table.setSelectable(true);
        table.setImmediate(true);

        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Gasto g = gastos.get(((int) table.getValue()) - 1);
                mostrarEvento(e);
            }
        });
        */
        VerticalLayout l = new VerticalLayout(form,table);
        l.setMargin(true);
        setSecondComponent(new VerticalLayout(form,table));
    }

    public void mostrarFormulario() {
        TextField nombre = new TextField("Nombre");
        nombre.setRequired(true);
        TextField divisa = new TextField("Divisa");
        divisa.setRequired(true);
        final Button add = new Button("Crear evento");
        add.addStyleName(ValoTheme.BUTTON_PRIMARY);
        FormLayout form = new FormLayout(nombre, divisa, add);
        add.addClickListener(clickEvent -> {
            EventoDAO.create(new Evento(nombre.getValue(), divisa.getValue()));
            mostrarEventos();
        });
        form.setMargin(true);
        setSecondComponent(form);
    }

}
