package stations;

import utils.Convertor;

/**
 * Created by juan
 * on 22/10/15.
 */
public class Has204 {

    //secuencia multicolor
    //bufferBCR1, busy, bufferAllow0, busyAllow, bufferAllow1, busyAllow

    public final static String cancelarProvision = "0102B1000D00000200000000";

    public final static String Busy = "01023100000A000101";

    public final static String provicionar15RojoBuffer1 = "0102B1000D00000200010000";
    public final static String provicionar30RojoBuffer1 = "0102B1000D00000200020000";
    public final static String provicionar45RojoBuffer1 = "0102B1000D00000200030000";

    public final static String leerCB = "0101B10007000002";

    private final static String buffer1 = "0102B1000D000002";

    private final static String bufferAllow0 = "0102B1000F000002";

    public final static String busyAllow = "01023100000B000101";

    public static String bufferBCR1(String codigoBarra, boolean gramos15){
        String aux = gramos15 ? codigoBarra + "1": codigoBarra + "2";
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
