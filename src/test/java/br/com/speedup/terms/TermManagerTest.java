package br.com.speedup.terms;


import br.com.speedup.GenerateConfig;
import br.com.speedup.config.Config;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TermManagerTest {

    @Test
    public void generateTerms() throws IOException {

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
        TermManager.TERMS.clear();

        docs.forEach(doc ->{
            var list = termManager.generateTerms(doc);
        });

        TermManager.TERMS.forEach((key, term) -> {
            Assert.assertEquals(true, words.contains(key));
        });

//        Assert.assertEquals(14, TermManager.TERMS.size());

    }
}