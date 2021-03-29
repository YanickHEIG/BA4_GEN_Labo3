package ch.heig.dgyt.lecteursredacteurs;


public class Lecteur extends Thread {
    private Controleur controleur;
    public Boolean reading = false;
    Lecteur(Controleur controleur) {
        Lecteur lecteur = this;
        this.controleur = controleur;
    }

    @Override
    public void run() {
        while (!controleur.read(this)) ;
        reading = true;
        while(controleur.isAccessing(this));
        reading = false;
    }

    public synchronized void startRead() {
        reading = false;
        this.start();
        while(reading == false && this.getState() == Thread.State.RUNNABLE);
    }

    public synchronized void stopRead() {
        this.controleur.close(this);
        while(reading);
    }

    public synchronized boolean isWaiting() {
        return reading == false;
    }
}
