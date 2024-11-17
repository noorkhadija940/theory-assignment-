class Message {
    private int senderId;
    private int receiverId;
    private String text;
    private String timestamp;
    private boolean isRead;

    public Message(int senderId, int receiverId, String text, String timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp = timestamp;
        this.isRead = false;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getText() {
        return text;
    }

    public boolean isRead() {
        return isRead;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public void displayMessage() {
        System.out.println("Send From: " + senderId + ", Receive To: " + receiverId + ", Text: " + text +
                ", Timestamp: " + timestamp + ", Status: " + (isRead ? "Read" : "Unread"));
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}