package ch.heigvd.res;

import ch.heigvd.res.config.ConfigurationManager;
import ch.heigvd.res.model.Group;
import ch.heigvd.res.model.Message;
import ch.heigvd.res.model.PrankGenerator;
import ch.heigvd.res.smtp.SmptClient;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException {
        ConfigurationManager config = new ConfigurationManager();
        config.loadConfig("config/config.properties");
        SmptClient client = new SmptClient(config.getSmtpServerAddress(), config.getSmtpServerPort());
        PrankGenerator generator = new PrankGenerator("config/victims.utf8", "config/messages.utf8");
        Group[] groups = generator.generateGroups(config.getNumberOfGroups(), 3);
        for (Group group : groups) {
            Message message = generator.generatePrank(group, config.getWitnessToCC());
            client.sendMessage(message);
        }
    }
}
