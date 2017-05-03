/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
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
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import com.vaadin.ui.UI;
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

    /**
     * Muestra una tabla con todos los eventos y un botón para añadir más.
     */
    public void mostrarEventos() {
        removeAllComponents();
        //BOTÓN PARA AÑADIR OTRO EVENTO
        Button addEventoBoton = new Button("Añadir Evento");
        addEventoBoton.addClickListener(clickEvent -> {
            mostrarFormularioAddEvento();
        });
        //TABLA DE LOS EVENTOS
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
        //SI CLICAMOS EN UN EVENTO DE LA TABLA SE MUESTRA
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                try {
                    Evento e = eventos.get(((int) table.getValue()) - 1);
                    mostrarEvento(e);
                } catch (Exception e) {

                }
            }
        });
        VerticalLayout col1 = new VerticalLayout(addEventoBoton, table);
        col1.setMargin(true);
        setFirstComponent(col1);
    }

    /**
     * Se muestra el evento en el vertical layout derecho del splitpanel.
     *
     * @param e Recoge el evento que se quiere mostrar.
     */
    public void mostrarEvento(Evento e) {
        //FORMULARIO POR SI SE QUIERE EDITAR EL EVENTO
        TextField nombre = new TextField("Nombre");
        nombre.setValue(e.getNombre());
        TextField divisa = new TextField("Divisa");
        divisa.setValue(e.getDivisa());
        final Button actualizar = new Button("Actualizar");
        final Button addPago = new Button("Añadir Pago");
        actualizar.addClickListener(clickEvent -> {
            EventoDAO.update(e.getIdEvento(), nombre.getValue(), divisa.getValue());
            Notification n = new Notification("Evento actualizado", Notification.Type.ASSISTIVE_NOTIFICATION);
            n.setPosition(Position.TOP_CENTER);
            n.show(Page.getCurrent());
        });
        //BOTÓN PARA QUE SALGA UNA VENTANA EMERGENTE PARA AÑADIR UN GASTO AL EVENTO
        addPago.addClickListener(clickEvent -> {
            mostrarFormularioAddGasto(e.getIdEvento());
        });
        FormLayout form = new FormLayout(nombre, divisa, actualizar, addPago);
        //TABLA CON TODOS LOS GASTOS DEL EVENTO
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
        VerticalLayout l = new VerticalLayout(form, table);
        l.setMargin(true);
        setSecondComponent(new VerticalLayout(form, table));
    }

    /**
     * Muestra el formulario para añadir un gasto al evento en una ventana
     * emergente.
     *
     * @param idEvento ID del Evento al que se quiere añadir un gasto.
     */
    public void mostrarFormularioAddGasto(int idEvento) {
        //SE CREA LA VENTANA EMERGENTE
        final Window subWindow = new Window("Añadir Pago");
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);
        TextField titulo = new TextField("Título");
        titulo.setRequired(true);
        TextField precio = new TextField("Precio");
        precio.setRequired(true);
        TextField usuario = new TextField("Usuario");
        usuario.setRequired(true);
        final Button add = new Button("Añadir Gasto");
        add.addStyleName(ValoTheme.BUTTON_PRIMARY);
        FormLayout form = new FormLayout(titulo, precio, usuario, add);
        subContent.addComponent(form);
        subWindow.center();
        UI.getCurrent().addWindow(subWindow);
        //SI SE CLICA EN AÑADIR PAGO SE CREA EL PAGO A LA VEZ QUE SE CIERRA LA VENTANA
        add.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    titulo.validate();
                    precio.validate();
                    usuario.validate();
                    GastoDAO.create(new Gasto(titulo.getValue(), Integer.valueOf(precio.getValue()), Integer.valueOf(usuario.getValue()), idEvento));
                    titulo.setValue("");
                    precio.setValue("");
                    usuario.setValue("");
                    subWindow.close(); // Close the sub-window
                } catch (Validator.InvalidValueException ex) {
                    Notification n = new Notification("Rellena todos los campos", Notification.Type.WARNING_MESSAGE);
                    n.setPosition(Position.TOP_CENTER);
                    n.show(Page.getCurrent());
                }
            }
        });
    }

    /**
     * Se muestra el formulario de añadir un nuevo evento.
     */
    public void mostrarFormularioAddEvento() {
        TextField nombre = new TextField("Nombre");
        nombre.setRequired(true);
        TextField divisa = new TextField("Divisa");
        divisa.setRequired(true);
        final Button add = new Button("Crear evento");
        add.addStyleName(ValoTheme.BUTTON_PRIMARY);
        FormLayout form = new FormLayout(nombre, divisa, add);
        add.addClickListener(clickEvent -> {
            try {
                nombre.validate();
                divisa.validate();
                EventoDAO.create(new Evento(nombre.getValue(), divisa.getValue()));
                mostrarEventos();
            } catch (Validator.InvalidValueException ex) {
                Notification n = new Notification("Rellena todos los campos", Notification.Type.WARNING_MESSAGE);
                n.setPosition(Position.TOP_CENTER);
                n.show(Page.getCurrent());
            }
        });
        form.setMargin(true);
        setSecondComponent(form);
    }

}
