/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloGestionGastos;

import ControladorGestionGastos.GestionGastos;
import UIGestionGastos.FormListaMonedas;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.StringItem;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author tAMs
 */
public class HiloNuevaMoneda implements Runnable {

    private static final String URL = "http://download.finance.yahoo.com/d/quotes";
    private static final String ACTION1 = "s=";
    private static final String ACTION2 = "=X&f=sl1";
    private HttpConnection comunic;
    private Moneda monedaActualizada;
    private String monedaReferencia;
    public static final String RS_NAME_MONEDAS = "Monedas";
    FormListaMonedas formularioListaMonedas;
    int indice;
    Display pantalla;
    GestionGastos midlet;
    Image im;
    ImageItem im2;

    public HiloNuevaMoneda(Moneda mon, String monedaRef, FormListaMonedas formularioMonedas, GestionGastos mid) {
        monedaActualizada = mon;
        monedaReferencia = monedaRef;
        formularioListaMonedas = formularioMonedas;
        midlet = mid;
        try {
            im = Image.createImage(getClass().getResourceAsStream("../Images/buscaMoneda.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        im2 = new ImageItem(null, im, ImageItem.LAYOUT_CENTER, null);
    }

    public void run() {

        InputStream is = null;
        String url = URL + '?' + ACTION1 + monedaActualizada.getNombreReducido() + monedaReferencia + ACTION2;

        Form pantallaBuscaMoneda = new Form("Buscando moneda");

        StringItem item = new StringItem(null, "Buscando informacion de moneda...");
        pantallaBuscaMoneda.append(item);
        pantallaBuscaMoneda.append("\n");

        pantallaBuscaMoneda.append(im2);
        pantalla = Display.getDisplay(midlet);
        pantalla.setCurrent(pantallaBuscaMoneda);

        try {
            comunic = (HttpConnection) Connector.open(url);
            comunic.setRequestMethod(HttpConnection.GET);
            // información de cabecera: opcional para GET
            comunic.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.0");
            comunic.setRequestProperty("Content-Language", "es-ES");
            is = comunic.openInputStream();
            procesarRespuesta(comunic, is);

            if (monedaActualizada.getRelacion() == 0.0f) {
                Alert alertaErrorMoneda = new Alert("Error Moneda", "La moneda no existe", null, AlertType.WARNING);
                alertaErrorMoneda.setTimeout(Alert.FOREVER);
                pantalla.setCurrent(alertaErrorMoneda, formularioListaMonedas);
                System.out.println("La moneda no existe");
            } else {
                RecordStore rs = null;

                try {
                    rs = RecordStore.openRecordStore(RS_NAME_MONEDAS, true);
                    byte[] data = monedaActualizada.toByteArray();
                    rs.addRecord(data, 0, data.length);
                    System.out.println("Nueva moneda introducida");

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        rs.closeRecordStore();
                    } catch (RecordStoreException ex) {
                        ex.printStackTrace();
                    }
                } //try

                try {
                    //try
                    midlet.leerDatosMonedas(null, null);
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                String monedaIndiceChoice = midlet.getFormListaMonedas().getChoiceGroupMonedas().getString(midlet.getFormListaMonedas().getChoiceGroupMonedas().getSelectedIndex());
                midlet.getFormListaMonedas().getChoiceGroupMonedas().deleteAll();
                midlet.getFormListaMonedas().update(midlet.getMonedas());
                String monedaPrimera = midlet.getFormListaMonedas().getChoiceGroupMonedas().getString(0);
                midlet.getFormListaMonedas().setMonedaReferencia(monedaIndiceChoice);
                for (int j = 1; j < midlet.getMonedas().length; j++) {
                    if (midlet.getFormListaMonedas().getChoiceGroupMonedas().getString(j).equals(monedaIndiceChoice)) {
                        midlet.getFormListaMonedas().getChoiceGroupMonedas().set(j, monedaPrimera, null);
                    }
                }
                pantalla.setCurrent(formularioListaMonedas);
            }

//            StringItem itemActualizado = new StringItem(null, monedaActualizada.toString());
//            itemActualizado.setLayout(Item.LAYOUT_NEWLINE_AFTER);
//            formularioListaMonedas.append(itemActualizado);
//
//            pantalla.setCurrent(formularioListaMonedas);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (comunic != null) {
                try {
                    comunic.close();
                    is.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void procesarRespuesta(HttpConnection http, InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();

        if (comunic.getResponseCode() == HttpConnection.HTTP_OK) {
            // Obtenemos datos referentes a la cotización
            int length = (int) http.getLength();
            String str;
            if (length != -1) {
                byte datos[] = new byte[length];
                is.read(datos);
                str = new String(datos);
            } else { // longitud no disponible
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                int ch;
                while ((ch = is.read()) != -1) {
                    baos.write(ch);
                }

                str = new String(baos.toByteArray());
                baos.close();
            }
            sb.append(str);
            sb.append('\n');

        } else {
            String mensajeError = http.getResponseCode() + ": " + http.getResponseMessage();
            System.out.println("Mensaje Error : " + mensajeError);
        }

        // Actualizar la cadena de respuesta
        System.out.println(sb.toString());
        double valorRelacion = Double.valueOf(sb.toString().substring(11)).doubleValue();
        monedaActualizada.setRelacion(valorRelacion);
        if (monedaActualizada.getNombreReducido().equals(monedaReferencia)) {
            monedaActualizada.setRelacion(1);
        }
        System.out.println("Nombre moneda : " + monedaActualizada.getNombreReducido());
        System.out.println("Moneda Referencia : " + monedaReferencia);
    }
}
