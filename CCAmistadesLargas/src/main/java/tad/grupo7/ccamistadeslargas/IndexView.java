/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import java.io.File;
import org.vaadin.teemusa.sidemenu.SideMenu;

/**
 *
 * @author cayetano
 */
@Theme("mytheme")
public class IndexView extends SideMenu implements View {

    public IndexView() {
        setMenuCaption("CCAmistadesLargas");
        String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath() + "/WEB-INF/wallpaper2.jpg";
        FileResource resource = new FileResource(new File(basepath));
        Image image = new Image(null, resource);
        image.setSizeFull();
        image.setHeight("100%");
        addComponent(image);
        addMenuItem("Perfil", () -> {
            removeAllComponents();
            addComponent(new PerfilLayout());
        });
        addMenuItem("Eventos", () -> {
            removeAllComponents();
            addComponent(new EventosLayout());
        });
        addMenuItem("Amigos", () -> {
            removeAllComponents();
            addComponent(new AmigosLayout());
        });
        addMenuItem("Cerrar sesión", () -> {
            Session.destroy();
            UI.getCurrent().getNavigator().navigateTo("");
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
