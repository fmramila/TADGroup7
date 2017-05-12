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
import com.vaadin.ui.themes.ValoTheme;
import java.util.Iterator;
import java.util.List;
import tad.grupo7.ccamistadeslargas.DAO.ParticipanteDAO;
import tad.grupo7.ccamistadeslargas.DAO.UsuarioDAO;
import tad.grupo7.ccamistadeslargas.modelo.Participante;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author cayetano
 */
class AmigosLayout extends HorizontalSplitPanel {

    private final Usuario usuario;

    public AmigosLayout() {
        usuario = (Usuario) Session.getAttribute("usuario");
        mostrarAmistades();
    }

    /**
     * Muestra el listado de amistades del usuario creadas por él.
     */
    private void mostrarAmistades() {
        removeAllComponents();
        //BOTÓN PARA AÑADIR OTRO PARTICIPANTE
        Button addAmistadBoton = new Button("Añadir Amistad");
        addAmistadBoton.addClickListener(clickEvent -> {
            mostrarFormularioAddAmistad();
        });
        //TABLA DE LOS PARTICIPANTES
        Table table = getTablaAmistades();
        //AÑADIMOS LOS COMPONENTES
        VerticalLayout col1 = new VerticalLayout(addAmistadBoton, table);
        col1.setMargin(true);
        setFirstComponent(col1);
    }

    /**
     * Muestra una amistad en concreto.
     * @param p Participante
     */
    private void mostrarParticipante(Participante p) {
        //FORMULARIO POR SI SE QUIERE EDITAR EL PARTICIPANTE
        TextField nombre = new TextField("Nombre");
        nombre.setValue(p.getNombre());
        final Button eliminar = new Button("Eliminar Participante");
        final Button actualizar = new Button("Actualizar Participante");

        //BOTÓN PARA ACTUALIZAR EL PARTICIPANTE
        actualizar.addClickListener(clickEvent -> {
            if (ParticipanteDAO.read(nombre.getValue(), usuario.getId()) == null) {
                ParticipanteDAO.update(p.getId(), nombre.getValue());
                UsuarioDAO.updateAmigo(nombre.getValue(), p.getId(), usuario.getId());
                Notification n = new Notification("Amigo actualizado", Notification.Type.ASSISTIVE_NOTIFICATION);
                n.setPosition(Position.TOP_CENTER);
                n.show(Page.getCurrent());
                removeAllComponents();
                mostrarAmistades();
            } else {
                Notification n = new Notification("Ya existe un amigo con el mismo nombre", Notification.Type.WARNING_MESSAGE);
                n.setPosition(Position.TOP_CENTER);
                n.show(Page.getCurrent());
            }

        });
        //BOTÓN PARA ELIMINAR EL PARTICIPANTE
        eliminar.addClickListener(clickEvent -> {
            ParticipanteDAO.delete(p.getId());
            UsuarioDAO.removeAmigo(nombre.getValue(), usuario.getId());
            removeAllComponents();
            mostrarAmistades();
        });

        //AÑADIMOS LOS COMPONENTES
        FormLayout form = new FormLayout(nombre, actualizar, eliminar);
        VerticalLayout l = new VerticalLayout(form);
        l.setMargin(true);
        setSecondComponent(l);
    }

    /**
     * Obtiene la tabla de amistades de la BD.
     * @return Table
     */
    private Table getTablaAmistades() {
        List<Participante> participantes = ParticipanteDAO.readAllFromUsuario(usuario.getId());
        Table table = new Table("");
        table.addContainerProperty("Nombre", String.class, null);
        Iterator<Participante> it = participantes.iterator();
        it.next();
        while (it.hasNext()) {
            table.addItem(it.next().getArray(), null);
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
                    Participante p = participantes.get(((int) table.getValue()));
                    mostrarParticipante(p);
                } catch (Exception e) {
                }
            }

        });
        return table;
    }

    /**
     * Muestra el formulario para añadir una nueva amistad.
     */
    private void mostrarFormularioAddAmistad() {
        TextField nombre = new TextField("Nombre");
        nombre.setRequired(true);

        final Button add = new Button("Crear Participante");
        add.addStyleName(ValoTheme.BUTTON_PRIMARY);
        FormLayout form = new FormLayout(nombre, add);
        add.addClickListener(clickEvent -> {
            try {
                nombre.validate();
                if (ParticipanteDAO.read(nombre.getValue(), usuario.getId()) == null) {
                    ParticipanteDAO.create(nombre.getValue(), usuario.getId());
                    UsuarioDAO.addAmigo(nombre.getValue(), usuario.getId());
                    mostrarAmistades();
                } else {
                    Notification n = new Notification("Ya existe un amigo con el mismo nombre", Notification.Type.WARNING_MESSAGE);
                    n.setPosition(Position.TOP_CENTER);
                    n.show(Page.getCurrent());
                }
            } catch (Validator.InvalidValueException ex) {
                Notification n = new Notification("Error con los campos", Notification.Type.WARNING_MESSAGE);
                n.setPosition(Position.TOP_CENTER);
                n.show(Page.getCurrent());
            }
        });
        form.setMargin(true);
        setSecondComponent(form);
    }

}
