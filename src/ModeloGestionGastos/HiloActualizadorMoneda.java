/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloGestionGastos;

import UIGestionGastos.FormListaMonedas;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

/**
 *
 * @author tAMs
 */
public class HiloActualizadorMoneda implements Runnable {

    private static final String URL = "http://download.finance.yahoo.com/d/quotes";
    private static final String ACTION1 = "s=";
    private static final String ACTION2 = "=X&f=sl1";
    private HttpConnection comunic;
    private Moneda monedaActualizada;
    private String monedaReferencia;
    FormListaMonedas formularioListaMonedas;
    int indice;

    public HiloActualizadorMoneda(Moneda mon, String monedaRef, FormListaMonedas formularioMonedas, int index) {
        monedaActualizada = mon;
        monedaReferencia = monedaRef;
        formularioListaMonedas = formularioMonedas;
        indice = index;
    }

    public void run() {

        InputStream is = null;
        String url = URL + '?' + ACTION1 + monedaActualizada.getNombreReducido() + monedaReferencia + ACTION2;

        StringItem item = new StringItem(null,"Loading...");
        item.setLayout(Item.LAYOUT_NEWLINE_AFTER);
        formularioListaMonedas.set(indice, item);

        try {
            comunic = (HttpConnection) Connector.open(url);
            comunic.setRequestMethod(HttpConnection.GET);
            // información de cabecera: opcional para GET
            comunic.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.0");
            comunic.setRequestProperty("Content-Language", "es-ES");
            is = comunic.openInputStream();
            procesarRespuesta(comunic, is);
            
            StringItem itemActualizado = new StringItem(null, monedaActualizada.toString());
            itemActualizado.setLayout(Item.LAYOUT_NEWLINE_AFTER);
            formularioListaMonedas.set(indice, itemActualizado);

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
