package synchronizer.semaphore;

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
            if (tokens > 0){
                --tokens;
                return;
            }

            while(true){
                this.wait();
                if (tokens > 0){
                    --tokens;
                    return;
                }
            }
        }
    }

    public synchronized void release(){
        if(tokens<5){
            ++tokens;
            this.notifyAll();
        }
        return;
    }
}
