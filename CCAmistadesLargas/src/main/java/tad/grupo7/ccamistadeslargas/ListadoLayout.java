/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import static com.vaadin.server.Sizeable.UNITS_PERCENTAGE;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import tad.grupo7.ccamistadeslargas.DAO.UsuarioDAO;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author Naiara
 */
public class ListadoLayout extends HorizontalSplitPanel {

    final VerticalLayout col1 = new VerticalLayout();
    final VerticalLayout col2 = new VerticalLayout();
    final Button eliminar = new Button("Eliminar Usuario");

    public ListadoLayout() {
        mostrarListado();
    }

    private void mostrarListado() {
        col1.removeAllComponents();
        Table table = getTablaListado();
        col1.addComponent(table);
        col1.setMargin(true);
        setFirstComponent(col1);
        setSecondComponent(col2);

    }

    private Table getTablaListado() {
        List<Usuario> usuarios = UsuarioDAO.readAll();
        Table table = new Table("");
        table.addContainerProperty("Nombre", String.class, null);
        table.addContainerProperty("Password", String.class, null);
        table.addContainerProperty("Email", String.class, null);

        for (Usuario u : usuarios) {
            table.addItem(u.getArray(), null);
        }

        table.setPageLength(table.size());
        table.setWidth(100, UNITS_PERCENTAGE);
        table.setImmediate(true);

        table.addValueChangeListener(new ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                col2.removeAllComponents();
                Label l = new Label();
                try {
                    Usuario u = usuarios.get((int) table.getValue() - 1);
                    l.setValue(u.getNombre());
                    eliminar.addClickListener(clickEvent -> {
                        UsuarioDAO.delete(u.getId());
                        col2.removeAllComponents();
                        mostrarListado();
                    });

                } catch (Exception e) {
                }
                col2.addComponents(l, eliminar);
                if(l.getValue() == null || l.getValue().equals("")){
                    col2.removeAllComponents();
                }
                col2.setMargin(true);
            }
        });

        return table;
    }
}
