

package ModeloGestionGastos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author tAMs
 */
public class Moneda {
    private String nombre;
    private String nombre_reducido;
    private double relacion;

    public Moneda(String nom, String nomRed, double rel){
        setNombre(nom);
        setNombreReducido(nomRed);
        setRelacion(rel);

    } //Creation Method

    public Moneda(){

    } //Creation Method

    public String getNombre(){
        return nombre;
    } //getNombre

    public void setNombre(String nom){
        nombre = nom;
    } //setNombre

    public String getNombreReducido(){
        return nombre_reducido;
    } //getNombreReducido

    public void setNombreReducido(String nomRed){
        nombre_reducido = nomRed;
    } //setNombreReducido

    public double getRelacion(){
        return relacion;
    } //getRelacion
    
    public void setRelacion(double rel){
        relacion = rel;
    } //relacion

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } //if
        if (getClass() != obj.getClass()) {
            return false;
        } //if
        final Moneda other = (Moneda) obj;
        if (!this.getNombre().equals(other.getNombre())) {
            return false;
        } //if
        if (!this.getNombreReducido().equals(other.getNombreReducido())){
            return false;
        } //if
        if (this.getRelacion() != other.getRelacion()){
            return false;
        }
        return true;
    } //equals

    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.getNombreReducido().hashCode();
        hash = 59 * hash + (this.getNombre() != null ? this.getNombre().hashCode() : 0);
        return hash;
    }

    /**
     * Lee los datos de un jugador de un flujo de entrada de datos.
     *
     * @param dis el flujo de entrada de datos
     * @throws IOException
     */
    public void fromDataStream(DataInputStream dis) throws IOException {
        setNombre(dis.readUTF());
        setNombreReducido(dis.readUTF());
        setRelacion(dis.readDouble());
    } //fromDataStream

    /**
     * Escribe los datos de un jugador en un flujo de salida de datos.
     *
     * @param dos el flujo de salida de datos
     * @throws IOException
     */
    public void toDataStream(DataOutputStream dos) throws IOException {
        dos.writeUTF(getNombre());
        dos.writeUTF(getNombreReducido());
        dos.writeDouble(getRelacion());
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
        sb.append(getNombre());
        sb.append(": ");
        sb.append(getNombreReducido());
        sb.append(" ");
        sb.append(getRelacion());
        sb.append("\n");
        return sb.toString();
    } //toString

} //class Moneda
