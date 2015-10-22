package stations;

import fins.Client;
import fins.Transmitter;
import org.avineas.fins.payload.Response;
import utils.Convertor;

/**
 * Created by juan
 * on 22/10/15.
 */
public class Has207 {

    //buffer = cb * 10 + 1
    //busy = 1
    //bufferAllow = cb * 10
    //busyAllow = 1
    //bufferAllow = cb * 10 + 1
    //busyAllow = 1

    private final static String buffer1 = "0102B10017000002";

    private final static String bufferAllow0 = "0102B10019000002";

    public final static String busyAllow = "01023100000B000101";

    public final static String busy = "01023100000A000101";

    public final static String leerBusy = "01013100000A0001";

    public final static String leerCB = "0101B10007000002";

    public static String bufferBCR1(String codigoBarra){
        String aux = codigoBarra + "1";
        String toHex = Convertor.decToHex(Integer.parseInt(aux));
        if (toHex.length() % 2 != 0) toHex = "0" + toHex;
        String end = "0000";
        return buffer1 + toHex + end;
    }

    public static String bCRAllow(String codigoBarra, boolean bit){
        String aux;
        if (bit) aux = codigoBarra + "1";
        else aux = codigoBarra + "0";
        String toHex = Convertor.decToHex(Integer.parseInt(aux));
        if (toHex.length() % 2 != 0) toHex = "0" + toHex;
        String end = "0000";
        return bufferAllow0 + toHex + end;
    }

}
