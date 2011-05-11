/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGestionGastos;

import java.io.IOException;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;

/**
 *
 * @author tAMs
 */
public class FormBorrarGastos extends Form {

    Image im;
    ImageItem im2;

    public FormBorrarGastos() {
        super("Borrar Gastos");
        try {
            im = Image.createImage(getClass().getResourceAsStream("../Images/eraser2.png"));
            im2 = new ImageItem(null, im, ImageItem.LAYOUT_CENTER,null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        append("\n");
        append("                   ¡ATENCION! ");
        append("     ¿Desea borrar todos los gastos?");
        append("\n");
        append(im2);
    } //Creation Method

    protected void paint(Graphics g) {
        g.setColor(0, 0, 0);
        g.drawImage(im, 0, 0, Graphics.HCENTER | Graphics.VCENTER);
    }
} //class FormListaMonedas

