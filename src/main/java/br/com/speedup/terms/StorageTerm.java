package br.com.speedup.terms;

import br.com.speedup.config.Config;
import br.com.speedup.threads.LocalProcess;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StorageTerm extends LocalProcess implements Storage {

    private final Config config;
    private final ObjectMapper objectMapper;

    public StorageTerm(Config config) {
        this.config = config;
        this.objectMapper = new ObjectMapper();
        onLoadIndexFolder();
    }

    private void onLoadIndexFolder() {
        if (!Files.isDirectory(Paths.get(config.getIndexRoot()))) {
            try {
                Files.createDirectories(Paths.get(config.getIndexRoot()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onReceiveMessage(Object message) {
        persist((List<Term>)message);
    }

    @Override
    public void persist(List<Term> terms) {

        terms.forEach(term -> {
            try {
                final var json = objectMapper.writeValueAsString(term);
                Files.write(Paths.get(config.getIndexRoot()+term.getId()), json.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

//    private byte[] serialize(Object obj) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream os = new ObjectOutputStream(out);
//        os.writeObject(obj);
//        return out.toByteArray();
//    }

}
