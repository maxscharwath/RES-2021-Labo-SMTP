package ch.heigvd.res.model;

public class Prank {
    private String subject;
    private String text;

    Prank(String subject, String text) {
        this.text = text;
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessage(Person person) {
        return text
                .replace(":firstname", person.getFirstname())
                .replace(":lastname", person.getLastname())
                .replace(":email", person.getEmail());
    }
}
