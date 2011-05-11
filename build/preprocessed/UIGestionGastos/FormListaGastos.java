/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGestionGastos;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import ControladorGestionGastos.GestionGastos;
import ModeloGestionGastos.CustomItemGestionGastos;
import ModeloGestionGastos.Gasto;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author tAMs
 */
public class FormListaGastos extends Form {

    private Gasto[] gastos;
    public ChoiceGroup listaGastos;
    private CustomItemGestionGastos customItem;
    private Canvas itemCanvas;
    private GestionGastos midlet;

    public FormListaGastos(Gasto[] gasts, GestionGastos mid) {
        super("Gestion de Gastos");
        midlet = mid;
        itemCanvas = new Canvas() {

            protected void paint(Graphics g) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        setGastos(gasts);
        render();
    } //Creation Method

    private void render() {

        if (gastos != null) {
            midlet.setNumeroRecordsDisponibles();
            if (midlet.getNumeroRecordsDisponibles() != 0) {
                listaGastos = new ChoiceGroup("Lista de Gastos", ChoiceGroup.POPUP);
                for (int i = 0; i < gastos.length; i++) {
                    // Usamos nuestro Custom Item
                    append(new CustomItemGestionGastos(gastos[i], itemCanvas));
                    listaGastos.append(gastos[i].toString(), null);
                } //for
                append(listaGastos);
            }else{
                append("Sin Gastos Pendientes");
            }
        } //if
    } //render

    public void update(Gasto[] gasts) {
        setGastos(gasts);
        deleteAll();
        render();
    } //update

    public Gasto[] getGastos() {
        return gastos;
    } //setGastos

    public void setGastos(Gasto[] gasts) {
        gastos = gasts;
    } //setGastos

    public int getIndexSelected() {
        return listaGastos.getSelectedIndex();
    }

    public Gasto getGastoSelected() {
        int index = 0;
        index = listaGastos.getSelectedIndex();
        return gastos[index];
    }
} //class FormListaGastos

