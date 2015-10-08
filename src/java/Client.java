import org.avineas.fins.*;
import org.avineas.fins.Transmitter;
import org.avineas.fins.payload.Command;
import org.avineas.fins.payload.Response;


/**
 * Created by juan
 * on 23/09/15.
 */
public class Client implements Unit{

    private Transmitter myTransmitter;
    private Address myAddress;

    public Client(Transmitter transmitter, Address address){
        myTransmitter = transmitter;
        myAddress = address;
    }

    public Client(Transmitter transmitter){
        myTransmitter = transmitter;
    }

    @Override
    public Response handleCommand(Command command) throws Exception {
        if (myTransmitter == null || myAddress == null) {
            throw new NullPointerException("Transmitter or Address are not defined or null");
        } else {
            return myTransmitter.sendPacket(myAddress, command);
        }
    }

    @Override
    public void setTransmitter(Transmitter transmitter) {
        myTransmitter = transmitter;
    }

    public Address getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(Address myAddress) {
        this.myAddress = myAddress;
    }
}
