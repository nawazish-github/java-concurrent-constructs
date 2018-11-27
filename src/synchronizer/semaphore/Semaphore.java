package synchronizer.semaphore;

public class Semaphore {
    private int tokens;

    public Semaphore(){
        tokens = 5;
    }

    public Semaphore(int tokens){
        this.tokens=tokens;
    }

    public synchronized void acquire() {
        if (tokens > 0){
            --tokens;
            System.out.println("Thread got token: "+Thread.currentThread().getName()+". Token count: "+tokens);
            return;
        }

        while(true){
            try {
                System.out.println("Thread blocked for token: "+Thread.currentThread().getName()+". Token count: "+tokens);
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                return;
            }
            if (tokens > 0){
                --tokens;
                System.out.println("Thread got token: "+Thread.currentThread().getName()+". Token count: "+tokens);
                return;
            }
        }
    }

    public synchronized void release(){
        if(tokens<5){
            ++tokens;
            System.out.println("Thread released token: "+Thread.currentThread().getName()+". Token count: "+tokens);
            this.notifyAll();
        }
        return;
    }
}
