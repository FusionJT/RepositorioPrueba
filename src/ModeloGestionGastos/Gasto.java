package ModeloGestionGastos;

import java.io.*;

/**
 *
 * @author tAMs
 */
public class Gasto {

    private Fecha fecha;
    private String categoria;
    private double cantidad;
    private Moneda moneda;

    public Gasto() {
    } //Creation Method

    public Gasto(String fech, String catg, double cant, Moneda mon) {
        setFechaString(fech);
        setCategoria(catg);
        setCantidad(cant);
        setMoneda(mon);
    } //Creation Method

    public Gasto(Fecha fecha, String catg, double cant, Moneda mon) {
        setFecha(fecha);
        setCategoria(catg);
        setCantidad(cant);
        setMoneda(mon);
    } //Creation Method

    public Gasto(byte[] data) throws IOException {
        fromByteArray(data);
    } //Creation Method

    public Fecha getFecha() {
        return fecha;
    } //getFecha

    public String getFechaString() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(getFecha().getDay()));
        sb.append("/");
        sb.append(String.valueOf(getFecha().getMonth()));
        sb.append("/");
        sb.append(String.valueOf(getFecha().getYear()));
        return sb.toString();
    } //getFechaString

    public void setFechaString(String fechaString) {
        int dia, mes, anyo;
        dia = Integer.valueOf(fechaString.substring(0, 2)).intValue();
        mes = Integer.valueOf(fechaString.substring(3, 5)).intValue();
        anyo = Integer.valueOf(fechaString.substring(6, 10)).intValue();
        Fecha fechaFecha = new Fecha(dia, mes, anyo);
        setFecha(fechaFecha);
    } //setFechaString

    public void setFecha(Fecha fech) {
        fecha = fech;
    } //setFecha

    public String getCategoria() {
        return categoria;
    } //getCategoria

    public void setCategoria(String catg) {
        categoria = catg;
    } //setCategoria

    public double getCantidad() {
        return cantidad;
    } //getCantidad

    public void setCantidad(double cant) {
        cantidad = cant;
    } //setCantidad

    public Moneda getMoneda() {
        return moneda;
    } //getMoneda

    public void setMoneda(Moneda mon) {
        moneda = mon;
    } //setMoneda

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Gasto other = (Gasto) obj;

        if ( (!getCategoria().equals(other.getCategoria())) ||
             (getCantidad() != other.getCantidad())         ||
             (!getMoneda().equals(other.getMoneda()))       ||
             (!getFecha().equals(other.getFecha())) ) {
            return false;
        } else {
            return true;
        }
    }

    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.getFecha().hashCode();
        hash = 59 * hash + (this.getCategoria() != null ? this.getCategoria().hashCode() : 0);
        hash = 59 * hash + (this.getMoneda() != null ? this.getMoneda().hashCode() : 0);
        return hash;
    } //hashCode

    /**
     * Lee los datos de un jugador de un flujo de entrada de datos.
     *
     * @param dis el flujo de entrada de datos
     * @throws IOException
     */
    public void fromDataStream(DataInputStream dis) throws IOException {
        Moneda mon;
        Fecha fech;
        fech = new Fecha(dis.readInt(), dis.readInt(), dis.readInt());
        setFecha(fech);
        setCategoria(dis.readUTF());
        setCantidad(dis.readDouble());
        mon = new Moneda(dis.readUTF(), dis.readUTF(), dis.readDouble());
        setMoneda(mon);
    } //fromDataStream

    /**
     * Escribe los datos de un jugador en un flujo de salida de datos.
     *
     * @param dos el flujo de salida de datos
     * @throws IOException
     */
    public void toDataStream(DataOutputStream dos) throws IOException {
        dos.writeInt(getFecha().getDay());
        dos.writeInt(getFecha().getMonth());
        dos.writeInt(getFecha().getYear());
        dos.writeUTF(getCategoria());
        dos.writeDouble(getCantidad());
        dos.writeUTF(getMoneda().getNombre());
        dos.writeUTF(getMoneda().getNombreReducido());
        dos.writeDouble(getMoneda().getRelacion());

    } //toDataStream

    /**
     * Lee los datos de un jugador de un array de bytes.
     *
     * @param data
     * @throws IOException
     */
    public void fromByteArray(byte[] data) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        DataInputStream din = new DataInputStream(bin);
        fromDataStream(din);
        din.close();
    } //fromByteArray

    /**
     * Serializa los datos del jugador a un array de bytes.
     *
     * @return
     * @throws IOException
     */
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        toDataStream(dout);
        dout.close();
        return bout.toByteArray();
    } //toByteArray

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getFecha().getDay());
        sb.append("/");
        sb.append(getFecha().getMonth());
        sb.append("/");
        sb.append(getFecha().getYear());
        sb.append(" (");
        sb.append(getCategoria());
        sb.append("): ");
        sb.append(getCantidad());
        sb.append(" ");
        sb.append(getMoneda().getNombreReducido());
        return sb.toString();
    } //toString
} //class Gasto

