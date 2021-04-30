package ch.heigvd.res;

import ch.heigvd.res.config.ConfigurationManager;
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

        PrankGenerator generator = new PrankGenerator("config/victims.utf8", "config/messages.utf8");

        Message message = generator.generatePrank(config.getNumberOfGroups(), config.getWitnessToCC());
        SmptClient client = new SmptClient(config.getSmtpServerAddress(), config.getSmtpServerPort());
        client.sendMessage(message);

    }
}
