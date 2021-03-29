package ch.heig.dgyt.lecteursredacteurs;

public class Redacteur extends Thread {
    private Controleur controleur;
    public Boolean writing = false;
    Redacteur(Controleur controleur) {
        Redacteur redacteur = this;
        this.controleur = controleur;
    }

    @Override
    public void run() {
        while (!controleur.write(this));
        writing = true;
        while(controleur.isAccessing(this));
        writing = false;
    }

    public synchronized void startWrite() {
        writing = false;
        this.start();
        while(writing == false && this.getState() == Thread.State.RUNNABLE);
    }

    public synchronized void stopWrite() {
        this.controleur.close(this);
        while(writing);
    }

    public synchronized boolean isWaiting() {
        return writing == false;
    }
}
