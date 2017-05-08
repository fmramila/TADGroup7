/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import static com.vaadin.server.Sizeable.UNITS_PERCENTAGE;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import java.util.List;
import com.vaadin.ui.UI;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import tad.grupo7.ccamistadeslargas.DAO.EventoDAO;
import tad.grupo7.ccamistadeslargas.DAO.GastoDAO;
import tad.grupo7.ccamistadeslargas.DAO.ParticipanteDAO;
import tad.grupo7.ccamistadeslargas.modelo.Evento;
import tad.grupo7.ccamistadeslargas.modelo.Gasto;
import tad.grupo7.ccamistadeslargas.modelo.Participante;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author cayetano
 */
class EventosLayout extends HorizontalSplitPanel {

    private final Usuario usuario;

    public EventosLayout() {
        usuario = (Usuario) Session.getAttribute("usuario");
        mostrarEventos();
    }

    /**
     * Muestra una tabla con todos los eventos y un botón para añadir más.
     */
    private void mostrarEventos() {
        removeAllComponents();
        //BOTÓN PARA AÑADIR OTRO EVENTO
        Button addEventoBoton = new Button("Añadir Evento");
        addEventoBoton.addClickListener(clickEvent -> {
            mostrarFormularioAddEvento();
        });
        //TABLA DE LOS EVENTOS
        Table table = getTablaEventos();
        //AÑADIMOS LOS COMPONENTES
        VerticalLayout col1 = new VerticalLayout(addEventoBoton, table);
        col1.setMargin(true);
        setFirstComponent(col1);
    }

    /**
     * Se muestra el evento en el vertical layout derecho del splitpanel.
     *
     * @param e Recoge el evento que se quiere mostrar.
     */
    private void mostrarEvento(Evento e) {
        removeAllComponents();
        //FORMULARIO POR SI SE QUIERE EDITAR EL EVENTO
        TextField nombre = new TextField("Nombre");
        nombre.setValue(e.getNombre());
        TextField divisa = new TextField("Divisa");
        divisa.setValue(e.getDivisa());
        final Button actualizar = new Button("Actualizar Evento");
        final Button eliminar = new Button("Eliminar Evento");
        final Button addPago = new Button("Añadir Pago");
        //BOTÓN PARA ACTUALIZAR EL EVENTO
        actualizar.addClickListener(clickEvent -> {
            EventoDAO.update(e.getId(), nombre.getValue(), divisa.getValue());
            Notification n = new Notification("Evento actualizado", Notification.Type.ASSISTIVE_NOTIFICATION);
            n.setPosition(Position.TOP_CENTER);
            n.show(Page.getCurrent());
        });
        //BOTÓN PARA QUE SALGA UNA VENTANA EMERGENTE PARA AÑADIR UN GASTO AL EVENTO
        addPago.addClickListener(clickEvent -> {
            mostrarFormularioAddGasto(e);
        });
        //BOTÓN PARA ELIMINAR EL EVENTO
        eliminar.addClickListener(clickEvent -> {
            EventoDAO.delete(e.getId());
            removeAllComponents();
            mostrarEventos();
        });
        //TABLA CON TODOS LOS GASTOS DEL EVENTO
        Table tablaGastos = getTablaGastos(e);
        //TABLA CON TODOS LOS PARTICIPANTES DEL EVENTO
        Table tablaParticipantes = getTablaParticipantes(e);
        //AÑADIMOS LOS COMPONENTES
        FormLayout form = new FormLayout(nombre, divisa, actualizar, eliminar, addPago);
        VerticalLayout l = new VerticalLayout(form, tablaGastos, tablaParticipantes);
        l.setMargin(true);
        setFirstComponent(l);
    }

     private void mostrarGasto(Gasto g, Evento e) {
         //FORMULARIO POR SI SE QUIERE EDITAR EL EVENTO
        TextField nombre = new TextField("Titulo");
        nombre.setValue(g.getNombre());
        TextField precio = new TextField("Precio");
        precio.setValue(g.getPrecio().toString());
        final Button actualizar = new Button("Actualizar Gasto");
        final Button eliminar = new Button("Eliminar Gasto");
        //BOTÓN PARA ACTUALIZAR EL GASTO
        actualizar.addClickListener(clickEvent -> {
            GastoDAO.update(g.getId(), nombre.getValue(), Double.valueOf(precio.getValue()), g.getIdEvento(), g.getIdPagador(), g.getDeudores());
            Notification n = new Notification("Gasto actualizado", Notification.Type.ASSISTIVE_NOTIFICATION);
            n.setPosition(Position.TOP_CENTER);
            n.show(Page.getCurrent());
        });
        //BOTÓN PARA ELIMINAR EL GASTO
        eliminar.addClickListener(clickEvent -> {
            GastoDAO.delete(g.getId());
            removeAllComponents();
            mostrarEvento(e);
        });
        //AÑADIMOS LOS COMPONENTES
        FormLayout form = new FormLayout(nombre, precio, actualizar, eliminar);
        VerticalLayout l = new VerticalLayout(form);
        l.setMargin(true);
        setSecondComponent(l);
     }
    
    /**
     * Muestra el formulario para añadir un gasto al evento en una ventana
     * emergente.
     *
     * @param idEvento ID del Evento al que se quiere añadir un gasto.
     */
    private void mostrarFormularioAddGasto(Evento e) {
        //SE CREA LA VENTANA EMERGENTE
        final Window subWindow = new Window("Añadir Pago");
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);
        TextField titulo = new TextField("Título");
        titulo.setRequired(true);
        TextField precio = new TextField("Precio");
        precio.setRequired(true);
        List<Participante> participantes = ParticipanteDAO.readAllFromEvento(e.getId());
        ComboBox pagador = new ComboBox("Pagador");
        FormLayout form = new FormLayout(titulo, precio, pagador);
        List<Participante> deudores = new ArrayList<>();
        for (Participante p : participantes) {
            pagador.addItem(p.getNombre());
            CheckBox c = new CheckBox(p.getNombre());
            c.addValueChangeListener(evento ->{
                deudores.add(p);
            });
            form.addComponent(c);
        }
        final Button add = new Button("Añadir Gasto");
        add.addStyleName(ValoTheme.BUTTON_PRIMARY);
        //SI SE CLICA EN AÑADIR PAGO SE CREA EL PAGO A LA VEZ QUE SE CIERRA LA VENTANA
        add.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    titulo.validate();
                    precio.validate();
                    pagador.validate();
                    GastoDAO.create(titulo.getValue(), Double.valueOf(precio.getValue()), e.getId(), ParticipanteDAO.read(pagador.getValue().toString()).getId(), deudores);
                    titulo.setValue("");
                    precio.setValue("");
                    pagador.setValue("");
                    subWindow.close(); // Close the sub-window
                    mostrarEvento(e);
                } catch (Validator.InvalidValueException ex) {
                    Notification n = new Notification("Rellena todos los campos", Notification.Type.WARNING_MESSAGE);
                    n.setPosition(Position.TOP_CENTER);
                    n.show(Page.getCurrent());
                }
            }
        });
        //AÑADIMOS LOS COMPONENTES
        form.addComponent(add);
        subContent.addComponent(form);
        subWindow.center();
        UI.getCurrent().addWindow(subWindow);
    }

    /**
     * Se muestra el formulario de añadir un nuevo evento.
     */
    private void mostrarFormularioAddEvento() {
        TextField nombre = new TextField("Nombre");
        nombre.setRequired(true);
        TextField divisa = new TextField("Divisa");
        divisa.setRequired(true);
        divisa.addValidator(new StringLengthValidator("Máximo 3 caracteres", 1, 3, false));
        final Button add = new Button("Crear evento");
        add.addStyleName(ValoTheme.BUTTON_PRIMARY);
        add.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        FormLayout form = new FormLayout(nombre, divisa, add);
        add.addClickListener(clickEvent -> {
            try {
                nombre.validate();
                divisa.validate();
                EventoDAO.create(nombre.getValue(), divisa.getValue(), usuario);
                mostrarEventos();
            } catch (Validator.InvalidValueException ex) {
                Notification n = new Notification("Error con los campos", Notification.Type.WARNING_MESSAGE);
                n.setPosition(Position.TOP_CENTER);
                n.show(Page.getCurrent());
            }
        });
        form.setMargin(true);
        setSecondComponent(form);
    }

    private Table getTablaEventos() {
        List<Evento> eventos = EventoDAO.readAll(usuario.getId());
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
        return table;
    }

    private Table getTablaGastos(Evento e) {
        List<Gasto> gastos = GastoDAO.readAll(e.getId());
        Table table = new Table("Gastos");
        table.addContainerProperty("Nombre", String.class, null);
        table.addContainerProperty("Precio", Double.class, null);
        table.addContainerProperty("Pagador", String.class, null);
        for (Gasto g : gastos) {
            table.addItem(g.getArray(), null);
        }
        table.setPageLength(table.size());
        table.setWidth(100, UNITS_PERCENTAGE);
        table.setSelectable(true);
        table.setImmediate(true);
        //SI CLICAMOS EN UN GASTO DE LA TABLA SE MUESTRA
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                try {
                    Gasto g = gastos.get(((int) table.getValue()) - 1);
                    mostrarGasto(g,e);
                } catch (Exception e) {
                }
            }
        });
        return table;
    }

    private Table getTablaParticipantes(Evento e) {
        List<Participante> participantes = ParticipanteDAO.readAllFromEvento(e.getId());
        Table table = new Table("Participantes");
        table.addContainerProperty("Nombre", String.class, null);
        for (Participante p : participantes) {
            table.addItem(p.getArray(), null);
        }
        table.setPageLength(table.size());
        table.setWidth(100, UNITS_PERCENTAGE);
        return table;
    }

}
