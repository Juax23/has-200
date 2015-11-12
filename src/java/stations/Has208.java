package stations;

import utils.Convertor;

/**
 * Created by juan
 * on 22/10/15.
 */
public class Has208 {

    //para sacar solo usar bufferBCR1 y buy
    //para guardar bufferBCR1, busy, bufferAllow0, busyAllow, bufferAllow1, busyAllow (para bufferAllow metodo bcrallow)

    private final static String buffer1 = "0102B1000B000002"; //check

    private final static String bufferAllow0 = "0102B1000D000002"; //check

    public final static String busyAllow = "01023100000B000101"; //check

    public final static String busy = "01023100000A000101"; //check

    public static String bufferBCR1(String codigoBarra, boolean guardar){
        String aux = guardar ? codigoBarra + "5": codigoBarra + "6";
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
