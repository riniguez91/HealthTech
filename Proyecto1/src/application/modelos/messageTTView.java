package application.modelos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class messageTTView extends RecursiveTreeObject<messageTTView> {
    private StringProperty sender;
    private StringProperty receiver;
    private StringProperty subject;
    private StringProperty message;
    private StringProperty idTicket;

    public messageTTView(String sender, String receiver, String subject, String message, String idTicket) {
        this.sender = new SimpleStringProperty(sender);
        this.receiver = new SimpleStringProperty(receiver);
        this.subject = new SimpleStringProperty(subject);
        this.message = new SimpleStringProperty(message);
        this.idTicket = new SimpleStringProperty(idTicket);
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

}
