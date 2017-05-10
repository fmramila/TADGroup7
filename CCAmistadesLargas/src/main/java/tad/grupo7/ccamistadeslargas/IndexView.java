/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalSplitPanel;
import org.vaadin.teemusa.sidemenu.SideMenu;

/**
 *
 * @author cayetano
 */
@Theme("mytheme")
public class IndexView extends SideMenu implements View{
    

    public IndexView(){
        setMenuCaption("CCAmistadesLargas");
        addMenuItem("Eventos", () -> {
            removeAllComponents();
            addComponent(new EventosLayout());
        });
        addMenuItem("Amigos", () -> {
            removeAllComponents();
            addComponent(new AmigosLayout());
        });
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
    
}
