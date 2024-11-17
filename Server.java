import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 1234;
    private static final ConcurrentHashMap<Integer, Socket> clientSockets = new ConcurrentHashMap<>();
    private static MessageManager messageManager = new MessageManager();

    public static void main(String[] args) {
        System.out.println("Server started. Waiting for clients...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (clientSockets.size() < 2) {
                Socket clientSocket = serverSocket.accept();
                int clientId = clientSockets.size() + 1;
                clientSockets.put(clientId, clientSocket);

                System.out.println("Client " + clientId + " connected!");

                new Thread(new ClientHandler(clientId, clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {
        private int clientId;
        private Socket socket;

        public ClientHandler(int clientId, Socket socket) {
            this.clientId = clientId;
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                out.println("Welcome Client " + clientId + " to the Messaging Server!");

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Client " + clientId + " says: " + message);

                    String[] parts = message.split("\\|");
                    if (parts.length == 4) {
                        int senderId = Integer.parseInt(parts[0]);
                        int receiverId = Integer.parseInt(parts[1]);
                        String text = parts[2];
                        String timestamp = parts[3];

                        messageManager.sendMessage(senderId, receiverId, text, timestamp);

                        Socket receiverSocket = clientSockets.get(receiverId);
                        if (receiverSocket != null) {
                            PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
                            receiverOut.println("From Client " + senderId + ": " + text);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error with Client " + clientId + ": " + e.getMessage());
            } finally {
                clientSockets.remove(clientId);
            }
        }
    }
}