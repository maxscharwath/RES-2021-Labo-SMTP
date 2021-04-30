package ch.heigvd.res.smtp;

import ch.heigvd.res.model.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class SmptClient {
    private static final Logger LOG = Logger.getLogger(SmptClient.class.getName());
    private String smtpPortAddress;
    private int smtpServerPort;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SmptClient(String smtpPortAddress, int smtpServerPort){
        this.smtpPortAddress = smtpPortAddress;
        this.smtpServerPort = smtpServerPort;
    }

    public void sendMessage(Message msg) throws IOException
    {
        socket = new Socket(smtpPortAddress, smtpServerPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));


        String line = reader.readLine();
        LOG.info(line);
        if(line.startsWith("250")){
            throw new IOException("SMTP error: " + line);
        }
        //Lecture des messages de bienvenue du serveur
        while (line.startsWith("250-")){
            line = reader.readLine();
            LOG.info(line);
        }
        writer.write("AUTH LOGIN\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        writer.write("MWEyZjQ5YmE4YWZmYzQ=\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        writer.write("MGQ4N2Y4ODdkMjNhMTI=\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        writer.write("MAIL FROM: ");
        writer.write("<" + msg.getFrom() + ">");
        writer.write("\r\n");
        writer.flush();

        for(String to : msg.getTo())
        {
            writer.write("RCPT TO: ");
            writer.write("<" + to + ">");
            LOG.info(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        for(String cc : msg.getCc())
        {
            writer.write("RCPT TO: ");
            writer.write("<" + cc + ">");
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        for(String witness : msg.getBcc())
        {
            writer.write("RCPT TO: ");
            writer.write( "<" + witness + ">");
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        writer.write("DATA");
        writer.write("\r\n");
        line = reader.readLine();
        LOG.info(line);
        writer.write("From: "+msg.getFrom());
        writer.write("\r\n");

        /*writer.write("To: ");

        for(String to : msg.getTo())
        {
            writer.write("<"+to+"> ");
        }
        writer.write("\r\n");*/

        writer.write("Subject: "+msg.getSubject());
        writer.write("\r\n");
        writer.write("\r\n");

        writer.write(msg.getBody());

        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");
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
