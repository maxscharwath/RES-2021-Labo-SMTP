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

    public static void main(String[] args) {
        ConfigurationManager config = new ConfigurationManager();
        config.loadConfig("config/config.properties");

        PrankGenerator generator = new PrankGenerator("config/victims.utf8","config/messages.utf8");

        SmptClient client = new SmptClient(config.getSmtpServerAddress(), config.getSmtpServerPort());
        Message message = new Message()
                .setBody("Hello buddy !")
                .setSubject("I'm back darling")
                .setTo("elizabeth.alexandra.mary@windsor.uk","charles.philip.arthur.george@windsor.uk","diana.frances.spencer@windsor.uk")
                .setCc("elizabeth.alexandra.mary@windsor.uk","charles.philip.arthur.george@windsor.uk","diana.frances.spencer@windsor.uk")
                .setFrom("philip.mountbatten@windsor.uk");
        try {
            client.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
