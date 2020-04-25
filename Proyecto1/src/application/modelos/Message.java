package application.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    public Message(int PK_Ticket, int senderID, int receiverID, String subject, String message, String idTicket, boolean read){
        this.PK_Ticket = PK_Ticket;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.subject = subject;
        this.message = message;
        this.idTicket = idTicket;
        this.read = read;
    }

    public Message(int senderID, int receiverID, String subject, String message, String idTicket, boolean read){
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.subject = subject;
        this.message = message;
        this.idTicket = idTicket;
        this.read = read;
    }

    private int PK_Ticket;

    private int senderID;

    private int receiverID;

    private String subject;

    private String message;

    private final String idTicket;

    private boolean read;

    public int getPK_Ticket() { return this.PK_Ticket; }

    public int getSenderID() { return senderID; }

    public void setSenderID(int senderID) { this.senderID = senderID; }

    public int getReceiverID() { return receiverID; }

    public void setReceiverID(int receiverID) { this.receiverID = receiverID; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getIdTicket() {
        return idTicket;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}