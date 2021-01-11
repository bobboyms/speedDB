package br.com.speedup.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class ProcessManager {

    private static long threadCount = 0;
    private static List<LocalProcess> localProcesses = new ArrayList<>();

    public synchronized static Process create(LocalProcess local) {

        var clone = localProcesses.parallelStream().
                collect(Collectors.toList());

        final var processId = ++threadCount;

        local.setProcessId(processId);
        clone.add(local);

        final var localThread = new Thread(local);
        localThread.setName("Process-" + processId);
        localThread.start();
        localProcesses = clone;

        return new Process(local);

    }

    public static void sendMessage(final Object o, final long processId) {

        new Thread(() -> {
            var process = localProcesses.parallelStream().
                    filter(localProcess -> localProcess.getProcessId() == processId).
                    collect(Collectors.toList()).get(0);
            try {
                process.onReceiveMessage(o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
