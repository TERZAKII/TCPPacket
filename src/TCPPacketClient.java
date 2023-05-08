import java.io.*;
import java.net.*;

public final class TCPPacketClient {
    private static String[] output;
    private InetAddress host;

    public TCPPacketClient(String[] output, InetAddress host) {
        this.output = output;
        this.host = host;
    }

    public void run() throws IOException {
        System.out.println("Connecting to server on port 1024...");
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            socket = new Socket(host, 1024);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Couldn't known this host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get connection to: " + host);
            System.exit(1);
        }
        BufferedReader bfReader = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.print("Enter the data packet: ");
        int numberOf=0;
        output = new String[1024];
        while ((userInput = bfReader.readLine()) != null) {
            if(userInput.equals("close") || userInput.equals("CLOSE")){
                System.out.println("Closing Client ... ");
                System.exit(1);
            }
            else{
                output[numberOf] = userInput;
                numberOf++;
                Packet packet = new Packet(numberOf,userInput);
                out.writeObject(packet);
                out.flush();
                try {
                    packet = (Packet) in.readObject();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(packet);
                System.out.print("Enter the data packet: ");
            }
        }
        out.close();
        in.close();
        bfReader.close();
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        output = new String[1];
        InetAddress host = InetAddress.getLocalHost();
        TCPPacketClient client = new TCPPacketClient(output, host);
        client.run();
    }
}
