import org.avineas.fins.Address;
import org.avineas.fins.payload.Command;
import org.avineas.fins.payload.Response;
import org.junit.Test;
import javax.xml.bind.DatatypeConverter;
import java.net.*;
import java.util.Enumeration;

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

        String data = "010270000302000103";
        final byte[] bytes = toByteArray(goldenEncabezadoFins + data);
        final Command command = new Command(bytes);
        try {
            final Response response = client.handleCommand(command);
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

