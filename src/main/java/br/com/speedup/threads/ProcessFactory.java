package br.com.speedup.threads;

public class ProcessFactory {

    private ProcessFactory(){}

    public static CreatedProcess createdProcess(LocalProcess localProcess) {
        return ProcessManager.create(localProcess);
    }

}
