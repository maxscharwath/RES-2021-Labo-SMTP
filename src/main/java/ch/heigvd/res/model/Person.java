package ch.heigvd.res.model;

public class Person {
    private String firstname;
    private String lastname;
    private String email;

    Person(String firstname, String lastname, String email)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public String toString() {
        return  firstname + " " + lastname + " <" + email + ">";

    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
