package br.com.speedup.terms;

import br.com.speedup.config.Config;
import br.com.speedup.threads.LocalProcess;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StorageTerm extends LocalProcess implements Storage {

    private final Config config;

    public StorageTerm(Config config) {
        this.config = config;

        System.out.println(System.getProperty("os.name"));

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

        System.out.println("Termos: " + terms);

        terms.forEach(term -> {

            final String value = term.getId().toString() + " " + term.getValue().toString();

            new Thread(()->{
                try {
                    Files.write(Paths.get(config.getIndexRoot()+term.getId()), value.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

//    public byte[] serialize(Object obj) throws IOException {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        ObjectOutputStream os = new ObjectOutputStream(out);
//        os.writeObject(obj);
//        return out.toByteArray();
//    }

}
