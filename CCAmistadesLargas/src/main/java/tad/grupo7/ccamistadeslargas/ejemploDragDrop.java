/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.InfoWindowClosedListener;
import com.vaadin.tapio.googlemaps.client.events.MapClickListener;
import com.vaadin.tapio.googlemaps.client.events.MapMoveListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.layers.GoogleMapKmlLayer;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapInfoWindow;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolygon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;

/**
 *
 * @author cayetano
 */
public class ejemploDragDrop extends VerticalLayout implements View {

    private GoogleMap googleMap;
    private GoogleMapMarker kakolaMarker = new GoogleMapMarker(
            "DRAGGABLE: Kakolan vankila", new LatLon(60.44291, 22.242415),
            true, null);
    private GoogleMapInfoWindow kakolaInfoWindow = new GoogleMapInfoWindow(
            "Kakola used to be a provincial prison.", kakolaMarker);
    private GoogleMapMarker maariaMarker = new GoogleMapMarker("Maaria",
            new LatLon(60.536403, 22.344648), false);
    private GoogleMapInfoWindow maariaWindow = new GoogleMapInfoWindow(
            "Maaria is a district of Turku", maariaMarker);
    ;
    private Button componentToMaariaInfoWindowButton;
    private final String apiKey = "";

    public ejemploDragDrop() {

        /*
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
         */
 /*
        String basepath = VaadinService.getCurrent()
                  .getBaseDirectory().getAbsolutePath()+"/WEB-INF/wallpaper.jpg";
        addComponent(new Label(basepath));
        FileResource resource = new FileResource(new File(basepath));
        Image image = new Image("", resource);
        addComponent(image);

         */
//        CssLayout rootLayout = new CssLayout();
//        rootLayout.setSizeFull();
//        setContent(rootLayout);

//        TabSheet tabs = new TabSheet();
//        tabs.setSizeFull();
//        rootLayout.addComponent(tabs);

//        VerticalLayout mapContent = new VerticalLayout();
//        mapContent.setSizeFull();
//        tabs.addTab(mapContent, "The map map");
//        tabs.addTab(new Label("An another tab"), "The other tab");

        googleMap = new GoogleMap(null, null, null);
        // uncomment to enable Chinese API.
        //googleMap.setApiUrl("maps.google.cn");
        googleMap.setCenter(new LatLon(60.440963, 22.25122));
        googleMap.setZoom(10);
        googleMap.setSizeFull();
        kakolaMarker.setAnimationEnabled(false);
        googleMap.addMarker(kakolaMarker);
        googleMap.addMarker("DRAGGABLE: Paavo Nurmi Stadion", new LatLon(
                60.442423, 22.26044), true, "VAADIN/1377279006_stadium.png");
        googleMap.addMarker("NOT DRAGGABLE: Iso-Heikkilä", new LatLon(
                60.450403, 22.230399), false, null);
        googleMap.addMarker(maariaMarker);
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(16);

        kakolaInfoWindow.setWidth("400px");
        kakolaInfoWindow.setHeight("500px");

        addComponent(googleMap);
        setExpandRatio(googleMap, 1.0f);

        Panel console = new Panel();
        console.setHeight("100px");
        final CssLayout consoleLayout = new CssLayout();
        console.setContent(consoleLayout);
        addComponent(console);

        HorizontalLayout buttonLayoutRow1 = new HorizontalLayout();
        buttonLayoutRow1.setHeight("26px");
        addComponent(buttonLayoutRow1);

        HorizontalLayout buttonLayoutRow2 = new HorizontalLayout();
        buttonLayoutRow2.setHeight("26px");
        addComponent(buttonLayoutRow2);

//        OpenInfoWindowOnMarkerClickListener infoWindowOpener = new OpenInfoWindowOnMarkerClickListener(
//                googleMap, kakolaMarker, kakolaInfoWindow);

//        googleMap.addMarkerClickListener(infoWindowOpener);

        googleMap.addMarkerClickListener(new MarkerClickListener() {
            @Override
            public void markerClicked(GoogleMapMarker clickedMarker) {
                Label consoleEntry = new Label("Marker \""
                        + clickedMarker.getCaption() + "\" at ("
                        + clickedMarker.getPosition().getLat() + ", "
                        + clickedMarker.getPosition().getLon() + ") clicked.");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        });

        googleMap.addMapMoveListener(new MapMoveListener() {
            @Override
            public void mapMoved(int zoomLevel, LatLon center, LatLon boundsNE,
                    LatLon boundsSW) {
                Label consoleEntry = new Label("Map moved to ("
                        + center.getLat() + ", " + center.getLon() + "), zoom "
                        + zoomLevel + ", boundsNE: (" + boundsNE.getLat()
                        + ", " + boundsNE.getLon() + "), boundsSW: ("
                        + boundsSW.getLat() + ", " + boundsSW.getLon() + ")");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        });

        googleMap.addMapClickListener(new MapClickListener() {
            @Override
            public void mapClicked(LatLon position) {
                Label consoleEntry = new Label("Map click to ("
                        + position.getLat() + ", " + position.getLon() + ")");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        });

        googleMap.addMarkerDragListener(new MarkerDragListener() {
            @Override
            public void markerDragged(GoogleMapMarker draggedMarker,
                    LatLon oldPosition) {
                Label consoleEntry = new Label("Marker \""
                        + draggedMarker.getCaption() + "\" dragged from ("
                        + oldPosition.getLat() + ", " + oldPosition.getLon()
                        + ") to (" + draggedMarker.getPosition().getLat()
                        + ", " + draggedMarker.getPosition().getLon() + ")");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        });

        googleMap.addInfoWindowClosedListener(new InfoWindowClosedListener() {

            @Override
            public void infoWindowClosed(GoogleMapInfoWindow window) {
                Label consoleEntry = new Label("InfoWindow \""
                        + window.getContent() + "\" closed");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        });

        Button moveCenterButton = new Button(
                "Move over Luonnonmaa (60.447737, 21.991668), zoom 12",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                googleMap.setCenter(new LatLon(60.447737, 21.991668));
                googleMap.setZoom(12);
            }
        });
        buttonLayoutRow1.addComponent(moveCenterButton);

        Button limitCenterButton = new Button(
                "Limit center between (60.619324, 22.712753), (60.373484, 21.945083)",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                googleMap.setCenterBoundLimits(new LatLon(60.619324,
                        22.712753), new LatLon(60.373484, 21.945083));
                event.getButton().setEnabled(false);
            }
        });
        buttonLayoutRow1.addComponent(limitCenterButton);

        Button limitVisibleAreaButton = new Button(
                "Limit visible area between (60.494439, 22.397835), (60.373484, 21.945083)",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                googleMap.setVisibleAreaBoundLimits(new LatLon(
                        60.494439, 22.397835), new LatLon(60.420632,
                        22.138626));
                event.getButton().setEnabled(false);
            }
        });
        buttonLayoutRow1.addComponent(limitVisibleAreaButton);

        Button zoomToBoundsButton = new Button("Zoom to bounds",
                new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                googleMap.fitToBounds(new LatLon(60.45685853323144,
                        22.320034754486073), new LatLon(
                        60.4482979242303, 22.27887893936156));

            }
        });
        buttonLayoutRow1.addComponent(zoomToBoundsButton);

        Button addMarkerToMaariaButton = new Button("Open Maaria Window",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                googleMap.openInfoWindow(maariaWindow);
            }
        });
        buttonLayoutRow1.addComponent(addMarkerToMaariaButton);

        componentToMaariaInfoWindowButton = new Button(
                "Add component to Maaria window",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                googleMap.setInfoWindowContents(maariaWindow,
                        new Button("Maaria button does something", new Button.ClickListener() {
                            @Override
                            public void buttonClick(ClickEvent event) {
                                Notification.show("hello there!");
                            }
                        }));
            }
        });
        buttonLayoutRow1.addComponent(componentToMaariaInfoWindowButton);

        Button addPolyOverlayButton = new Button("Add overlay over Luonnonmaa",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ArrayList<LatLon> points = new ArrayList<LatLon>();
                points.add(new LatLon(60.484715, 21.923706));
                points.add(new LatLon(60.446636, 21.941387));
                points.add(new LatLon(60.422496, 21.99546));
                points.add(new LatLon(60.427326, 22.06464));
                points.add(new LatLon(60.446467, 22.064297));

                GoogleMapPolygon overlay = new GoogleMapPolygon(points,
                        "#ae1f1f", 0.8, "#194915", 0.5, 3);
                googleMap.addPolygonOverlay(overlay);
                event.getButton().setEnabled(false);
            }
        });
        buttonLayoutRow2.addComponent(addPolyOverlayButton);

        Button addPolyLineButton = new Button("Draw line from Turku to Raisio",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ArrayList<LatLon> points = new ArrayList<LatLon>();
                points.add(new LatLon(60.448118, 22.253738));
                points.add(new LatLon(60.455144, 22.24198));
                points.add(new LatLon(60.460222, 22.211939));
                points.add(new LatLon(60.488224, 22.174602));
                points.add(new LatLon(60.486025, 22.169195));

                GoogleMapPolyline overlay = new GoogleMapPolyline(
                        points, "#d31717", 0.8, 10);
                googleMap.addPolyline(overlay);
                event.getButton().setEnabled(false);
            }
        });
        buttonLayoutRow2.addComponent(addPolyLineButton);
        Button addPolyLineButton2 = new Button(
                "Draw line from Turku to Raisio2", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                ArrayList<LatLon> points2 = new ArrayList<LatLon>();
                points2.add(new LatLon(60.448118, 22.253738));
                points2.add(new LatLon(60.486025, 22.169195));
                GoogleMapPolyline overlay2 = new GoogleMapPolyline(
                        points2, "#d31717", 0.8, 10);
                googleMap.addPolyline(overlay2);
                event.getButton().setEnabled(false);
            }
        });
        buttonLayoutRow2.addComponent(addPolyLineButton2);
        Button changeToTerrainButton = new Button("Change to terrain map",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                googleMap.setMapType(GoogleMap.MapType.Terrain);
                event.getButton().setEnabled(false);
            }
        });
        buttonLayoutRow2.addComponent(changeToTerrainButton);

        Button changeControls = new Button("Remove street view control",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                googleMap.removeControl(GoogleMapControl.StreetView);
                event.getButton().setEnabled(false);
            }
        });
        buttonLayoutRow2.addComponent(changeControls);

        Button addInfoWindowButton = new Button(
                "Add InfoWindow to Kakola marker", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                googleMap.openInfoWindow(kakolaInfoWindow);
            }
        });
        buttonLayoutRow2.addComponent(addInfoWindowButton);

        Button moveMarkerButton = new Button("Move kakola marker",
                new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                kakolaMarker.setPosition(new LatLon(60.3, 22.242415));
                googleMap.addMarker(kakolaMarker);
            }
        });
        buttonLayoutRow2.addComponent(moveMarkerButton);

        Button addKmlLayerButton = new Button("Add KML layer",
                new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                googleMap
                        .addKmlLayer(new GoogleMapKmlLayer(
                                "http://maps.google.it/maps/"
                                + "ms?authuser=0&ie=UTF8&hl=it&oe=UTF8&msa=0&"
                                + "output=kml&msid=212897908682884215672.0004ecbac547d2d635ff5"));
            }
        });
        buttonLayoutRow2.addComponent(addKmlLayerButton);

        Button clearMarkersButton = new Button("Remove all markers",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                googleMap.clearMarkers();
            }
        });

        Button trafficLayerButton = new Button("Toggle Traffic Layer",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                googleMap.setTrafficLayerVisible(
                        !googleMap.isTrafficLayerVisible());
            }
        });
        buttonLayoutRow2.addComponent(trafficLayerButton);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

}
