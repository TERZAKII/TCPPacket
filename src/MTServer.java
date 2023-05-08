import java.net.*;
import java.io.*;

public class MTServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            System.out.println("Waiting for client request");
            Socket socket = serverSocket.accept();
            System.out.println("New client is pop up!");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            String message = "null";
            while (!message.equals("close")) {
                System.out.print("Server: ");
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                String response = input.readLine();
                printStream.println(response);
                message = bufferedReader.readLine();
                System.out.println("Clients: " + message);
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
