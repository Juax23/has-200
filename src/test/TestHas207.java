import fins.Client;
import fins.HeaderFINS;
import fins.Transmitter;
import org.avineas.fins.Address;
import org.avineas.fins.payload.Command;
import org.avineas.fins.payload.Response;
import org.junit.Test;
import stations.Has204;
import stations.Has207;

import javax.xml.bind.DatatypeConverter;
import java.net.SocketException;

/**
 * Created by juan
 * on 22/10/15.
 */
public class TestHas207 {

    @Test
    public void run() throws Exception {
        Address address = new Address(1, 7, 0);
        final Client client = new Client(new Transmitter(), address);
        final String headerFINS = HeaderFINS.getHeaderFINS(address, true);

        String data = Has207.bufferBCR1("1023");
        byte[] bytes = toByteArray(headerFINS + data);
        Command command = new Command(bytes);
        Response response = client.handleCommand(command);
        printByteArray(response.getBytes());

        data = Has207.busy;
        bytes = toByteArray(headerFINS + data);
        command = new Command(bytes);
        response = client.handleCommand(command);
        printByteArray(response.getBytes());

        data = Has207.bCRAllow("1023", false);
        bytes = toByteArray(headerFINS + data);
        command = new Command(bytes);
        response = client.handleCommand(command);
        printByteArray(response.getBytes());

        data = Has207.busyAllow;
        bytes = toByteArray(headerFINS + data);
        command = new Command(bytes);
        response = client.handleCommand(command);
        printByteArray(response.getBytes());

        data = Has207.bCRAllow("1023", true);
        bytes = toByteArray(headerFINS + data);
        command = new Command(bytes);
        response = client.handleCommand(command);
        printByteArray(response.getBytes());

        data = Has207.busyAllow;
        bytes = toByteArray(headerFINS + data);
        command = new Command(bytes);
        response = client.handleCommand(command);
        printByteArray(response.getBytes());
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
