package application.modelos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;

public class messageTTView extends RecursiveTreeObject<messageTTView> {
    private StringProperty sender;
    private StringProperty receiver;
    private StringProperty subject;
    private StringProperty message;
    private StringProperty idTicket;
    private BooleanProperty read;

    public messageTTView(String sender, String receiver, String subject, String message, String idTicket, boolean read) {
        this.sender = new SimpleStringProperty(sender);
        this.receiver = new SimpleStringProperty(receiver);
        this.subject = new SimpleStringProperty(subject);
        this.message = new SimpleStringProperty(message);
        this.idTicket = new SimpleStringProperty(idTicket);
        this.read = new SimpleBooleanProperty(read);
    }

    public StringProperty getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender.set(sender);
    }

    public StringProperty getReceiver() {
        return this.receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver.set(receiver);
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
