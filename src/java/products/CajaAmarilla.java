package products;

import fins.Client;
import fins.HeaderFINS;
import fins.Transmitter;
import org.avineas.fins.Address;
import org.avineas.fins.payload.Command;
import org.avineas.fins.payload.Response;
import stations.Has203;
import stations.Has205;
import stations.Has207;
import utils.Convertor;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.util.concurrent.TimeUnit;

/**
 * Created by juan
 * on 29/10/15.
 */
public class CajaAmarilla {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static String codigoDeBarraEnHex;
    private static String codigoDeBarraEnDec;
    private static String altura;

    public static void producir(int peso, Date fechaSalida, int despacho) throws Exception {

        //Estacion HAS-203, da la caja amarilla
        Address address = new Address(1, 3, 0);
        final Client client = new Client(new Transmitter(), address);
        final String headerFINS = HeaderFINS.getHeaderFINS(address, true);

        //seteo de buffer para la cantidad de gramos
        String data = peso == 15 ? Has203.provicionar15AmarilloBuffer1 : peso == 30 ? Has203.provicionar30AmarilloBuffer1 : Has203.provicionar45AmarilloBuffer1;
        sendData(client, headerFINS, data);

        //poner busy en 1 para mandar a producir
        sendData(client, headerFINS, Has203.Busy);

        //lectura y guardado de codigo de barra
        Runnable runnable = new Runnable() {
            @Override
            public void run(){
                try {
                    final Response response = sendData(client, headerFINS, Has203.leerCB);
                    final String cbFull = Convertor.byteArrayToHexString(response.getBytes());
                    codigoDeBarraEnHex = cbFull.substring(28,32);
                    codigoDeBarraEnDec = Convertor.hexTodecimal(codigoDeBarraEnHex);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        scheduler.schedule(runnable, 20, TimeUnit.SECONDS);

        //Estacion HAS-205, control estadistico
        address = new Address(1, 5, 0);
        final Client client1 = new Client(new Transmitter(), address);
        final String headerFINS1 = HeaderFINS.getHeaderFINS(address, true);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 7; i++) {
                    String data = "";
                    switch (i) {
                        case 0:
                            data = Has205.bufferBCR1(codigoDeBarraEnDec);
                            break;
                        case 1:
                            data = Has205.busy;
                            break;
                        case 2:
                            data = Has205.bCRAllow(codigoDeBarraEnDec, false);
                            break;
                        case 3:
                            data = Has205.busyAllow;
                            break;
                        case 4:
                            data = Has205.bCRAllow(codigoDeBarraEnDec, true);
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
                    Response response = null;
                    try {
                        response = sendData(client1, headerFINS1, data);
                        if (i == 6) System.out.println(response.getBytes().toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i == 5) {
                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        scheduler.schedule(runnable1, 0, TimeUnit.SECONDS);


        /*Address address4 = new Address(1, 7, 0);
        final Client client4 = new Client(new Transmitter(), address4);
        final String headerFINS4 = HeaderFINS.getHeaderFINS(address4, true);

        String data4 = Has207.bufferBCR1("4002");
        byte[] bytes4 = Convertor.toByteArray(headerFINS4 + data4);
        Command command4 = new Command(bytes4);
        client.handleCommand(command4);

        String data = Has207.busy;
        byte[] bytes = Convertor.toByteArray(headerFINS4 + data);
        Command command = new Command(bytes);
        client.handleCommand(command);

        data = Has207.bCRAllow("4002", false);
        bytes = toByteArray(headerFINS + data);
        command = new Command(bytes);
        client.handleCommand(command);

        data = Has207.busyAllow;
        bytes = toByteArray(headerFINS + data);
        command = new Command(bytes);
        client.handleCommand(command);

        data = Has207.bCRAllow("4002", true);
        bytes = toByteArray(headerFINS + data);
        command = new Command(bytes);
        client.handleCommand(command);

        data = Has207.busyAllow;
        bytes = toByteArray(headerFINS + data);
        command = new Command(bytes);
        client.handleCommand(command);*/
    }

    private static Response sendData(Client client, String fins, String data) throws Exception {
        final byte[] bytes = Convertor.toByteArray(fins + data);
        final Command command = new Command(bytes);
        return client.handleCommand(command);
    }
}
