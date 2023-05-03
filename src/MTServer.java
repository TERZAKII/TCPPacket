import java.net.*;
import java.io.*;

public class MTServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(7777);
            System.out.println("Waiting for client request");
            Socket s = ss.accept();
            System.out.println("New client is pop up!");
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream ps = new PrintStream(s.getOutputStream());
            String message = "";
            while (!message.equals("exit")) {
                System.out.print("Server: ");
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                String response = input.readLine();
                ps.println(response);
                message = br.readLine();
                System.out.println("Client: " + message);
            }
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
