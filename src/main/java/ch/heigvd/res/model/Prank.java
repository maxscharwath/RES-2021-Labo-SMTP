package ch.heigvd.res.model;

public class Prank {
    private String text;

    Prank(String text) {
        this.text = text;
    }

    public String toString() {
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
