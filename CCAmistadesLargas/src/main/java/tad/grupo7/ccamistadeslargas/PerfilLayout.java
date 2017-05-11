/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import tad.grupo7.ccamistadeslargas.DAO.GastoDAO;
import tad.grupo7.ccamistadeslargas.DAO.UsuarioDAO;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 *
 * @author cayetano
 */
public class PerfilLayout extends VerticalLayout {

    private final Usuario usuario;

    public PerfilLayout() {
        usuario = (Usuario) Session.getAttribute("usuario");
        mostrarPerfil();
    }

    private void mostrarPerfil() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");
        Label l = new Label("Perfil");
        l.setSizeUndefined();
        l.addStyleName(ValoTheme.LABEL_H2);
        l.addStyleName(ValoTheme.LABEL_COLORED);
        TextField nombre = new TextField("Nombre");
        nombre.setValue(usuario.getNombre());
        nombre.setRequired(true);
        TextField password = new TextField("Password");
        password.setValue(usuario.getPassword());
        password.setRequired(true);
        TextField email = new TextField("Email");
        email.setValue(usuario.getPassword());
        email.setEnabled(false);
        Button actualizar = new Button("Actualizar");

        actualizar.addClickListener(clickEvent -> {
            UsuarioDAO.update(usuario.getId(), nombre.getValue(), password.getValue(), usuario.getEmail());
            Notification n = new Notification("Usuario actualizado", Notification.Type.ASSISTIVE_NOTIFICATION);
            n.setPosition(Position.TOP_CENTER);
            n.show(Page.getCurrent());
            usuario.setNombre(nombre.getValue());
            usuario.setPassword(password.getValue());
        });

        FormLayout form = new FormLayout(l, nombre, password, email, actualizar);
        form.setMargin(true);
        addComponents(form);
    }

}
