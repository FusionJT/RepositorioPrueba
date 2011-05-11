/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGestionGastos;

import ModeloGestionGastos.Fecha;
import ModeloGestionGastos.Gasto;
import ModeloGestionGastos.Moneda;
import java.util.Date;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author tAMs
 */
public class FormAnadirGasto extends Form {

    DateField fecha;
    TextField categoria;
    TextField cantidad;
    ChoiceGroup moneda;

    public FormAnadirGasto(Moneda[] vectorMonedas) {

        super("Añadir Gasto");
        fecha = new DateField("Fecha", DateField.DATE);
        fecha.setDate(new Date());
        categoria = new TextField("Categoria", null, 100, TextField.ANY);
        cantidad = new TextField("Cantidad ", null, 100, TextField.DECIMAL);
        moneda = new ChoiceGroup("Selected", ChoiceGroup.POPUP);
        for (int i = 0; i < vectorMonedas.length; i++) {
            moneda.append(vectorMonedas[i].getNombreReducido(), null);
        } //for
        render();
    } //Creation Method

    private void render() {
        if ((fecha != null) && (categoria != null) && (cantidad != null)) {
            append(fecha);
            append(categoria);
            append(cantidad);
            append(moneda);
        } //if
    } //render

    public void update() {
        deleteAll();
        render();
    } //update

    public DateField getCampoFecha() {
        return fecha;
    } //getCampoFecha

    public Fecha getFecha() {
        return (new Fecha(getCampoFecha().getDate()));
    } //getFecha

    public TextField getCampoCategoria() {
        return categoria;
    } //getCampoCategoria

    public String getCategoria() {
        return getCampoCategoria().getString();
    } //getCategoria

    public TextField getCampoCantidad() {
        return cantidad;
    } //getCampoCantidad

    public double getCantidad() {
        if (!getCampoCantidad().getString().equals("")) {
            return Double.valueOf(getCampoCantidad().getString()).doubleValue();
        } else {
            return -1;
        } //if
    } //getCantidad

    public ChoiceGroup getCampoMoneda() {
        return moneda;
    } //getCampoMoneda

    public int getMonedaIndex() {
        return getCampoMoneda().getSelectedIndex();
    } //getMonedaIndex

    public boolean isCompleted() {
        if (getCategoria().equals("") || (getCantidad() == -1)) {
            return false;
        } else {
            return true;
        } //if
    } //isCompleted
} //class FormListaGastos

