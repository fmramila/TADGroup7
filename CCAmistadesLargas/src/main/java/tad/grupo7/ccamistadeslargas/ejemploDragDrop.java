/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import fi.jasoft.dragdroplayouts.DDPanel;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultPanelDropHandler;

/**
 *
 * @author cayetano
 */
public class ejemploDragDrop extends VerticalLayout implements View {

    public ejemploDragDrop() {


         DDPanel panel1 = new DDPanel("Source");
         panel1.setWidth("200px");
         panel1.setHeight("200px");
         panel1.setDragMode(LayoutDragMode.CLONE);
         panel1.setDropHandler(new DefaultPanelDropHandler());
         addComponent(panel1);
 
         Button content = new Button("Drag me!");
         content.setSizeFull();
         
 
         DDPanel panel2 = new DDPanel("Destination");
         panel2.setWidth("200px");
         panel2.setHeight("200px");
         panel2.setDragMode(LayoutDragMode.CLONE);
         panel2.setDropHandler(new DefaultPanelDropHandler());
         addComponent(panel2);
         
         panel2.setContent(content);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

}
