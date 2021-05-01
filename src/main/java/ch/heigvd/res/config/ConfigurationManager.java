package ch.heigvd.res.config;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Class that retrieves configuration parameters from a config file that have the following attributes:
 * - smtpServerAddress : the address of the smtp server
 * - smtpServerPort: the port of the smtp server
 * - numberOfGroups: the number of groups to send a prank
 * - groupSize: the size of a group
 * - witnessToCC: Emails of the prank's witnesses
 */
public class ConfigurationManager {
    private final Properties prop = new Properties();

    public ConfigurationManager() {
    }

    /**
     * Set the object's attributes to the ones in a config file
     *
     * @param propFileName the path of the config file
     * @return true if Ok, false if there's an error
     */
    public boolean loadConfig(String propFileName) {
        try {
            FileInputStream stream = new FileInputStream(propFileName);
            prop.load(stream);
            stream.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getSmtpServerAddress() {
        return prop.getProperty("smtpServerAddress", "localhost");
    }

    public int getSmtpServerPort() {
        return Integer.parseInt(prop.getProperty("smtpServerPort", "25"));
    }

    public int getNumberOfGroups() {
        return Integer.parseInt(prop.getProperty("numberOfGroups", "8"));
    }

    public int getGroupSize() {
        return Integer.parseInt(prop.getProperty("groupSize", "3"));
    }

    public String getWitnessToCC() {
        return prop.getProperty("witnessToCC", "");
    }
}
