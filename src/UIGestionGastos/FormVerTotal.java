/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGestionGastos;

import ModeloGestionGastos.Moneda;
import javax.microedition.lcdui.Form;
import ModeloGestionGastos.Gasto;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

/**
 *
 * @author tAMs
 */
public class FormVerTotal extends Form {

    private Gasto[] gastos;
    private Moneda[] monedas;
    private Alert alertaVerTotal;
    private FormListaMonedas formularioListaMonedas;

    public FormVerTotal(Gasto[] gasts, Moneda[] mons, FormListaMonedas ListaMonedas) {
        super("Gasto Total");
        formularioListaMonedas = ListaMonedas;
        setGastos(gasts);
        setMonedas(mons);
        render();
    } //Creation Method

    private void render() {
        alertaVerTotal = new Alert("Total de Gastos", "El Total Acumulado es : " + getCantidadTotal() + " " + formularioListaMonedas.getMonedaReferencia(), null, AlertType.INFO);
        alertaVerTotal.setTimeout(Alert.FOREVER);
    } //render

    public void update(Gasto[] gasts) {
        setGastos(gasts);
        deleteAll();
        render();
    } //update

    public Gasto[] getGastos() {
        return gastos;
    } //getMonedas

    public void setGastos(Gasto[] gasts) {
        gastos = gasts;
    } //setGastos

    public void setMonedas(Moneda[] mons) {
        monedas = mons;
    } //setGastos

    public Alert getAlert() {
        return alertaVerTotal;
    }

    public double getCantidadTotal() {
        double auxTotal = 0;

        if (gastos != null) {
            for (int i = 0; i < gastos.length; i++) {
                auxTotal += gastos[i].getCantidad()*gastos[i].getMoneda().getRelacion();
            } //for
        } //if
        return auxTotal;
    }

} //class FormListaMonedas

