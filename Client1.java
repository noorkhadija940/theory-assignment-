import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client1 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1234;
    private static MessageManager messageManager = new MessageManager();

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(in.readLine());

            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Connection to server lost.");
                }
            }).start();

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Send Message");
                System.out.println("2. Display All Messages");
                System.out.println("3. Mark Messages as Read");
                System.out.println("4. Delete Messages");
                System.out.println("5. Search Messages");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter your ID: ");
                        int senderId = scanner.nextInt();
                        System.out.print("Enter receiver ID: ");
                        int receiverId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter your message: ");
                        String message = scanner.nextLine();
                        String timestamp = new java.util.Date().toString();
                        out.println(senderId + "|" + receiverId + "|" + message + "|" + timestamp);
                        messageManager.sendMessage(senderId, receiverId, message, timestamp);
                        System.out.println("Message sent!");
                    }
                    case 2 -> messageManager.displayAllMessages();
                    case 3 -> {
                        System.out.print("Enter contact ID to mark messages as read: ");
                        int contactId = scanner.nextInt();
                        messageManager.markMessageStatus(contactId, true);
                    }
                    case 4 -> {
                        System.out.print("Enter contact ID to delete messages: ");
                        int contactId = scanner.nextInt();
                        messageManager.deleteMessagesForContact(contactId);
                    }
                    case 5 -> {
                        System.out.print("Enter sender ID to search messages (or -1 for all): ");
                        int senderId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter search query (leave blank for all messages): ");
                        String query = scanner.nextLine();
                        messageManager.searchMessages(query, senderId);
                    }
                    case 6 -> {
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}