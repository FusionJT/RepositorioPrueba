/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloGestionGastos;

import ControladorGestionGastos.GestionGastos;
import UIGestionGastos.FormListaGastos;
import UIGestionGastos.FormListaMonedas;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author tAMs
 */
public class HiloActualizadorGasto implements Runnable {

    FormListaGastos formularioListaGastos;
    FormListaMonedas formularioListaMonedas;
    int indice;
    Display pantalla;
    GestionGastos midlet;
    Image im;
    ImageItem im2;

    public HiloActualizadorGasto(FormListaGastos formularioGastos, FormListaMonedas formularioMonedas,
            int index, GestionGastos mid) {
        formularioListaGastos = formularioGastos;
        formularioListaMonedas = formularioMonedas;
        indice = index;
        midlet = mid;
        try {
            im = Image.createImage(getClass().getResourceAsStream("../Images/actualizaGasto.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        im2 = new ImageItem(null, im, ImageItem.LAYOUT_CENTER, null);
    }

    public void run() {

        InputStream is = null;

        Form pantallaActualizaGasto = new Form("Actualizando Gastos");

        StringItem item = new StringItem(null, "Actualizando Gastos...");
        pantallaActualizaGasto.append(item);
        pantallaActualizaGasto.append("\n");
        pantallaActualizaGasto.append(im2);
        pantalla = Display.getDisplay(midlet);
        pantalla.setCurrent(pantallaActualizaGasto);
        formularioListaGastos.getGastos()[indice].setMoneda(formularioListaMonedas.getMonedas()[indice]);
        System.out.println("Gasto " + indice + " actualizado");
        pantalla.setCurrent(formularioListaMonedas);
    }
}
