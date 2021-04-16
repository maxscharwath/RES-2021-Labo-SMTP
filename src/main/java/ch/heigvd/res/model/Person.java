package ch.heigvd.res.model;

public class Person {
    private String firstname;
    private String lastname;
    private String email;

    Person(String email)
    {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
