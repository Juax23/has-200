import fins.Client;
import fins.HeaderFINS;
import fins.Transmitter;
import org.avineas.fins.Address;
import org.avineas.fins.payload.Command;
import org.avineas.fins.payload.Response;
import org.junit.Test;
import stations.Has205;
import stations.Has207;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by juan
 * on 29/10/15.
 */
public class TestHas205 {

    @Test
    public void run() throws Exception {
        Address address = new Address(1, 5, 0);
        final Client client = new Client(new Transmitter(), address);
        final String headerFINS = HeaderFINS.getHeaderFINS(address, true);

        for (int i = 0; i < 7; i++) {
            String data = "";
            switch (i){
                case 0:
                    data = Has205.bufferBCR1("4002");
                    break;
                case 1:
                    data = Has205.busy;
                    break;
                case 2:
                    data = Has205.bCRAllow("4002", false);
                    break;
                case 3:
                    data = Has205.busyAllow;
                    break;
                case 4:
                    data = Has205.bCRAllow("4002", true);
                    break;
                case 5:
                    data = Has205.busyAllow;
                    break;
                case 6:
                    data = Has205.leerAltura;
                    break;
                default:
                    break;
            }
            byte[] bytes = toByteArray(headerFINS + data);
            Command command = new Command(bytes);
            Response response = client.handleCommand(command);
            printByteArray(response.getBytes());

            Thread.sleep(2000);
            if (i == 5) Thread.sleep(30000);
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
