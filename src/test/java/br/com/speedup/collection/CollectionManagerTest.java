package br.com.speedup.collection;

import br.com.speedup.GenerateConfig;
import br.com.speedup.config.Config;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CollectionManagerTest extends TestCase {

    public void testCreateCollection() throws IOException {
        Config config = GenerateConfig.getConfig();
        CollectionManager collectionManager = new CollectionManager(config);

        String collectionName = "CLIENTES";
        collectionManager.createCollection(collectionName);
        Assert.assertEquals(true, Files.isDirectory(Paths.get(config.getDbRoot() + collectionName)));

        try {
            collectionManager.createCollection(collectionName);
        } catch (Exception e) {
            Assert.assertEquals("CLIENTES collection already exists", e.getMessage());
        }

        collectionManager.dropColletion(collectionName);
        Assert.assertEquals(false, Files.isDirectory(Paths.get(config.getDbRoot() + collectionName)));

        try {
            collectionManager.dropColletion(collectionName);
        } catch (Exception e) {
            Assert.assertEquals("CLIENTES collection not found", e.getMessage());
        }
    }

}