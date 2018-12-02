package synchronizer.semaphore;

import java.util.concurrent.TimeUnit;

public class Semaphore {
    private int tokens;

    public Semaphore(){
        tokens = 5;
    }

    public Semaphore(int tokens){
        this.tokens=tokens;
    }

    public void acquire() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) throw new InterruptedException();
        synchronized (this){
            if (checkAndDispenceToken()) return;

            while(true){
                this.wait();
                if (checkAndDispenceToken()) return;
            }
        }
    }

    private boolean checkAndDispenceToken() {
        if (tokens > 0) {
            --tokens;
            return true;
        }
        return false;
    }

    public synchronized void release(){
        if(tokens<5){
            ++tokens;
            this.notifyAll();
        }
    }

    public int availablePermits(){
        return tokens;
    }

    public void acquireUninterruptibly(){
        synchronized (this){
            if(checkAndDispenceToken()) return;

            while(true){
                try {
                    this.wait();
                    if(checkAndDispenceToken()) return;
                } catch (InterruptedException e) {}
            }

        }
    }

    public boolean tryAcquire(){
        synchronized (this){
           return checkAndDispenceToken();
        }
    }

    public boolean tryAcquire(long timeout,
                              TimeUnit unit)
            throws InterruptedException{

        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException();

        synchronized (this){
            boolean status = checkAndDispenceToken();
                if (!status){
                    long expiryTime =  unit.toMillis(timeout);
                    this.wait(expiryTime);
                    status = checkAndDispenceToken();
                    return status;
                }else
                    return status;
        }
    }
}

