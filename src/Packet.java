import java.io.Serializable;

public class Packet implements Serializable {
//    private int serialNoClients=0;
//    private int serialNoServer=0;
    private int serialNo;
    private String data;

    public Packet(int serialNo, String data) {
        this.serialNo = serialNo;
        this.data = data;
    }
    public int getSerialNo() {
        return serialNo;
    }

    public String upper_case(Packet receivedPacket){
        String letter = String.valueOf(receivedPacket);
        letter = letter.toUpperCase();
        return letter;
    }

    public String getData() {
        return data;
    }

    public String toString() {
        return data;
    }
}
