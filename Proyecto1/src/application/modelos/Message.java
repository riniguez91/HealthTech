package application.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    public Message(String sender, String receiver, String subject, String message, String idTicket){
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
        this.idTicket = idTicket;
    }

    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("receiver")
    @Expose
    private String receiver;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("idTicket")
    @Expose
    private String idTicket;

    public String getSender() { return sender; }

    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }
}