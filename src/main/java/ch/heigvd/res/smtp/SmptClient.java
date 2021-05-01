package ch.heigvd.res.smtp;

import ch.heigvd.res.model.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Class that represent an smtp Client
 */
public class SmptClient {
    private static final Logger LOG = Logger.getLogger(SmptClient.class.getName());
    private String smtpPortAddress;
    private int smtpServerPort;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SmptClient(String smtpPortAddress, int smtpServerPort) {
        this.smtpPortAddress = smtpPortAddress;
        this.smtpServerPort = smtpServerPort;
    }

    public static void setLogLevel(Level level) {
        LOG.setLevel(level);
    }

    /**
     * send a message to the address to the server pointed by smtpPortAddress and smtpServerPort
     * @param msg the message to send
     * @throws IOException
     */
    public void sendMessage(Message msg) throws IOException {
        socket = new Socket(smtpPortAddress, smtpServerPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));


        String line = reader.readLine();
        LOG.info(line);
        if (line.startsWith("250")) {
            throw new IOException("SMTP error: " + line);
        }
        //Lecture des messages de bienvenue du serveur
        while (line.startsWith("250-")) {
            line = reader.readLine();
            LOG.info(line);
        }

        writer.write("MAIL FROM: ");
        writer.write("<" + msg.getFrom() + ">");
        writer.write("\r\n");
        writer.flush();

        for (String to : Stream.of(msg.getTo(), msg.getBcc(), msg.getCc()).flatMap(Stream::of).toArray(String[]::new)) {
            writer.write("RCPT TO: ");
            writer.write("<" + to + ">");
            LOG.info(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        writer.write("DATA");
        writer.write("\r\n");
        line = reader.readLine();
        LOG.info(line);
        writer.write("From: " + msg.getFrom());
        writer.write("\r\n");

        writer.write("To: ");
        writer.write(String.join(",", msg.getTo()));
        writer.write("\r\n");

        writer.write("Cc: ");
        writer.write(String.join(",", msg.getCc()));
        writer.write("\r\n");

        writer.write("Bcc: ");
        writer.write(String.join(",", msg.getBcc()));
        writer.write("\r\n");

        writer.write("Subject: " + "=?utf-8?B?" + Base64.getEncoder().encodeToString(msg.getSubject().getBytes(StandardCharsets.UTF_8)) + "?=" + "\r\n");
        writer.write("Content-type: text/plain; charset=utf8" + "\r\n");
        writer.write("\r\n");

        writer.write(msg.getBody());

        writer.write("\r\n.\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        writer.write("QUIT");
        writer.write("\r\n");
        writer.close();
        reader.close();
        socket.close();
    }

}
