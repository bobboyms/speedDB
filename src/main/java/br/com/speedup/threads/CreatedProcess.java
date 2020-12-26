package br.com.speedup.threads;

public interface CreatedProcess {
    void sendMessage(final Object message);
    void kill();
}
