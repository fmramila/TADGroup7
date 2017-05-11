package tad.grupo7.ccamistadeslargas;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class NavigatorUI extends UI {
    
     Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        navigator.addView("", new LoginView());
        navigator.addView("index", new IndexView());
        navigator.addView("AdminIndex", new AdminIndexView());
        navigator.addView("registrar", new RegistrarView());
    }

    @WebServlet(urlPatterns = "/*", name = "NavigatorUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
