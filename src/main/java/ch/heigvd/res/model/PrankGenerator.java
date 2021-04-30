package ch.heigvd.res.model;

import jdk.jfr.MemoryAddress;

import javax.sound.midi.SysexMessage;
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
        System.out.println("Nb Pranks: "+ pranks.size());
        for (Prank prank: pranks) {
            System.out.println(prank);
        }
        System.out.println("Nb victims: "+ victims.size());
        for (Person victim: victims) {
            System.out.println(victim);
        }
    }

    public Message generatePrank(int nbVictims){
        Random random = new Random();
        Prank prank = pranks.get(random.nextInt(pranks.size()));
        Person from = victims.get(random.nextInt(victims.size()));

        Message msg = new Message();

        msg.setFrom(from.getEmail());
        LinkedList<Person> actualVictims = new LinkedList<>();

        while(actualVictims.size() < Math.min(victims.size()-1, nbVictims)){
            Person to = victims.get(random.nextInt(victims.size()));
            if(!actualVictims.contains(to) && to != from){
                actualVictims.add(to);
            }
        }
        msg.setBody(prank.getMessage(from));

        //TODO
        return null;

    }
    private void loadPranks(String filePath) {
        StringBuilder prankBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.equals("====")) {
                    pranks.add(new Prank(prankBuilder.toString()));
                    prankBuilder = new StringBuilder();
                } else {
                    prankBuilder.append(sCurrentLine).append("\n");
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
