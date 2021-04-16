package ch.heigvd.res.model;

public class Group {
    private Person[] members = new Person[0];

    public Person[] getMembers() {
        return members;
    }

    public void setMembers(Person[] members) {
        this.members = members;
    }
}
