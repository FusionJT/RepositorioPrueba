/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGestionGastos;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author tAMs
 */
public class FormNuevaMoneda extends Form {

    private TextField nuevaMoneda;
    private TextField nombreLargo;

    public FormNuevaMoneda() {
        super("Crear Nueva Moneda");
        nombreLargo = new TextField("Descripción larga", null, 100, TextField.ANY);
        nuevaMoneda = new TextField("Descripción abreviado", null, 3, TextField.ANY);
        render();
    } //Creation Method

    private void render() {
        append(nombreLargo);
        append(nuevaMoneda);
    } //render

    public void update() {
        deleteAll();
        render();
    } //update

    public TextField getNuevaMoneda() {
        return nuevaMoneda;
    } //getCampoCantidad

    public TextField getNombreLargo(){
        return nombreLargo;
    }

    public boolean isCompleted() {
        if ( (getNuevaMoneda().getString().equals("")) || (getNombreLargo().getString().equals("")) ) {
            return false;
        } else {
            return true;
        } //if
    } //isCompleted
} //class FormListaGastos

