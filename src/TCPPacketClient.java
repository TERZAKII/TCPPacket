import java.io.*;
import java.net.*;

public final class TCPPacketClient {
    private String[] output;
    private InetAddress host;

    public TCPPacketClient(String[] output, InetAddress host) {
        this.output = output;
        this.host = host;
    }

    public void run() throws IOException {
        System.out.println("Connecting to server on port 1024...");

        Socket echoSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            echoSocket = new Socket(host, 1024);
            out = new ObjectOutputStream(echoSocket.getOutputStream());
            in = new ObjectInputStream(echoSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + host);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.print("Enter the data packet: ");
        int numberOf=0;
        while ((userInput = stdIn.readLine()) != null) {
            numberOf++;
            // Send packet to server
            Packet packet = new Packet(numberOf,userInput);
            out.writeObject(packet);
            out.flush();

            // Receive packet from server
            try {
                packet = (Packet) in.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(packet);
            System.out.print("Enter the data packet: ");
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }

    public static void main(String[] args) throws IOException {
        String[] output = new String[1];
        InetAddress host = InetAddress.getLocalHost();

        TCPPacketClient client = new TCPPacketClient(output, host);
        client.run();
    }
}
