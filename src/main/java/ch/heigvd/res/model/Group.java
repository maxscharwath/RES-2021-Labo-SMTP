package ch.heigvd.res.model;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 */
public class Group {
    private LinkedList<Person> members = new LinkedList<>();

    public Person[] getMembers() {
        return members.toArray(Person[]::new);
    }

    public Group setMembers(Person[] members) {
        this.members = new LinkedList<>(Arrays.asList(members));
        return this;
    }

    public Group addMember(Person member) {
        this.members.add(member);
        return this;
    }
}
