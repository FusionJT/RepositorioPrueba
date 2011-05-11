/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGestionGastos;

import ModeloGestionGastos.Moneda;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author tAMs
 */
public class FormListaMonedas extends Form {

    private Moneda[] monedas;
    private ChoiceGroup monedaReferencia;

    public FormListaMonedas() {
        this(null);
    } //Creation Method

    public FormListaMonedas(Moneda[] vectorMonedas) {
        super("Monedas");
        monedaReferencia = new ChoiceGroup("Moneda Referencia", ChoiceGroup.POPUP);
        setMonedas(vectorMonedas);
        render();
    } //Creation Method

    private void render() {
        if (monedas != null) {
            for (int i = 0; i < monedas.length; i++) {
                StringItem item = new StringItem(null, monedas[i].toString());
                item.setLayout(Item.LAYOUT_NEWLINE_AFTER);
                append(item);
            } //for
            for (int i = 0; i < monedas.length; i++) {
                monedaReferencia.append(monedas[i].getNombreReducido(), null);
            } //for
            append(monedaReferencia);
        } //if
    } //render

    public void update(Moneda[] mons) {
        setMonedas(mons);
        deleteAll();
        render();
    } //update

    public void updateChoiceMonedas(String nuevaMoneda) {
        monedaReferencia.append(nuevaMoneda, null);
    }

    public Moneda[] getMonedas() {
        return monedas;
    } //getMonedas

    public String getMonedaReferencia() {
        return (monedaReferencia.getString(monedaReferencia.getSelectedIndex()));
    }

    public int getIndexMonedaReferencia() {
        return monedaReferencia.getSelectedIndex();
    }

    public double getCantidadMonedaReferencia(){
        return monedas[getIndexMonedaReferencia()].getRelacion();
    }

    public void setMonedas(Moneda[] mons) {
        monedas = mons;
    } //setMonedas

    public void setMonedaReferencia(String cad) {
        monedaReferencia.setLabel("Moneda de Referencia");
        monedaReferencia.set(0, cad, null);
    }

    public ChoiceGroup getChoiceGroupMonedas() {
        return monedaReferencia;
    }
} //class FormListaMonedas

