package ch.heigvd.res.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class PrankGenerator {
    private LinkedList<Person> victims = new LinkedList<>();

    private LinkedList<Prank> pranks = new LinkedList<>();

    public PrankGenerator(String victimsFileName, String pranksFileName) {
        loadPranks(pranksFileName);
        loadVictims(victimsFileName);
    }

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

    private void loadPranks(String filePath) {
        StringBuilder prankBody = new StringBuilder();
        String prankSubject = "";
        int nbLine = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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

    private void loadVictims(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                String infos[] = sCurrentLine.split(",");
                victims.add(new Person(infos[0], infos[1], infos[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
