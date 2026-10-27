package tn.esprit.elharizi.service.location.legacy;

/**
 * Client SMTP simule (fourni pour l'Atelier 6) : il affiche le message
 * dans la console au lieu d'envoyer un vrai e-mail.
 */
public class SmtpClient {
    private final String host;
    private final int port;

    public SmtpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void send(String destinataire, String message) {
        System.out.println("[SMTP " + host + ":" + port + "] -> " + destinataire + " : " + message);
    }
}
