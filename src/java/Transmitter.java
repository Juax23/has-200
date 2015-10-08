import org.avineas.fins.Address;
import org.avineas.fins.payload.Command;
import org.avineas.fins.payload.Response;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by juan
 * on 23/09/15.
 */
public class Transmitter implements org.avineas.fins.Transmitter {

    @Override
    public Response sendPacket(Address to, Command command) throws IOException{
        DatagramSocket clientSocket = new DatagramSocket(9601);
        InetAddress IPAddress = InetAddress.getByName("192.168.0.1");
        byte[] receiveData = new byte[1024];
        byte[] sendData = command.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9601);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.send(sendPacket);
        clientSocket.receive(receivePacket);
        clientSocket.close();
        return new Response(receivePacket.getData());
    }
}
