/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGestionGastos;

import ControladorGestionGastos.GestionGastos;
import javax.microedition.lcdui.*;

public class SplashScreen extends Canvas {

    private GestionGastos midlet;
    private Form formularioSplash;
    private Gauge cargaTiempo;
    private Image im;
    private ImageItem im2;
    private Display pantalla;
    private static final int ESPERA_CARGA = 100;

    public SplashScreen(GestionGastos mid) {

        String text = "    LOADING...";
        this.midlet = mid;
        pantalla = Display.getDisplay(mid);
        formularioSplash = new Form("Splash Screen");
        cargaTiempo = new Gauge("  ", false, 100, 0);

        try {

            im = Image.createImage(getClass().getResourceAsStream("../Images/cash.png"));
            im2 = new ImageItem(null, im, ImageItem.LAYOUT_CENTER, null);
            formularioSplash.append("\n");
            formularioSplash.append(im2);
            formularioSplash.append(text);
            formularioSplash.append(cargaTiempo);

            for (int i = 0; i < ESPERA_CARGA; i += 20) {
                try {
                    Thread.sleep(1000);
                    pantalla.setCurrent(formularioSplash);
                    cargaTiempo.setValue(i);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.out.println("Error al cargar archivo de imagen");
        }
    }

    public void paint(Graphics g) {
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(0, 0, 0);
        g.drawImage(im, 0, 0, Graphics.HCENTER | Graphics.VCENTER);
    }
}
