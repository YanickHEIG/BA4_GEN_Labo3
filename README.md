# Readme



### Links

* [Travis CI](https://travis-ci.com/github/YanickHEIG/BA4_GEN_Labo3)

* [Debug Idea](https://stackoverflow.com/questions/29749334/intellij-java-package-org-junit-does-not-exist)



## Issues

Les asserts sont faits de manières synchrone, mais les threads ne sont pas forcement arrivé au point nécessaire:

```java
redacteur2.stopWrite();

// après les redacteurs , lecteur3 est libéré
assertFalse(lecteur3.isWaiting());
```

Dans cette exemple, le stopWrite déclenche la mise en route de lecteur3, mais il est possible que lecteur 3 ne soit pas encore arrivé suffisament loin à ce moment. Pour fixer le problème temporairement, un délai supplémentaire est accordé après le notifiy:

```java
private synchronized void notifyLecteursRedacteurs() {
    synchronized (writeLock) {
        writeLock.notifyAll();
    }
    sleep();
    synchronized (readLock) {
        readLock.notifyAll();
    }
    sleep();
}

private void sleep() {
    try {
        Thread.sleep(100);
    } catch (Exception e) {
        System.out.println(e);
    }
}
```



Solution: Garder le compte des lecteurs et rédacteurs en attente.

* Si des rédacteurs sont en attente, on ne débloque que les rédacteurs (mais pas les lecteurs)
* Si des lecteurs sont en attente, on les débloque

Si un des 2 a été débloqué, on attend que la main ait été reprise sur le controlleur