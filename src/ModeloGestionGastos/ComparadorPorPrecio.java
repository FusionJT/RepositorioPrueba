package ModeloGestionGastos;

import UIGestionGastos.FormListaMonedas;
import java.io.IOException;
import javax.microedition.rms.RecordComparator;

/**
 * Comparador de jugadores por altura. Puede indicarse si el orden es
 * de menor a mayor altura (masBajoPrimero = true) o viceversa.
 *
 * @author jala
 */
public class ComparadorPorPrecio implements RecordComparator {

    private boolean masBajoUltimo;
    private FormListaMonedas formularioListaMonedas;

    public ComparadorPorPrecio(boolean masBajoUltimo) {
        this.masBajoUltimo = masBajoUltimo;
    }

    public int compare(byte[] rec1, byte[] rec2) {
        int result;

        try {
            Gasto g1 = new Gasto(rec1);
            Gasto g2 = new Gasto(rec2);

            double precio1 = g1.getCantidad()*g1.getMoneda().getRelacion();
            double precio2 = g2.getCantidad()*g2.getMoneda().getRelacion();

            if (precio1 < precio2) {
                result = masBajoUltimo ? FOLLOWS : PRECEDES;
            } else if (g1.getCantidad() > g2.getCantidad()) {
                result = masBajoUltimo ? PRECEDES : FOLLOWS;
            } else {
                result = EQUIVALENT;
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = EQUIVALENT;
        }

        return result;
    }
}
