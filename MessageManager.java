import java.util.ArrayList;

class MessageManager {
    private ArrayList<Message> messages = new ArrayList<>();

    public void sendMessage(int senderId, int receiverId, String text, String timestamp) {
        Message message = new Message(senderId, receiverId, text, timestamp);
        messages.add(message);
        System.out.println("Message sent successfully!");
    }

    public void receiveMessage(int senderId, int receiverId, String text, String timestamp) {
        Message message = new Message(senderId, receiverId, text, timestamp);
        messages.add(message);
        System.out.println("Message received.");
    }

    public void markMessageStatus(int contactId, boolean isRead) {
        for (Message message : messages) {
            if (message.getSenderId() == contactId || message.getReceiverId() == contactId) {
                message.setRead(isRead);
            }
        }
        System.out.println("Messages for contact " + contactId + " marked as " + (isRead ? "read" : "unread") + ".");
    }

    public void searchMessages(String query, int senderId) {
        boolean found = false;
        for (Message message : messages) {
            if ((senderId == -1 || message.getSenderId() == senderId) &&
                    (query.isEmpty() || message.getText().contains(query))) {
                message.displayMessage();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No message was found");
        }
    }

    public void deleteMessagesForContact(int contactId) {
        messages.removeIf(message -> message.getSenderId() == contactId || message.getReceiverId() == contactId);
        System.out.println("All messages for contact " + contactId + " have been deleted.");
    }

    public void displayMessagesForContact(int contactId) {
        boolean found = false;
        for (Message message : messages) {
            if (message.getSenderId() == contactId || message.getReceiverId() == contactId) {
                message.displayMessage();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found for contact " + contactId + ".");
        }
    }

    public void displayAllMessages() {
        if (messages.isEmpty()) {
            System.out.println("No messages to display.");
        } else {
            for (Message message : messages) {
                message.displayMessage();
            }
        }
    }
}