import java.net.*;
import java.io.*;

public class Clients {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 7777);
            System.out.println("Request sent successfully");
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintStream ps = new PrintStream(s.getOutputStream());
            String message = "";
            while (!message.equals("exit")) {
                String response = br.readLine();
                System.out.println("Server: " + response);
                System.out.print("Client: ");
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                message = input.readLine();
                ps.println(message);
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
