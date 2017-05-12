/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;
import org.vaadin.teemusa.sidemenu.SideMenu;

/**
 *
 * @author Naiara
 */
class AdminIndexView extends SideMenu implements View {

    public AdminIndexView() {
        setMenuCaption("CCAmistadesLargas");
        addMenuItem("Listado", () -> {
            removeAllComponents();
            addComponent(new ListadoLayout());
        });
        addMenuItem("Grafica", () -> {
            removeAllComponents();
            addComponent(new GraficaLayout());
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
