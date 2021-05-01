package ch.heigvd.res;

import ch.heigvd.res.config.ConfigurationManager;
import ch.heigvd.res.model.Group;
import ch.heigvd.res.model.Message;
import ch.heigvd.res.model.PrankGenerator;
import ch.heigvd.res.smtp.SmptClient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {

    private static final Logger LOG = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        ConfigurationManager config = new ConfigurationManager();
        config.loadConfig("config/config.properties");
        SmptClient client = new SmptClient(config.getSmtpServerAddress(), config.getSmtpServerPort());
        SmptClient.setLogLevel(Level.OFF);
        PrankGenerator generator = new PrankGenerator("config/victims.utf8", "config/messages.utf8");
        Group[] groups = generator.generateGroups(config.getNumberOfGroups(), config.getGroupSize());
        LOG.info("Let send a prank to " + groups.length + " groups.");
        int sentEmails = 0;
        for (Group group : groups) {
            Message message = generator.generatePrank(group, config.getWitnessToCC());
            try {
                client.sendMessage(message);
                ++sentEmails;
            } catch (IOException e) {
                LOG.severe("Oops there was a problem to send a prank to a group.");
               e.printStackTrace();
            }
        }
        LOG.info("Yes we send " + sentEmails / groups.length * 100 + "% of emails !");
    }
}
