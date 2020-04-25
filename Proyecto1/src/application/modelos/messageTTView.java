package application.modelos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

public class messageTTView extends RecursiveTreeObject<messageTTView> {
    private final IntegerProperty senderID;
    private final IntegerProperty receiverID;
    private final StringProperty senderName;
    private final StringProperty receiverName;
    private final StringProperty subject;
    private final StringProperty message;
    private final StringProperty idTicket;
    private final BooleanProperty read;

    public messageTTView(int senderID, int receiverID, String senderName, String receiverName, String subject, String message, String idTicket, boolean read) {
        this.senderID = new SimpleIntegerProperty(senderID);
        this.receiverID = new SimpleIntegerProperty(receiverID);
        this.senderName = new SimpleStringProperty(senderName);
        this.receiverName = new SimpleStringProperty(receiverName);
        this.subject = new SimpleStringProperty(subject);
        this.message = new SimpleStringProperty(message);
        this.idTicket = new SimpleStringProperty(idTicket);
        this.read = new SimpleBooleanProperty(read);
    }

    public IntegerProperty getSenderID() { return senderID; }

    public void setSenderID(int senderID) { this.senderID.set(senderID); }

    public IntegerProperty getReceiverID() { return this.receiverID; }

    public void setReceiverID(int receiverID) { this.receiverID.set(receiverID); }

    public StringProperty getSender() {
        return this.senderName;
    }

    public void setSender(String sender) {
        this.senderName.set(sender);
    }

    public StringProperty getReceiver() {
        return this.receiverName;
    }

    public void setReceiver(String receiver) {
        this.receiverName.set(receiver);
    }

    public StringProperty getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public StringProperty getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public StringProperty getIdTicket() { return this.idTicket; }

    public void setIdTicket(String idTicket) { this.idTicket.set(idTicket); }

    public BooleanProperty getRead() {
        return this.read;
    }

    public void setRead(boolean read) {
        this.read.set(read);
    }
}
