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
            System.out.println("Is being written: " + controleur.isBeingWritten());
            System.out.println("Is being read: " + controleur.isBeingRead());
            while (controleur.isBeingWritten() || controleur.isBeingRead()) {
                try {
                    controleur.wait();
                    this.setPriority(Thread.MAX_PRIORITY);
                    //System.out.println("Redactor thread : " + this.getName() + " is set to wait : " + "\n");
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
        return this.getState() == Thread.State.WAITING;
    }
}
