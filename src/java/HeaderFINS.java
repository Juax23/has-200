import org.avineas.fins.Address;

import javax.xml.bind.DatatypeConverter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by juan
 * on 08/10/15.
 */
public class HeaderFINS {

    //cambiar a byte[]. Hay que cambiar el metodo toByteArray porque me pone signo negativo
    public static String getHeaderFINS(Address address, boolean respuesta) throws SocketException {
        String header = "";
        if (respuesta) header = header + "80";
        else header = header + "81";
        header = header + "000201";
        final String nodeNumber = "0" + Integer.toHexString(address.getNodeNumber());
        header = header + nodeNumber;
        header = header + "0001";
        final String[] myIp = getIp().split("\\.");
        header = header + Integer.toHexString(Integer.parseInt(myIp[3]));
        header = header + "0003";
        return header;
    }

    private static String getIp() throws SocketException {
        NetworkInterface ni = NetworkInterface.getByName("eth0");
        Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
        while(inetAddresses.hasMoreElements()) {
            InetAddress ia = inetAddresses.nextElement();
            if(!ia.isLinkLocalAddress()) {
                return ia.getHostAddress();
            }
        }
        return null;
    }

    private static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    private static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}
