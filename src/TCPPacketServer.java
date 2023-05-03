import java.io.*;
import java.net.*;

public final class TCPPacketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1024);
        } catch (IOException e) {
            System.err.println("Could not working on port: 1024");
            System.exit(1);
        }

        Socket clientSocket = null;
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

        Packet receivedPacket;

        while (true) {
            try {
                receivedPacket = (Packet) in.readObject();
                String letter = receivedPacket.upper_case(receivedPacket);
                System.out.println("Receiving From Clients Packet's serialNo#" + receivedPacket.getSerialNo() +" and packet's Data is " + letter);

                // Sending response packet
                Packet responsePacket = new Packet(receivedPacket.getSerialNo(),"FROM SERVER: Packet SerialNo#" + receivedPacket.getSerialNo() + " is received");
                out.writeObject(responsePacket);
                out.flush();
            } catch (ClassNotFoundException e) {
                System.err.println("Received object is not of type Packet");
                continue;
            }
        }
    }
}
