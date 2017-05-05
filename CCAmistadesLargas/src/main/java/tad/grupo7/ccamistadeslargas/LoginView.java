/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Validator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import tad.grupo7.ccamistadeslargas.DAO.Participante_has_EventoDAO;
import tad.grupo7.ccamistadeslargas.DAO.UsuarioDAO;
import tad.grupo7.ccamistadeslargas.modelo.Participante_has_Evento;
import tad.grupo7.ccamistadeslargas.modelo.Usuario;

@SuppressWarnings("serial")
@Theme("mytheme")
public class LoginView extends VerticalLayout implements View {

    public LoginView() {
        setSizeFull();
        buildLoginForm();
        
        /*
        ParticipanteDAO.create(new Participante("nai", "nai", 1));
        ParticipanteDAO.create(new Participante("fer", "fer", 1));



        Participante_has_EventoDAO.create(new Participante_has_Evento(4,2)); //idPart, idEven
        
        
        List<Integer> lista = Participante_has_EventoDAO.readAllParticipantesID(1);
        for (int i : lista) {
            System.out.println("HOLA: " + i);
        }
        if (lista.size() == 0) {
            System.out.println("\n\nPATATA\n\n");
        }
        */
    }

    /**
     * Crea el formulario.
     */
    private void buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildFields());
        addComponent(loginPanel);
        setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    /**
     * Crea los campos del formulario.
     *
     * @return Component Devuelve el layout que contiene todos los campos del
     * formulario.
     */
    private Component buildFields() {
        //LAYOUT CON LOS CAMPOS DEL FORMULARIO
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        final TextField email = new TextField("Email");
        email.setRequired(true);
        email.setIcon(FontAwesome.USER);
        email.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField password = new PasswordField("Password");
        password.setRequired(true);
        password.setIcon(FontAwesome.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final Button signin = new Button("Sign In");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(KeyCode.ENTER);
        signin.focus();

        final Button registrar = new Button("Sign Up");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);

        fields.addComponents(email, password, signin, registrar);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
        fields.setComponentAlignment(registrar, Alignment.BOTTOM_LEFT);

        //LOGARSE
        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                try {
                    email.validate();
                    password.validate();
                    Usuario u = UsuarioDAO.read(email.getValue(), password.getValue());
                    if (u != null) {
                        Session.setAttribute("usuario", u);
                        UI.getCurrent().getNavigator().navigateTo("index");
                    } else {
                        Notification n = new Notification("Usuario incorrecto", Notification.Type.WARNING_MESSAGE);
                        n.setPosition(Position.TOP_CENTER);
                        n.show(Page.getCurrent());
                    }
                } catch (Validator.InvalidValueException ex) {
                    Notification n = new Notification("Rellena todos los campos", Notification.Type.WARNING_MESSAGE);
                    n.setPosition(Position.TOP_CENTER);
                    n.show(Page.getCurrent());
                }
            }
        });
        //IR AL FORMULARIO DE REGISTRARSE
        registrar.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                UI.getCurrent().getNavigator().navigateTo("registrar");
            }
        });
        return fields;
    }

    @Override

    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

}
