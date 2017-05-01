/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.teemusa.sidemenu.SideMenu;

/**
 *
 * @author cayetano
 */
@Theme("mytheme")
public class IndexView extends SideMenu implements View{

    private VerticalLayout mainLayout = new EventosLayout();
    
    public IndexView(){
        setMenuCaption("CCAmistadesLargas");
        addMenuItem("Eventos", () -> {
            removeAllComponents();
            mainLayout = new EventosLayout();
            addComponent(mainLayout);
        });
        addMenuItem("Usuarios", () -> {
            removeAllComponents();
            mainLayout = new UsuariosLayout();
            addComponent(mainLayout);
        });
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        
    }
    
}
