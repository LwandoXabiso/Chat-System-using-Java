import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientSide {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Server IP Address: ");
        String serverIp = scanner.nextLine();

        System.out.print("Enter yyour display name: ");
        String clientName = scanner.nextLine();

        try (Socket socket = new Socket(serverIp, 2020);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintStream serverOutput  = new PrintStream(socket.getOutputStream());
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))){

            System.out.println("Connected to server. Start chatting....");

            String message;
            while (true) {
                System.out.println(serverInput.readLine());
                System.out.print("You: ");
                message = userInput.readLine();
                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
                serverOutput.println(clientName + ": " + message);
            }
            
        } catch (Exception e) {
          System.err.println("Eror connecting to server: ");
        }

        scanner.close();
    }
}