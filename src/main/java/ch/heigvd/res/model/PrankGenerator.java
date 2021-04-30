package ch.heigvd.res.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class PrankGenerator {
    private LinkedList<Person> victims = new LinkedList<>();

    private LinkedList<Prank> pranks = new LinkedList<>();

    public PrankGenerator(String victimsFileName, String pranksFileName) {
        loadPranks(pranksFileName);
        loadUsers(victimsFileName);
    }

    public Message generatePrank(int nbVictims, String bcc) {
        Random random = new Random();
        Prank prank = pranks.get(random.nextInt(pranks.size()));
        Person from = victims.get(random.nextInt(victims.size()));
        LinkedList<Person> actualVictims = new LinkedList<>();

        while (actualVictims.size() < Math.min(victims.size() - 1, nbVictims)) {
            Person to = victims.get(random.nextInt(victims.size()));
            if (!actualVictims.contains(to) && to != from) {
                actualVictims.add(to);
            }
        }

        Message msg = new Message();
        msg.setFrom(from.getEmail());
        for (Person victim : actualVictims) {
            msg.addTo(victim.getEmail());
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

    private void loadUsers(String filePath) {
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
