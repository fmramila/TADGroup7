/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tad.grupo7.ccamistadeslargas;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.data.Property;
import com.vaadin.server.GAEVaadinServlet;
import static com.vaadin.server.Sizeable.UNITS_PERCENTAGE;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import tad.grupo7.ccamistadeslargas.DAO.EventoDAO;
import tad.grupo7.ccamistadeslargas.DAO.GastoDAO;
import tad.grupo7.ccamistadeslargas.DAO.ParticipanteDAO;
import tad.grupo7.ccamistadeslargas.modelo.Evento;
import tad.grupo7.ccamistadeslargas.modelo.Gasto;
import tad.grupo7.ccamistadeslargas.modelo.Participante;

/**
 *
 * @author Naiara
 */
public class GraficaLayout extends HorizontalSplitPanel {

    final VerticalLayout col1 = new VerticalLayout();
    final VerticalLayout col2 = new VerticalLayout();

    public GraficaLayout() {
        mostrarEventos();
    }

    private void mostrarEventos() {
        removeAllComponents();
        Table table = getTablaEventos();

        col1.addComponents(table);
        col1.setMargin(true);
        setFirstComponent(col1);
        setSecondComponent(col2);

    }

    private Table getTablaEventos() {
        List<Evento> eventos = EventoDAO.readAll();
        List<Gasto> listaGasto;
        Double precio = 0.0;
        Gasto g;
        Table table = new Table("");
        table.addContainerProperty("Nombre", String.class, null);
        table.addContainerProperty("Gasto", Double.class, null);
        table.addContainerProperty("Divisa", String.class, null);

        for (Evento e : eventos) {
            listaGasto = GastoDAO.readAll(e.getId());
            for (int i = 0; i < listaGasto.size(); i++) {
                g = listaGasto.get(i);
                precio += g.getPrecio();
            }
            table.addItem(new Object[]{e.getNombre(), precio, e.getDivisa()}, null);
            precio = 0.0;
        }

        table.setPageLength(table.size());
        table.setWidth(100, UNITS_PERCENTAGE);
        table.setImmediate(true);

        table.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                //***************************
                col2.removeAllComponents();
                Chart chart = new Chart(ChartType.PIE);
                chart.setWidth("400px");
                chart.setHeight("300px");

                Configuration conf = chart.getConfiguration();
                conf.setTitle("Gastos por evento");

                //lo que se ha gastado cada participante en ese evento
                try {
                    Evento e = eventos.get(((int) table.getValue()) - 1);
                    List<Gasto> gastos = GastoDAO.readAll(e.getId());
                    final DataSeries series = new DataSeries();
                    for (Gasto g : gastos) {
                        series.add(new DataSeriesItem(g.getNombre(), g.getPrecio()));
                    }
                    conf.addSeries(series);
                } catch (Exception e) {
                }

                Chart chart1 = new Chart(ChartType.PIE);
                chart1.setWidth("400px");
                chart1.setHeight("300px");

                Configuration conf1 = chart1.getConfiguration();
                conf1.setTitle("Gastos por participante");

                //lo que se ha gastado cada participante en ese evento
                try {
                    Evento e = eventos.get(((int) table.getValue()) - 1);
                    List<Gasto> gastos = GastoDAO.readAll(e.getId());
                    List<Participante> participantes = ParticipanteDAO.readAllFromEvento(e.getId());
                    Participante p;
                    Gasto g;
                    Double pago = 0.0;
                    String nombre;
                    final DataSeries series = new DataSeries();

                    for (Participante par : participantes) {
                        nombre = par.getNombre();
                        for (int j = 0; j < gastos.size(); j++) {
                            g = gastos.get(j);
                            if (par.getId().equals(g.getIdPagador())) {
                                pago += g.getPrecio();
                            }
                        }
                        if (pago != 0) {
                            series.add(new DataSeriesItem(nombre, pago));
                            pago = 0.0;
                        }
                    }

                    conf1.addSeries(series);
                } catch (Exception e) {
                }

                col2.addComponents(chart, chart1);
                col2.setMargin(true);

                //******************************
            }
        });
        return table;
    }
}
