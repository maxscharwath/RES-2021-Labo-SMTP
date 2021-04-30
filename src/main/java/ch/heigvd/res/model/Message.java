package ch.heigvd.res.model;

import java.util.Arrays;
import java.util.LinkedList;

public class Message {
    private String from;
    private LinkedList<String> to = new LinkedList<>();
    private LinkedList<String> cc = new LinkedList<>();
    private LinkedList<String> bcc = new LinkedList<>();
    private String subject;
    private String body;


    public String getSubject() {
        return subject;
    }

    public Message setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Message setBody(String body) {
        this.body = body;
        return this;
    }

    public String[] getTo() {
        return to.toArray(new String[0]);
    }

    public Message setTo(String[] emails) {
        this.to = new LinkedList<>(Arrays.asList(emails));
        return this;
    }

    public Message addTo(String email) {
        this.to.add(email);
        return this;
    }

    public String[] getCc() {
        return cc.toArray(new String[0]);
    }

    public Message setCc(String[] emails) {
        this.cc = new LinkedList<>(Arrays.asList(emails));
        return this;
    }

    public Message addCC(String email) {
        this.cc.add(email);
        return this;
    }

    public String[] getBcc() {
        return bcc.toArray(new String[0]);
    }

    public Message setBcc(String[] emails) {
        this.bcc = new LinkedList<>(Arrays.asList(emails));
        return this;
    }

    public Message addBcc(String email) {
        this.bcc.add(email);
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Message setFrom(String from) {
        this.from = from;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", to=" + to +
                ", cc=" + cc +
                ", bcc=" + bcc +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
