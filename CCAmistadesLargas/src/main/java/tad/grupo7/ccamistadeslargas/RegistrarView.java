/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.data.Validator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import tad.grupo7.ccamistadeslargas.DAO.UsuarioDAO;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

/**
 * Vista encargada del formulario de registro.
 * 
 * @author cayetano
 */
public class RegistrarView extends VerticalLayout implements View {

    public RegistrarView() {
        setMargin(true);
        setSpacing(true);
        //TÍTULO
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");
        Label l = new Label("Registro");
        l.setSizeUndefined();
        l.addStyleName(ValoTheme.LABEL_H2);
        l.addStyleName(ValoTheme.LABEL_COLORED);
        //FORMULARIO
        TextField nombre = new TextField("Nombre");
        nombre.setRequired(true);
        PasswordField password = new PasswordField("Contraseña");
        password.setRequired(true);
        TextField email = new TextField("Email");
        email.setRequired(true);
        final Button registrar = new Button("Sign Up");
        registrar.addStyleName(ValoTheme.BUTTON_PRIMARY);
        FormLayout form = new FormLayout(nombre, password, email, registrar);
        //BOTÓN PARA REGISTRARSE
        registrar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                try {
                    nombre.validate();
                    password.validate();
                    email.validate();
                    UsuarioDAO.create(nombre.getValue(),password.getValue(),email.getValue());
                    Usuario u = UsuarioDAO.read(nombre.getValue(), password.getValue());
                    Session.setAttribute("usuario", u);
                    UI.getCurrent().getNavigator().navigateTo("index");
                } catch (Validator.InvalidValueException ex) {

                }
            }

        });
        //AÑADIR COMPONENTES
        addComponents(l,form);
        setComponentAlignment(form, Alignment.MIDDLE_CENTER);
    }

    @Override

    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

}
