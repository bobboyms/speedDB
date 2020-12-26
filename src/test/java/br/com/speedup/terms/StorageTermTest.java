package br.com.speedup.terms;

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

public class StorageTermTest extends TestCase {

//    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
//        ByteArrayInputStream in = new ByteArrayInputStream(data);
//        ObjectInputStream is = new ObjectInputStream(in);
//        return is.readObject();
//    }

    @Test
    public void testPersist() throws IOException {

        Config config = ConfigFactory.create(new String[]{"CONTAINER"});

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
                "são");

        List<String> docs = Arrays.asList(
                "Tatiane foi almoçar com Isabella",
                "Thiago e Tatiane são pais de Isabella",
                "Isabella foi tomar açai com Thiago seu pai");


        TermManager termManager = new TermManager(new StorageTerm(config));
        StorageTerm storageTerm = new StorageTerm(config);

        docs.forEach(doc ->{
            var list = termManager.generateTerms(doc);
            storageTerm.persist(list);
        });

        Files.list(Paths.get(config.getIndexRoot()))
                .forEach(path -> {

                    System.out.println("-----------------");
                    System.out.println(path);

                    try {
                        String value = new String(Files.readAllBytes(path));
                        var splited = value.split(" ");
                        Term term = new Term(Long.valueOf(splited[0]), splited[1]);
                        Assert.assertEquals(true, words.contains(term.getValue()));
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


        Files.delete(Paths.get(config.getIndexRoot()));

    }
}