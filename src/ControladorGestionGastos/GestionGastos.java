/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorGestionGastos;

import ModeloGestionGastos.ComparadorPorFecha;
import ModeloGestionGastos.ComparadorPorPrecio;
import ModeloGestionGastos.Gasto;
import ModeloGestionGastos.HiloActualizadorGasto;
import ModeloGestionGastos.HiloActualizadorMoneda;
import ModeloGestionGastos.HiloNuevaMoneda;
import ModeloGestionGastos.Moneda;
import UIGestionGastos.FormAnadirGasto;
import UIGestionGastos.FormBorrarGastos;
import UIGestionGastos.FormEditarGasto;
import UIGestionGastos.FormListaGastos;
import UIGestionGastos.FormListaMonedas;
import UIGestionGastos.FormNuevaMoneda;
import UIGestionGastos.SplashScreen;
import UIGestionGastos.FormVerTotal;
import java.io.IOException;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

/**
 * @author tAMs
 */
public class GestionGastos extends MIDlet implements CommandListener {

    private static Moneda[] datosMoneda = {
        new Moneda("Euro", "EUR", 1),
        new Moneda("US Dollar", "USD", 0.708),
        new Moneda("British Pound Sterling", "GBP", 1.1387),
        new Moneda("Swiss Franc", "CHF", 0.771),
        new Moneda("Japanese Yen", "JPY", 0.00854),
        new Moneda("Australlian Dollar", "AUD", 0.731)
    }; //datosMoneda
    private static Gasto[] datosGastos = {
        new Gasto("29/11/1989", "Dia D", 20, datosMoneda[0]),
        new Gasto("06/09/1987", "Compra Comida", 19, datosMoneda[0]),
        new Gasto("23/10/2000", "Clinex", 3, datosMoneda[0]),
        new Gasto("12/03/2007", "Viaje", 35, datosMoneda[3]),
        new Gasto("09/03/2011", "Balneario", 78, datosMoneda[2]),
        new Gasto("11/04/2011", "Regalo", 13, datosMoneda[5])
    };
    private Command botonAñadirGasto, botonEditarGasto, botonRanking, botonVerMonedas, botonBorrarTodo, botonVerTotal;
    private Command botonSalir, botonAceptar, botonVolver, botonActualizar, botonBorrarGasto, botonOrdenarCantidad, botonNuevaMoneda;
    private Command botonConfirmar, botonCancelar;
    private Gasto[] gastos;
    private Moneda[] monedas;
    private boolean recordBorrado = false;
    private int numeroGastosBorrados = 0;
    private int numeroRecordsDisponibles;
    Canvas itemCanvas;
    public static final String RS_NAME_MONEDAS = "Monedas";
    public static final String RS_NAME_GASTOS = "Gastos";
    private FormAnadirGasto formularioAñadirGasto;
    private FormEditarGasto formularioEditarGasto;
    private FormListaGastos formularioListaGastos;
    private FormListaMonedas formularioListaMonedas;
    private SplashScreen formularioSplashScreen;
    private FormBorrarGastos formularioBorrarGastos;
    private FormVerTotal formularioVerTotal;
    private FormNuevaMoneda formularioNuevaMoneda;
    private Display pantalla;

    // Método Constructor
    public GestionGastos() {
        try {
            inicializarDatos();
            leerDatosGastos(new ComparadorPorFecha(true), null);
            leerDatosMonedas(null, null);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        pantalla = Display.getDisplay(this);
        crearFormularios();
        crearBotones();
        crearPantallaPrincipal();
        formularioListaMonedas.setMonedaReferencia("getMonedas()[0]");
        formularioListaGastos.setCommandListener(this);
        pantalla.setCurrent(formularioListaGastos);

    }

    public void startApp() {
        pantalla.setCurrent(formularioListaGastos);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean uncond) {
    }

    public void commandAction(Command c, Displayable d) {

        // Diferenciamos entre el comando y el display para mostrar los datos

        if (d == formularioListaGastos) {
            if (c == botonSalir) {

                destroyApp(false);
                notifyPaused();

            } else if (c == botonAñadirGasto) {

                formularioAñadirGasto = new FormAnadirGasto(datosMoneda);
                formularioAñadirGasto.addCommand(botonVolver);
                formularioAñadirGasto.addCommand(botonAceptar);
                formularioAñadirGasto.addCommand(botonNuevaMoneda);
                formularioAñadirGasto.setCommandListener(this);
                pantalla.setCurrent(formularioAñadirGasto);

            } else if (c == botonEditarGasto) {

                formularioEditarGasto = new FormEditarGasto(datosMoneda, formularioListaGastos.getGastoSelected());
                formularioEditarGasto.addCommand(botonVolver);
                formularioEditarGasto.addCommand(botonBorrarGasto);
                formularioEditarGasto.addCommand(botonAceptar);
                formularioEditarGasto.setCommandListener(this);
                pantalla.setCurrent(formularioEditarGasto);

            } else if (c == botonRanking) {

                try {
                    // Ordenar los gastos de mayor a menor
                    leerDatosGastos(new ComparadorPorPrecio(true), null);
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                formularioListaGastos.update(gastos);
                pantalla.setCurrent(formularioListaGastos);

            } else if (c == botonVerMonedas) {

                formularioListaMonedas.addCommand(botonVolver);
                formularioListaMonedas.addCommand(botonActualizar);
                formularioListaMonedas.addCommand(botonNuevaMoneda);
                formularioListaMonedas.setCommandListener(this);
                pantalla.setCurrent(formularioListaMonedas);

            } else if (c == botonBorrarTodo) {

                formularioBorrarGastos = new FormBorrarGastos();
                formularioBorrarGastos.addCommand(botonConfirmar);
                formularioBorrarGastos.addCommand(botonCancelar);
                formularioBorrarGastos.setCommandListener(this);
                pantalla.setCurrent(formularioBorrarGastos);

            } else if (c == botonVerTotal) {
                formularioVerTotal = new FormVerTotal(gastos, monedas, formularioListaMonedas);
                formularioVerTotal.setCommandListener(this);
                pantalla.setCurrent(formularioVerTotal.getAlert());
            }
        }

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (d == formularioAñadirGasto) {
            if (c == botonAceptar) {
                recordBorrado = false;
                if (formularioAñadirGasto.isCompleted()) {
                    //Añadimos Gasto
                    Gasto nuevoGasto = new Gasto(formularioAñadirGasto.getFecha(), formularioAñadirGasto.getCategoria(), formularioAñadirGasto.getCantidad(), monedas[formularioAñadirGasto.getMonedaIndex()]);
                    RecordStore rs = null;
                    try {
                        rs = RecordStore.openRecordStore(RS_NAME_GASTOS, true);
                        byte[] data = nuevoGasto.toByteArray();
                        rs.addRecord(data, 0, data.length);
                        System.out.println("Nuevo gasto introducido");

                        leerDatosGastos(new ComparadorPorFecha(true), null);
                        formularioListaGastos.update(gastos);
                        pantalla.setCurrent(formularioListaGastos);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            rs.closeRecordStore();
                        } catch (RecordStoreException ex) {
                            ex.printStackTrace();
                        }
                    } //try

                } else {
                    Alert ventanaAlerta = new Alert("ERROR", "Rellene todos los campos", null, AlertType.ERROR);
                    ventanaAlerta.setTimeout(Alert.FOREVER);
                    pantalla.setCurrent(ventanaAlerta);
                }
            } // aceptar
            if (c == botonVolver) {
                pantalla.setCurrent(formularioListaGastos);
            } // volver

            if (c == botonNuevaMoneda) {
                // Crear una nueva moneda --> uso de comunicacion
                formularioNuevaMoneda = new FormNuevaMoneda();
                formularioNuevaMoneda.addCommand(botonVolver);
                formularioNuevaMoneda.addCommand(botonAceptar);
                formularioNuevaMoneda.setCommandListener(this);
                pantalla.setCurrent(formularioNuevaMoneda);
            }

        } // añadir gasto

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (d == formularioListaMonedas) {
            if (c == botonActualizar) {

                // Actualizamos monedas
                for (int i = 0; i < monedas.length; i++) {
                    Runnable r = new HiloActualizadorMoneda(monedas[i], formularioListaMonedas.getMonedaReferencia(),
                            formularioListaMonedas, i);
                    Thread rThread = new Thread(r);
                    rThread.start();
                }

                for (int i = 0; i < gastos.length; i++) {
                    Runnable t = new HiloActualizadorGasto(formularioListaGastos, formularioListaMonedas, i, this);
                    Thread tThread = new Thread(t);
                    tThread.start();
                }

                pantalla.setCurrent(formularioListaMonedas);

            }
            if (c == botonVolver) {
                pantalla.setCurrent(formularioListaGastos);
            }
            if (c == botonNuevaMoneda) {
                // Crear una nueva moneda --> uso de comunicacion
                formularioNuevaMoneda = new FormNuevaMoneda();
                formularioNuevaMoneda.addCommand(botonVolver);
                formularioNuevaMoneda.addCommand(botonAceptar);
                formularioNuevaMoneda.setCommandListener(this);
                pantalla.setCurrent(formularioNuevaMoneda);
            }
        }

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (d == formularioEditarGasto) {
            if (c == botonBorrarGasto) {
                numeroGastosBorrados++;
                RecordStore rs = null;
                try {
                    setNumeroRecordsDisponibles();
                    if (getNumeroRecordsDisponibles() == 1) {
                        rs.deleteRecordStore(RS_NAME_GASTOS);
                        formularioListaGastos.deleteAll();
                        formularioListaGastos.append("Sin Gastos Pendientes");
                        recordBorrado = true;
                    } else {
                        rs = RecordStore.openRecordStore(RS_NAME_GASTOS, true);
                        int indiceBorrar = getRecordIndex(formularioListaGastos.getGastoSelected());
                        System.out.println("Borramos el indice --> " + indiceBorrar);
                        rs.deleteRecord(indiceBorrar);
                        rs.closeRecordStore();
                    }
                    System.out.println("Gasto seleccionado eliminado");
                    System.out.println("Gastos borrados acumulados : " + numeroGastosBorrados);
                    System.out.println("Total de gastos " + datosGastos.length);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try {
                    //try
                    leerDatosGastos(new ComparadorPorFecha(true), null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                formularioListaGastos.update(gastos);
                pantalla.setCurrent(formularioListaGastos);

            }

            if (c == botonAceptar) {
                System.out.println("Campo categoria : " + formularioEditarGasto.getCategoria());
                Gasto nuevoGasto = new Gasto(formularioEditarGasto.getFecha(), formularioEditarGasto.getCategoria(),
                        formularioEditarGasto.getCantidad(), monedas[formularioEditarGasto.getMonedaIndex()]);
                RecordStore rs = null;
                try {
                    rs = RecordStore.openRecordStore(RS_NAME_GASTOS, true);
                    byte[] data = nuevoGasto.toByteArray();
                    int indiceEditar = getRecordIndex(formularioListaGastos.getGastoSelected());
                    rs.setRecord(indiceEditar, data, 0, data.length);
                    System.out.println("Gasto seleccionado modificado");

                    leerDatosGastos(new ComparadorPorFecha(true), null);

                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        rs.closeRecordStore();
                    } catch (RecordStoreException ex) {
                        ex.printStackTrace();
                    }
                } //try

                formularioListaGastos.update(gastos);
                pantalla.setCurrent(formularioListaGastos);

            }
            if (c == botonVolver) {
                pantalla.setCurrent(formularioListaGastos);
            }
        }

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (d == formularioBorrarGastos) {
            if (c == botonConfirmar) {
                // Borrar todos los gastos
                RecordStore rs = null;
                try {
                    if (recordBorrado == false) {
                        rs.deleteRecordStore(RS_NAME_GASTOS);
                        formularioListaGastos.deleteAll();
                        formularioListaGastos.append("Sin Gastos Pendientes");
                        System.out.println("Todos los gastos han sido borrados");
                        recordBorrado = true;
                        formularioListaGastos.update(gastos);
                        pantalla.setCurrent(formularioListaGastos);
                    } else {
                        Alert alertaGastos = new Alert("Error Borrar Gastos", "No existen gastos para borrar", null, AlertType.WARNING);
                        alertaGastos.setTimeout(Alert.FOREVER);
                        pantalla.setCurrent(alertaGastos, formularioListaGastos);
                        System.out.println("No hay gastos para borrar");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (c == botonCancelar) {
                pantalla.setCurrent(formularioListaGastos);
            }
        }

        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (d == formularioNuevaMoneda) {
            if (c == botonVolver) {
                pantalla.setCurrent(formularioListaGastos);
            }
            if (c == botonAceptar) {
                if (formularioNuevaMoneda.isCompleted()) {
                    // Confirmar con comunicacion a traves de internet

                    Moneda nuevaMoneda = new Moneda(formularioNuevaMoneda.getNombreLargo().getString(), formularioNuevaMoneda.getNuevaMoneda().getString(), 0);
                    Runnable r = new HiloNuevaMoneda(nuevaMoneda, formularioListaMonedas.getMonedaReferencia(), formularioListaMonedas, this);
                    Thread rThread = new Thread(r);
                    rThread.start();

                    System.out.println("Comprobamos relacion : " + nuevaMoneda.getRelacion());
                }
            }
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public int getNumeroRecordsDisponibles() {
        return numeroRecordsDisponibles;
    }

    public void setNumeroRecordsDisponibles() {
        RecordStore rs = null;
        try {
            rs = RecordStore.openRecordStore(RS_NAME_GASTOS, true);
            numeroRecordsDisponibles = rs.getNumRecords();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        try {
            rs.closeRecordStore();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    public int getRecordIndex(Gasto gast) throws RecordStoreException, IOException {

        RecordStore rs = null;
        int indexRecord = 0;
        boolean encontrado = false;

        try {
            rs = RecordStore.openRecordStore(RS_NAME_GASTOS, true);
            RecordEnumeration re = rs.enumerateRecords(null, null, true);
            while (re.hasNextElement() && encontrado == false) {
                Gasto gasto = new Gasto();
                gasto.fromByteArray(re.nextRecord());
                if (gasto.equals(gast)) {
                    encontrado = true;
                    if (re.hasNextElement()) {
                        indexRecord = re.nextRecordId();
                        indexRecord = re.previousRecordId();
                    } else if (re.hasPreviousElement()) {
                        indexRecord = re.previousRecordId();
                        indexRecord = re.nextRecordId();
                    } //if
                }
            } //while
        } finally {
            rs.closeRecordStore();
            if (encontrado == true) {
                return indexRecord;
            } else {
                return -1;
            } //if
        } //try
    } //getIndexRecor

    private void inicializarDatos() throws RecordStoreException, IOException {
        RecordStore rs = null;
        try {
            rs = RecordStore.openRecordStore(RS_NAME_MONEDAS, true);
            if (rs.getNumRecords() == 0) {
                for (int i = 0; i < datosMoneda.length; i++) {
                    byte[] data = datosMoneda[i].toByteArray();
                    rs.addRecord(data, 0, data.length);
                } //for
                System.out.println("Datos iniciales de monedas grabados en RS");
            } //if
        } finally {
            rs.closeRecordStore();
        } //try
    } //inicializarDatos

    private void leerDatosGastos(RecordComparator c, RecordFilter f) throws RecordStoreException, IOException {
        RecordStore rs = null;
        try {
            rs = RecordStore.openRecordStore(RS_NAME_GASTOS, true);
            int i = 0;
            RecordEnumeration re = rs.enumerateRecords(f, c, true);
            gastos = new Gasto[re.numRecords()];
            while (re.hasNextElement()) {
                gastos[i] = new Gasto();
                gastos[i].fromByteArray(re.nextRecord());
                i++;
            }
            System.out.println("Datos leidos de RS: " + gastos.length);
        } finally {
            rs.closeRecordStore();
        } //try
    } //leerDatosGastos

    public void leerDatosMonedas(RecordComparator c, RecordFilter f) throws RecordStoreException, IOException {
        RecordStore rs = null;
        try {
            rs = RecordStore.openRecordStore(RS_NAME_MONEDAS, true);
            int i = 0;
            RecordEnumeration re = rs.enumerateRecords(f, c, true);
            monedas = new Moneda[re.numRecords()];
            while (re.hasNextElement()) {
                monedas[i] = new Moneda();
                monedas[i].fromByteArray(re.nextRecord());
                i++;
            }
            System.out.println("Datos leidos de RS: " + monedas.length);
        } finally {
            rs.closeRecordStore();
        } //try
    } //leerDatosMonedas

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public Moneda[] getMonedas() {
        return monedas;
    }

    public Display getPantalla() {
        return pantalla;
    }

    public FormListaMonedas getFormListaMonedas() {
        return formularioListaMonedas;
    }

    public FormNuevaMoneda getFormNuevaMoneda() {
        return formularioNuevaMoneda;
    }

    public void crearFormularios() {
        formularioListaGastos = new FormListaGastos(gastos, this);
        formularioListaMonedas = new FormListaMonedas(datosMoneda);
        actualizarMonedas();
        formularioSplashScreen = new SplashScreen(this);
    }

    public void crearBotones() {
        botonSalir = new Command("Salir", Command.EXIT, 1);
        botonAñadirGasto = new Command("Añadir Gasto", Command.SCREEN, 1);
        botonEditarGasto = new Command("Editar Gasto", Command.SCREEN, 1);
        botonRanking = new Command("Ranking", Command.SCREEN, 1);
        botonVerMonedas = new Command("Monedas", Command.SCREEN, 1);
        botonBorrarTodo = new Command("Borrar Gastos", Command.SCREEN, 1);
        botonVerTotal = new Command("Ver Total", Command.SCREEN, 1);
        botonAceptar = new Command("Aceptar", Command.SCREEN, 1);
        botonVolver = new Command("Volver", Command.SCREEN, 1);
        botonNuevaMoneda = new Command("Nueva Moneda", Command.SCREEN, 1);
        botonConfirmar = new Command("Si", Command.OK, 1);
        botonCancelar = new Command("No", Command.CANCEL, 1);
        botonBorrarGasto = new Command("Borrar", Command.SCREEN, 1);
        botonNuevaMoneda = new Command("Nueva Moneda", Command.SCREEN, 1);
        botonActualizar = new Command("Actualizar", Command.SCREEN, 1);
    }

    public void crearPantallaPrincipal() {

        formularioListaGastos.addCommand(botonSalir);
        formularioListaGastos.addCommand(botonAñadirGasto);
        formularioListaGastos.addCommand(botonEditarGasto);
        formularioListaGastos.addCommand(botonRanking);
        formularioListaGastos.addCommand(botonVerMonedas);
        formularioListaGastos.addCommand(botonBorrarTodo);
        formularioListaGastos.addCommand(botonVerTotal);
    }

    public void actualizarMonedas() {
        for (int i = 0; i < monedas.length; i++) {
            Runnable r = new HiloActualizadorMoneda(monedas[i], formularioListaMonedas.getMonedaReferencia(),
                    formularioListaMonedas, i);
            Thread rThread = new Thread(r);
            rThread.start();
        }
    }
}


