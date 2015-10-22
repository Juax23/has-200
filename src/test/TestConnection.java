import org.avineas.fins.Address;
import org.avineas.fins.payload.Command;
import org.avineas.fins.payload.Response;
import org.junit.Test;
import stations.Has201;

import javax.xml.bind.DatatypeConverter;
import java.net.*;

/**
 * Created by juan
 * on 23/09/15.
 */

public class TestConnection {

    @Test
    public void run() throws UnknownHostException, SocketException {
        Address address = new Address(1, 1, 0);
        final Client client = new Client(new Transmitter(), address);

        final String headerFINS = HeaderFINS.getHeaderFINS(address, true);
        String goldenEncabezadoFins = "80000201010001650003";
        assert goldenEncabezadoFins.equals(headerFINS);

        /*String data = station.Has201.bufferEn0;
        final byte[] bytes = toByteArray(goldenEncabezadoFins + data);
        final Command command = new Command(bytes);
        try {
            final Response response = client.handleCommand(command);
            printByteArray(response.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String data1 = station.Has201.bufferEn1;
        final byte[] bytes1 = toByteArray(goldenEncabezadoFins + data1);
        final Command command1 = new Command(bytes1);
        try {
            final Response response = client.handleCommand(command1);
            printByteArray(response.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        String data3 = Has201.provisionarCaja;
        final byte[] bytes3 = toByteArray(goldenEncabezadoFins + data3);
        final Command command3 = new Command(bytes3);
        try {
            final Response response = client.handleCommand(command3);
            printByteArray(response.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String data2 = Has201.leerCB;
        final byte[] bytes2 = toByteArray(goldenEncabezadoFins + data2);
        final Command command2 = new Command(bytes2);
        try {
            final Response response = client.handleCommand(command2);
            printByteArray(response.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void printByteArray(byte[] byteArray){
        for (final byte aux : byteArray) {
            System.out.print(aux);
            System.out.print(" ");
        }
        System.out.println();
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}

