package ch.heigvd.res.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class PrankGenerator {
    private LinkedList<Person> victims = new LinkedList<>(); //list of all the potential victims

    private LinkedList<Prank> pranks = new LinkedList<>(); //List of all the pranks

    public PrankGenerator(String victimsFileName, String pranksFileName) {
        loadPranks(pranksFileName);
        loadVictims(victimsFileName);
    }

    /**
     * Generate an array of groups of victims
     * @param nbGroups the number of groups to create
     * @param groupSize the size of each group
     * @return the arrayy of the groups created
     */
    public Group[] generateGroups(int nbGroups, int groupSize) {
        groupSize = Math.min(groupSize, victims.size());
        nbGroups = Math.min(nbGroups, victims.size() / groupSize);
        Group[] groups = new Group[nbGroups];
        LinkedList<Person> copy = new LinkedList<>(victims);
        Collections.shuffle(copy);
        for (int i = 0; i < nbGroups; i++) {
            groups[i] = new Group();
            for (int j = 0; j < groupSize; ++j) {
                groups[i].addMember(copy.pop());
            }
        }
        return groups;
    }

    /**
     * Generate a prank message for a group
     * @param group the victim group
     * @param bcc the email to send an hidden copy
     * @return the prank message
     */
    public Message generatePrank(Group group, String bcc) {
        Random random = new Random();
        Prank prank = pranks.get(random.nextInt(pranks.size()));

        Person[] groupMembers = group.getMembers();
        Person from = groupMembers[0];
        Message msg = new Message();
        msg.setFrom(from.getEmail());
        for (int i = 1; i < groupMembers.length; i++) {
            msg.addTo(groupMembers[i].getEmail());
        }
        msg.setBody(prank.getMessage(from));
        msg.setSubject(prank.getSubject());
        msg.addBcc(bcc);
        return msg;
    }

    /**
     * Retrieve all the pranks from a file
     * The redaction of each prank in the file must follow this structure:
     *
     * Subject
     * Body
     * ====

     * @param filePath the path to the pranks file
     */
    private void loadPranks(String filePath) {
        StringBuilder prankBody = new StringBuilder();
        String prankSubject = "";
        int nbLine = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.equals("====")) {
                    pranks.add(new Prank(prankSubject, prankBody.toString()));
                    prankBody = new StringBuilder();
                    nbLine = 0;
                } else {
                    if (nbLine == 0)
                        prankSubject = sCurrentLine;
                    else
                        prankBody.append(sCurrentLine).append("\n");
                    nbLine++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all the potential victims from a file
     * The redaction of each victim in the file must follow this structure:
     *
     * firstname,lastname,email
     * @param filePath the path to the victim file
     */
    private void loadVictims(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                String[] infos = sCurrentLine.split(",");
                victims.add(new Person(infos[0], infos[1], infos[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
