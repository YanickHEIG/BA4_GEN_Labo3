package ch.heig.dgyt.lecteursredacteurs;

public class Redacteur extends Thread {
    //private Thread thread;
    private Controleur controleur;

    Redacteur(Controleur controleur) {
        Redacteur redacteur = this;
        this.controleur = controleur;
    }

    @Override
    public void run() {
        synchronized (controleur) {

            System.out.println("Is being put to wait: " + (controleur.isBeingRead() || controleur.isBeingWritten()));
            while ( controleur.isBeingWritten()) {
                try {
                    controleur.wait();
                    this.setPriority(Thread.MAX_PRIORITY);
                    System.out.println("Redactor thread : " + this.getName() + " is set to wait : " +  this.getState());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.print("New redactor thread: " + this.getName() + " " + this.getState() + "\n");
            this.controleur.write(this);
        }
    }

    public void startWrite() {
        this.start();
    }

    public void stopWrite() {
        this.controleur.close(this);
    }

    public boolean isWaiting() {
        return this.getState() == State.WAITING;
    }
}
