package tn.esprit.elharizi.service.location.legacy;

/**
 * Passerelle SMS simulee (fournie pour l'Atelier 6) : elle affiche le message
 * dans la console au lieu d'envoyer un vrai SMS.
 */
public class SmsGateway {
    private final String url;

    public SmsGateway(String url) {
        this.url = url;
    }

    public void send(String telephone, String message) {
        System.out.println("[SMS " + url + "] -> " + telephone + " : " + message);
    }
}
