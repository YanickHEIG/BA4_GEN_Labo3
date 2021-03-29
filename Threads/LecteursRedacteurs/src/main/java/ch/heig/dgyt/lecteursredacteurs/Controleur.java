package ch.heig.dgyt.lecteursredacteurs;


import java.util.HashSet;
import java.util.Set;

public class Controleur {
    private Set<Lecteur> lecteurs = new HashSet<>();
    private Redacteur redacteur;

    boolean isBeingRead() {
        return this.lecteurs.size() > 0;
    }

    boolean isBeingWritten() {
        return this.redacteur != null;

    }

    synchronized boolean read(Lecteur lecteur) {
        synchronized (this) {
            if (lecteur == null || redacteur != null)
                return false;
            lecteurs.add(lecteur);
            return true;
        }
    }

    synchronized boolean write(Redacteur redacteur) {
        synchronized (this) {
            if (this.redacteur != null || redacteur == null)
                return false;
            this.redacteur = redacteur;
            return lecteurs.isEmpty();
        }
    }

    void close(Lecteur lecteur) {
        synchronized (this) {
            if (lecteurs.remove(lecteur) && lecteurs.size() == 0) {
                this.redacteur = null;
                this.notifyAll();
            }
        }
    }

    void close(Redacteur redacteur) {
        synchronized (this) {
            if (this.redacteur != null && this.redacteur == redacteur) {
                this.redacteur = null;
                this.notifyAll();
            }
        }
    }


}
