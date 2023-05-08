import java.io.*;
import java.net.*;

public final class TCPPacketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(0);
        try {
            serverSocket = new ServerSocket(1024);
        } catch (IOException e) {
            System.err.println("Could not working on port: 1024");
            System.exit(1);
        }
        Socket clientSocket = new Socket();
        try {
            System.out.println("Opening port...");
            clientSocket = serverSocket.accept();
            System.out.println("New client is connected!");
        } catch (IOException e) {
            System.err.println("Failed to connect to client");
            System.exit(1);
        }
        System.out.println();
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        Packet receivedPacket = null;
        boolean have = true;
        while (have) {
            try {
                receivedPacket = (Packet) in.readObject();
                if(receivedPacket.toString().equals("close") || receivedPacket.toString().equals("CLOSE")){
                    System.out.println("Closing Server ... ");
                    have = false;
                    break;
                }
                else{
                    String letter = receivedPacket.upper_case(receivedPacket);
                    System.out.println("Receiving From Clients Packet's serialNo#" + receivedPacket.getSerialNo() +" and packet's Data is " + letter);
                    Packet responsePacket = new Packet(receivedPacket.getSerialNo(),"FROM SERVER: Packet SerialNo#" + receivedPacket.getSerialNo() + " is received");
                    out.writeObject(responsePacket);
                    out.flush();
                }

            } catch (ClassNotFoundException e) {
                System.err.println("Received object is not of type Packet");
                have = false;
                continue;

            }
            catch (SocketException e){
                System.out.println("Connection closed to Client");
                have = false;
            }

            
        }
    }
}
