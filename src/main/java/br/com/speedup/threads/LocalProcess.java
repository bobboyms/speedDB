package br.com.speedup.threads;

public abstract class LocalProcess implements Runnable {

    private long processId;
    protected volatile boolean isRunning = true;

    public void setProcessId(long processId) {
        this.processId = processId;
    }

    public long getProcessId() {
        return processId;
    }

    public void stopProcess() {
        this.isRunning = false;
    }

    public abstract void onReceiveMessage(Object message);

    @Override
    public void run() {

        while (isRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
