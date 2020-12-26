package br.com.speedup.threads;

public class Process implements CreatedProcess {

    private final LocalProcess localProcess;

    public Process(LocalProcess localProcess) {
        this.localProcess = localProcess;
    }

    @Override
    public void sendMessage(final Object message) {
        ProcessManager.sendMessage(message,localProcess.getProcessId());
    }

    @Override
    public void kill() {
        localProcess.stopProcess();
    }

}
