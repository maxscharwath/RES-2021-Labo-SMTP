package ch.heigvd.res.model;

public class Message {
    private String from;
    //TODO: Mettre LinkedList partout
    private String[] to = new String[0];
    private String[] cc = new String[0];
    private String[] bcc = new String[0];
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
        return to;
    }

    public Message setTo(String ...to) {
        this.to = to;
        return this;
    }

    public String[] getCc() {
        return cc;
    }

    public Message setCc(String ...cc) {
        this.cc = cc;
        return this;
    }

    public String[] getBcc() {
        return bcc;
    }

    public Message setBcc(String[] bcc) {
        this.bcc = bcc;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Message setFrom(String from) {
        this.from = from;
        return this;
    }
}
