package ch.heig.dgyt.lecteursredacteurs;


import java.util.HashSet;
import java.util.Set;

public class Controleur {
    private Set<Lecteur> lecteurs = new HashSet<>();
    private Redacteur redacteur;

    synchronized boolean isBeingRead() {
        synchronized (this) {
            return this.lecteurs.size() > 0;
        }
    }

    synchronized boolean isBeingWritten() {
        synchronized (this) {
            return this.redacteur != null;
        }

    }

    void read(Lecteur lecteur) {
        synchronized (this) {
            if (lecteur != null)
                lecteurs.add(lecteur);
        }
    }

    void write(Redacteur redacteur) {
        synchronized (this) {
            if (this.redacteur == null || redacteur != null)
                this.redacteur = redacteur;
        }

    }

    void close(Lecteur lecteur) {
        if (lecteurs.remove(lecteur) && lecteurs.size() == 0) {
            this.redacteur = null;
            this.notifyAll();
        }
    }

    void close(Redacteur redacteur) {
        if (this.redacteur != null && this.redacteur == redacteur) {
            this.redacteur = null;
            this.notifyAll();
        }
    }


}
