import fins.Client;
import fins.HeaderFINS;
import fins.Transmitter;
import org.avineas.fins.Address;
import org.avineas.fins.payload.Command;
import org.avineas.fins.payload.Response;
import org.junit.Test;
import stations.Has204;

import javax.xml.bind.DatatypeConverter;
import java.net.SocketException;

/**
 * Created by juan
 * on 22/10/15.
 */
public class TestHas204 {

    @Test
    public void run() throws SocketException {
        Address address = new Address(1, 4, 0);
        final Client client = new Client(new Transmitter(), address);
        final String headerFINS = HeaderFINS.getHeaderFINS(address, true);

        String data2 = Has204.provicionar15RojoBuffer1;
        final byte[] bytes2 = toByteArray(headerFINS + data2);
        final Command command2 = new Command(bytes2);
        try {
            final Response response = client.handleCommand(command2);
            printByteArray(response.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String data1 = Has204.Busy;
        final byte[] bytes1 = toByteArray(headerFINS + data1);
        final Command command1 = new Command(bytes1);
        try {
            final Response response = client.handleCommand(command1);
            printByteArray(response.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String data3 = Has204.leerCB;
        final byte[] bytes3 = toByteArray(headerFINS + data3);
        final Command command3 = new Command(bytes3);
        try {
            final Response response = client.handleCommand(command3);
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

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

}
