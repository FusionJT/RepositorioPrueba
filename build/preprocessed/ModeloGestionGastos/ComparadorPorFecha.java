package ModeloGestionGastos;

import java.io.IOException;
import javax.microedition.rms.RecordComparator;

/**
 * Comparador de jugadores por altura. Puede indicarse si el orden es
 * de menor a mayor altura (masBajoPrimero = true) o viceversa.
 *
 * @author jala
 */
public class ComparadorPorFecha implements RecordComparator {

    private boolean fechaUltimo;

    public ComparadorPorFecha(boolean masBajoUltimo) {
        this.fechaUltimo = masBajoUltimo;
    }

    public int compare(byte[] rec1, byte[] rec2) {
        int result;

        try {
            Gasto g1 = new Gasto(rec1);
            Gasto g2 = new Gasto(rec2);

            if (g1.getFecha().getYear() < g2.getFecha().getYear()) {
                result = fechaUltimo ? FOLLOWS : PRECEDES;
            } else if (g1.getFecha().getYear() > g2.getFecha().getYear()) {
                result = fechaUltimo ? PRECEDES : FOLLOWS;
            } else if (g1.getFecha().getMonth() < g2.getFecha().getMonth()) {
                result = fechaUltimo ? FOLLOWS : PRECEDES;
            } else if (g1.getFecha().getMonth() > g2.getFecha().getMonth()) {
                result = fechaUltimo ? PRECEDES : FOLLOWS;
            } else if (g1.getFecha().getDay() < g2.getFecha().getDay()) {
                result = fechaUltimo ? FOLLOWS : PRECEDES;
            } else if (g1.getFecha().getDay() > g2.getFecha().getDay()) {
                result = fechaUltimo ? PRECEDES : FOLLOWS;
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
