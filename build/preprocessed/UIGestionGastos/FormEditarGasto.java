/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGestionGastos;

import ModeloGestionGastos.Fecha;
import ModeloGestionGastos.Gasto;
import ModeloGestionGastos.Moneda;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author tAMs
 */
public class FormEditarGasto extends Form {

    DateField fecha;
    TextField categoria;
    TextField cantidad;
    ChoiceGroup moneda;

    public FormEditarGasto(Moneda[] vectorMonedas, Gasto gastoOriginal) {
        super("Añadir Gasto");
        fecha = new DateField("Fecha", DateField.DATE);
        fecha.setDate(gastoOriginal.getFecha().getDate());
        categoria = new TextField("Categoria", gastoOriginal.getCategoria(), 120, TextField.ANY);
        cantidad = new TextField("Cantidad", String.valueOf(gastoOriginal.getCantidad()), 120, TextField.DECIMAL);
        moneda = new ChoiceGroup("Moneda", ChoiceGroup.POPUP);
        int selectedIndex = 0;
        for (int i = 0; i < vectorMonedas.length; i++) {
            moneda.append(vectorMonedas[i].getNombreReducido(), null);
            if (gastoOriginal.getMoneda().getNombreReducido().equals(vectorMonedas[i].getNombreReducido())) {
                selectedIndex = i;
            }
        } //for
        moneda.setSelectedIndex(selectedIndex, true);
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

    public TextField getCampoCategoria() {
        return categoria;
    } //getCategoria

    public TextField getCampoCantidad() {
        return cantidad;
    } //getCantidad

    public ChoiceGroup getCampoMoneda() {
        return moneda;
    } //getCampoMoneda

    public Fecha getFecha() {
        return (new Fecha(getCampoFecha().getDate()));
    } //getFecha

    public String getCategoria(){
        return getCampoCategoria().getString();
    }

    public double getCantidad(){
        return (Double.valueOf(getCampoCantidad().getString()).doubleValue());
    }

    public int getMonedaIndex() {
        return getCampoMoneda().getSelectedIndex();
    } //getMonedaIndex
} //class FormListaGastos

