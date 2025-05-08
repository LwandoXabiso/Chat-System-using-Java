import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ServerSide {
    private static final int PORT = 2020;
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args){
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.print("Server started on port " + PORT + ". Waiting for client....");

            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connection: " + clientSocket.getInetAddress());

                // Craete new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (Exception e) {
            System.err.println("Error starting server: "  + e.getMessage());
        }
    }

    // Handles each client in a separate thread
    private static class ClientHandler extends Thread{
        private Socket socket;
        private BufferedReader input;
        private PrintStream output;
        private String clientName;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintStream(socket.getOutputStream());

                // Read client's name
                output.println("Enter your name");
                clientName = input.readLine();
                if(clientName == null || clientName.trim().isEmpty()){
                    clientName = "Anonymous";
                }

                System.out.println(clientName + "has joined the chat. ");
                broadcast(clientName + " has joinned the chat");

                String message;
                while ((message = input.readLine()) != null) {
                    broadcast(message);
                }
            } catch(Exception e){
                System.out.println(clientName + " has left the chat.");
            }finally{
                try {
                    socket.close();
                } catch (Exception e) {
                   System.err.println("Error closing connection: " + e.getMessage());
                }
                clients.remove(this);
                broadcast(clientName + " has left the chat");
            }
        }

        private void broadcast(String message){
            for(ClientHandler client : clients){
                client.output.println(message);
            }
        }
    }
}
