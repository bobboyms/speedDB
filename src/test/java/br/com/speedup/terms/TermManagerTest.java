package br.com.speedup.terms;


import br.com.speedup.config.Config;
import br.com.speedup.config.ConfigFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TermManagerTest {

    @Test
    public void generateTerms() {

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

        docs.forEach(doc ->{
            var list = termManager.generateTerms(doc);
        });

        TermManager.TERMS.forEach((key, term) -> {
            Assert.assertEquals(true, words.contains(key));
        });

        Assert.assertEquals(14, TermManager.TERMS.size());

    }
}