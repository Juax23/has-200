package stations;

import utils.Convertor;

/**
 * Created by juan
 * on 22/10/15.
 */
public class Has205 {

    //codigo de barra + 1 en BCR1
    //poner en 1 el busy
    //codigo de barra + 0 en BCR_Allow
    //poner en 1 el busyAllow
    //codigo de barra + 1 en BCR_Allow
    //poner el 1 el busyAllow
    //leer la altura

    private final static String buffer1 = "0102B1000D000002";

    private final static String bufferAllow0 = "0102B1000F000002";

    public final static String busyAllow = "01023100000B000101";

    public final static String busy = "01023100000A000101";

    public final static String leerBusy = "01013100000A0001"; //not checked

    public final static String leerOutput = "0101B10009000002";

    public final static String leerAltura = "0101B1000B000002"; //los 2 ultimos pares + los 2 primeros

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
