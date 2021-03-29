package ch.heig.dgyt.lecteursredacteurs;

public class Lecteur extends Thread {
    //private Thread thread;
    private Controleur controleur;

    Lecteur(Controleur controleur) {
        Lecteur lecteur = this;
        this.controleur = controleur;
    }

    @Override
    public void run() {
        synchronized (controleur) {
            while (controleur.isBeingWritten()) {
                try {
                    this.setPriority(Thread.MIN_PRIORITY);
                    controleur.wait();
                    System.out.print("Reader thread : " + this.getName() + " is set to wait " + this.getState() + "\n");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //System.out.print("New reader thread: " + this.getName() + " " + this.getState() + "\n");
            this.controleur.read(this);
        }
    }


    public void startRead() {
        this.start();
    }

    public void stopRead() {
        this.controleur.close(this);
    }

    public boolean isWaiting() {
        return this.getState() == Thread.State.WAITING;
    }
}
