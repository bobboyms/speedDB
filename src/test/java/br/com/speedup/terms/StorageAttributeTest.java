package br.com.speedup.terms;

import br.com.speedup.GenerateConfig;
import br.com.speedup.config.Config;
import br.com.speedup.config.ConfigFactory;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class StorageAttributeTest extends TestCase {

    @Test
    public void testPersist() throws IOException {

        Config config = GenerateConfig.getConfig();

        var words = Arrays.asList("com",
                "foi",
                "de",
                "açai",
                "seu",
                "isabella",
                "e",
                "thiago",
                "pais",
                "tomar",
                "almoçar",
                "pai",
                "tatiane",
                "são",
                "mirante",
                "izabella",
                "80610-040",
                "luiz",
                "serra",
                "curitiba",
                "rodrigues",
                "ji-panara",
                "da",
                "taliba",
                "80610-000",
                "paulo");

        List<String> docs = Arrays.asList(
                "Tatiane foi almoçar com Isabella",
                "Thiago e Tatiane são pais de Isabella",
                "Isabella foi tomar açai com Thiago seu pai");


        TermManager termManager = new TermManager(new StorageTerm(config), config);

        docs.forEach(doc ->{
            var list = termManager.generateTerms(doc);
            termManager.persistTerms(list);
        });

        TermManager.TERMS.forEach((key, term) -> {
            System.out.println(key);
            Assert.assertEquals(true, words.contains(key));
        });

        Files.list(Paths.get(config.getIndexRoot()))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        Files.deleteIfExists(Paths.get(config.getIndexRoot()));

    }
}