/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloGestionGastos;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author tAMs
 */
public class CustomItemGestionGastos extends CustomItem {

    private Gasto gastoItem;
    private Canvas canvas;

    public CustomItemGestionGastos(Gasto gasto, Canvas c) {
        super("");
        gastoItem = gasto;
        canvas = c;
    }

    public int getMinContentHeight() {
        return 17;
    }

    public int getMinContentWidth() {
        return canvas.getWidth();
    }

    public int getPrefContentHeight(int width) {
        return getMinContentHeight();
    }

    public int getPrefContentWidth(int height) {
        return getMinContentWidth();
    }

    public void paint(Graphics g, int w, int h) {

        String cadenaFormateada1, cadenaFormateada2 = "";
        Font fuente = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
        g.setFont(fuente);

        cadenaFormateada1 = String.valueOf(gastoItem.getCantidad()) + " " + gastoItem.getMoneda().getNombreReducido();
        cadenaFormateada2 = gastoItem.getFechaString() + " (" + gastoItem.getCategoria() + ")";

        Fecha fechaActual = new Fecha();
        int añoActual = fechaActual.getYear();
        int mesActual = fechaActual.getMonth();
        int diaActual = fechaActual.getDay();
        int diferenciaMeses = mesActual - gastoItem.getFecha().getMonth();

        if (gastoItem.getFecha().getYear() == añoActual) {

            if (diferenciaMeses == 0) {
                g.setColor(0, 0, 0);
                g.drawString(cadenaFormateada2, 0, 0, Graphics.TOP | Graphics.LEFT);
                g.drawString(cadenaFormateada1, getMinContentWidth() - 20, 0, Graphics.TOP | Graphics.RIGHT);
            }

            if (diferenciaMeses == 1) {
                int diasRestantes = 31 - gastoItem.getFecha().getDay();
                if (diasRestantes + diaActual >= 31) {
                    // Pintar fonde de azul
                    g.setColor(0, 200, 255);
                    g.fillRect(0, 0, getMinContentWidth(), getMinContentHeight());
                    g.setColor(0, 0, 0);
                    g.drawString(cadenaFormateada2, 0, 0, Graphics.TOP | Graphics.LEFT);
                    g.drawString(cadenaFormateada1, getMinContentWidth() - 20, 0, Graphics.TOP | Graphics.RIGHT);
                } else {
                    g.setColor(0, 0, 0);
                    g.drawString(cadenaFormateada2, 0, 0, Graphics.TOP | Graphics.LEFT);
                    g.drawString(cadenaFormateada1, getMinContentWidth() - 20, 0, Graphics.TOP | Graphics.RIGHT);
                }

            } else if (diferenciaMeses == 3) {

                int diasRestantes = 31 - gastoItem.getFecha().getDay();
                if (diasRestantes + diaActual >= 31) {
                    // Pintar fonde de rojo
                    g.setColor(255, 0, 0);
                    g.fillRect(0, 0, getMinContentWidth(), getMinContentHeight());
                    g.setColor(0, 0, 0);
                    g.drawString(cadenaFormateada2, 0, 0, Graphics.TOP | Graphics.LEFT);
                    g.drawString(cadenaFormateada1, getMinContentWidth() - 20, 0, Graphics.TOP | Graphics.RIGHT);
                } else {
                    // Pintar fonde de azul
                    g.setColor(0, 200, 255);
                    g.fillRect(0, 0, getMinContentWidth(), getMinContentHeight());
                    g.setColor(0, 0, 0);
                    g.drawString(cadenaFormateada2, 0, 0, Graphics.TOP | Graphics.LEFT);
                    g.drawString(cadenaFormateada1, getMinContentWidth() - 20, 0, Graphics.TOP | Graphics.RIGHT);
                }

            } else if (diferenciaMeses == 2) {
                // Pintar fonde de azul
                g.setColor(0, 200, 255);
                g.fillRect(0, 0, getMinContentWidth(), getMinContentHeight());
                g.setColor(0, 0, 0);
                g.drawString(cadenaFormateada2, 0, 0, Graphics.TOP | Graphics.LEFT);
                g.drawString(cadenaFormateada1, getMinContentWidth() - 20, 0, Graphics.TOP | Graphics.RIGHT);
            } else if ((mesActual - gastoItem.getFecha().getMonth()) > 3) {
                // Pintar fonde de rojo
                g.setColor(255, 0, 0);
                g.fillRect(0, 0, getMinContentWidth(), getMinContentHeight());
                g.setColor(0, 0, 0);
                g.drawString(cadenaFormateada2, 0, 0, Graphics.TOP | Graphics.LEFT);
                g.drawString(cadenaFormateada1, getMinContentWidth() - 20, 0, Graphics.TOP | Graphics.RIGHT);
            }
        } else if (gastoItem.getFecha().getYear() < añoActual) {
            g.setColor(255, 0, 0);
            g.fillRect(0, 0, getMinContentWidth(), getMinContentHeight());
            g.setColor(0, 0, 0);
            g.drawString(cadenaFormateada2, 0, 0, Graphics.TOP | Graphics.LEFT);
            g.drawString(cadenaFormateada1, getMinContentWidth() - 20, 0, Graphics.TOP | Graphics.RIGHT);
        } else {
            g.setColor(0, 0, 0);
            g.drawString(cadenaFormateada2, 0, 0, Graphics.TOP | Graphics.LEFT);
            g.drawString(cadenaFormateada1, getMinContentWidth() - 20, 0, Graphics.TOP | Graphics.RIGHT);
        }
    }
}
