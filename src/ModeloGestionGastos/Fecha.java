package ModeloGestionGastos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author tAMs
 */
public class Fecha {

    Date date;

    public Fecha() {
        date = new Date();
    } //Creation Method

    public Fecha(Date dat) {
        date = dat;
    } //Creation Method

    public Fecha(long dat) {
        date = new Date(dat);
    } //Creation Method

    public Fecha(int dia, int mes, int anyo) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.DAY_OF_MONTH, dia);
        calendar.set(calendar.MONTH, mes - 1);
        calendar.set(calendar.YEAR, anyo);
        date = calendar.getTime();
    } //Creation Method

    public Fecha(byte[] data) throws IOException {
        fromByteArray(data);
    } //Creation Method

    public void setDate(Date dat) {
        date = dat;
    } //setDate

    public Date getDate() {
        return date;
    } //getDate

    public void setDay(int dia) {
        if (getDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getDate());
            calendar.set(calendar.DAY_OF_MONTH, dia);
            setDate(calendar.getTime());
        } //if
    } //setDay

    public void setMonth(int mes) {
        if (getDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getDate());
            calendar.set(calendar.MONTH, mes - 1);
            setDate(calendar.getTime());
        } //if
    } //setMonth

    public void setYear(int anyo) {
        if (getDate() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getDate());
            calendar.set(calendar.YEAR, anyo);
            setDate(calendar.getTime());
        } //if
    } //setYear

    public int getDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        return calendar.get(calendar.DAY_OF_MONTH);
    } //getDay

    public int getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        return calendar.get(calendar.MONTH) + 1;
    } //getMonth

    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        return calendar.get(calendar.YEAR);
    } //getDay

    private void fromByteArray(byte[] data) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        DataInputStream din = new DataInputStream(bin);
        fromDataStream(din);
        din.close();
    } //fromByteArray

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        toDataStream(dout);
        dout.close();
        return bout.toByteArray();
    } //toByteArray

    public void fromDataStream(DataInputStream dis) throws IOException {
        Fecha fech;
        fech = new Fecha(dis.readInt(), dis.readInt(), dis.readInt());
        setDate(fech.getDate());
    } //fromDataStream

    public void toDataStream(DataOutputStream dos) throws IOException {
        dos.writeInt(getDay());
        dos.writeInt(getMonth());
        dos.writeInt(getYear());
    } //toDataStream

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(getDay()));
        sb.append("/");
        sb.append(String.valueOf(getMonth()));
        sb.append("/");
        sb.append(String.valueOf(getYear()));
        return sb.toString();
    } //toString

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fecha other = (Fecha) obj;

        if ( (this.getDay() != (other.getDay()))   ||
             (this.getMonth() != other.getMonth()) ||
             (this.getYear() != other.getYear()) ) {
            return false;
        } else {
            return true;
        }
    }
} //class Fecha

