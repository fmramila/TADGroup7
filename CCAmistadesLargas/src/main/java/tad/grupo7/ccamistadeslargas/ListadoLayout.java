/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Page;
import static com.vaadin.server.Sizeable.UNITS_PERCENTAGE;
import com.vaadin.shared.Position;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Iterator;
import java.util.List;
import tad.grupo7.ccamistadeslargas.DAO.UsuarioDAO;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author Naiara
 */
public class ListadoLayout extends VerticalLayout {

    public ListadoLayout() {
        mostrarListado();
    }

    /**
     * Muestra una tabla con todos los usuarios.
     */
    private void mostrarListado() {
        removeAllComponents();
        //TÍTULO
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");
        Label l = new Label("Selecciona un usuario para eliminarlo");
        l.setSizeUndefined();
        l.addStyleName(ValoTheme.LABEL_H2);
        l.addStyleName(ValoTheme.LABEL_COLORED);
        //TABLA DE USUARIOS
        Table table = getTablaListado();
        //AÑADIMOS COMPONENTES
        addComponents(l,table);
        setMargin(true);

    }

    /**
     * Obtiene una tabla con todos los usuarios.
     *
     * @return Table
     */
    private Table getTablaListado() {
        List<Usuario> usuarios = UsuarioDAO.readAll();
        Table table = new Table("");
        table.addContainerProperty("Nombre", String.class, null);
        table.addContainerProperty("Password", String.class, null);
        table.addContainerProperty("Email", String.class, null);
        Iterator<Usuario> it = usuarios.iterator();
        it.next();
        while(it.hasNext()){
            table.addItem(it.next().getArray(), null);
        }

        table.setPageLength(table.size());
        table.setWidth(100, UNITS_PERCENTAGE);
        table.setSelectable(true);
        table.setImmediate(true);

        table.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                try {
                    Usuario u = usuarios.get((int) table.getValue());
                    UsuarioDAO.delete(u.getId());
                    mostrarListado();
                    Notification n = new Notification("Usuario eliminado", Notification.Type.WARNING_MESSAGE);
                    n.setPosition(Position.TOP_CENTER);
                    n.show(Page.getCurrent());
                } catch (Exception e) {
                }
            }
        });

        return table;
    }
}
