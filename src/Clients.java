import java.net.*;
import java.io.*;

public class Clients {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 7777);
            System.out.println("Request sent successfully");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            String message = "null";
            while (!message.equals("close")) {
                String response = bufferedReader.readLine();
                System.out.println("Server: " + response);
                System.out.print("Clients: ");
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                message = input.readLine();
                printStream.println(message);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
