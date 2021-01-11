package br.com.speedup.collection;

import br.com.speedup.config.Config;
import br.com.speedup.exception.CollectionException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CollectionManager {

    private final Config config;

    public CollectionManager(Config config) {
        this.config = config;
    }

    public void createCollection(String name) throws IOException, CollectionException {

        if (Files.isDirectory(Paths.get(config.getDbRoot() + name))) {
            throw new CollectionException(name + " collection already exists");
        }

        Files.createDirectories(Paths.get(config.getDbRoot() + name));

    }

    public void dropColletion(String name) throws IOException {

        if (!Files.isDirectory(Paths.get(config.getDbRoot() + name))) {
            throw new CollectionException(name + " collection not found");
        }

        Files.list(Paths.get(config.getDbRoot() + name))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        Files.delete(Paths.get(config.getDbRoot() + name));

    }


}
