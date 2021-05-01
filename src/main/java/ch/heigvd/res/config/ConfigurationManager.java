package ch.heigvd.res.config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationManager {
    private final Properties prop = new Properties();

    public ConfigurationManager() {
    }

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
