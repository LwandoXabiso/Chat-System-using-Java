import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ClientGUI {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private BufferedReader serverInput;
    private PrintStream serverOutput;
    private Socket socket;
    private String clientName;

    public ClientGUI(String serverIp){
        //Ask for user name
        clientName = JOptionPane.showInputDialog("Enter your display name: ");
        if(clientName == null || clientName.trim().isEmpty()){
            clientName = "Anonymous";
        }

        //Setup GUI
        frame = new JFrame("Chat client - " + clientName);
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        messageField = new JTextField();
        sendButton = new JButton("Send");

        panel.add(messageField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);
        frame.add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                sendMessage();
            }
        });
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                sendMessage();
            }
        });

        frame.setVisible(true);

        // Connect to server
        try {
            socket = new Socket(serverIp, 2020);
            serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOutput = new PrintStream(socket.getOutputStream());

            // Start thread to listen for incoming messages
            new Thread(() -> receiveMessage()).start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Failed to connect to server!", "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void sendMessage(){
        String message = messageField.getText().trim();
        if(!message.isEmpty()){
            serverOutput.println(clientName + ": " + message);
            messageField.setText("");
        }
    }

    private void receiveMessage() {
        try {
            String message;
            while ((message = serverInput.readLine()) != null) {
                chatArea.append(message + "\n");
            }
        } catch (Exception e) {
            chatArea.append("Disconnected from server. \n");
        }
    }

    public static void main(String[] args){
        String serverIp = JOptionPane.showInputDialog("Enter Server IPv4 Address:");
        if(serverIp != null && !serverIp.trim().isEmpty()){
            new ClientGUI(serverIp);
        }
    }
}
