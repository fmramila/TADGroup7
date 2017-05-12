/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import java.io.File;
import org.vaadin.teemusa.sidemenu.SideMenu;

/**
 *
 * @author Naiara
 */
class AdminIndexView extends SideMenu implements View {

    public AdminIndexView() {
        setMenuCaption("CCAmistadesLargas - ADMIN");
        String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath() + "/WEB-INF/wallpaper2.jpg";
        FileResource resource = new FileResource(new File(basepath));
        Image image = new Image(null, resource);
        image.setSizeFull();
        addComponent(image);
        addMenuItem("Listado",FontAwesome.LIST, () -> {
            removeAllComponents();
            addComponent(new ListadoLayout());
        });
        addMenuItem("Grafica",FontAwesome.PIE_CHART, () -> {
            removeAllComponents();
            addComponent(new GraficaLayout());
        });
        addMenuItem("Cerrar sesión",FontAwesome.POWER_OFF, () -> {
            Session.destroy();
            UI.getCurrent().getNavigator().navigateTo("");
        });
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
