package br.com.speedup.attributes;

import br.com.speedup.GenerateConfig;
import br.com.speedup.collection.CollectionManager;
import br.com.speedup.config.Config;
import br.com.speedup.terms.StorageTerm;
import br.com.speedup.terms.TermManager;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;
import java.util.Arrays;

public class AttributesManagerTest extends TestCase {

    public void testIndexAttributes() throws IOException, InterruptedException {

        var docs = Arrays.asList("{\n" +
                "   \"nome\":\"Thiago luiz Rodrigues\",\n" +
                "   \"cidade\":\"Curitiba\",\n" +
                "   \"cep\":\"80610-040\"\n" +
                "}",
                "{\n" +
                        "   \"nome\":\"Tatiane Rodrigues\",\n" +
                        "   \"cidade\":\"Ji-Panara\",\n" +
                        "   \"cep\":\"80610-040\"\n" +
                        "}",
                "{\n" +
                        "   \"nome\":\"Izabella rodrigues\",\n" +
                        "   \"cidade\":\"Mirante da Serra\",\n" +
                        "   \"cep\":\"80610-040\"\n" +
                        "}");

        Config config = GenerateConfig.getConfig();
        CollectionManager collectionManager = new CollectionManager(config);
        collectionManager.createCollection("CLIENTES");

        TermManager termManager = new TermManager(new StorageTerm(config), config);

        AttributesManager attributesManager = new AttributesManager(termManager, "CLIENTES", config, new StorageAttribute(config, "CLIENTES"));
        docs.forEach(doc -> {
            attributesManager.indexAttributes(doc);
        });

        Thread.sleep(5000);

        attributesManager.getMAP_ATTIBUTES().forEach((key, terms) -> {

            if (key.equals("nome")) {
                Assert.assertEquals(5, terms.size());
                Assert.assertEquals(true, terms.get(0).getValue().equals("thiago"));
                Assert.assertEquals(true, terms.get(1).getValue().equals("luiz"));
                Assert.assertEquals(true, terms.get(2).getValue().equals("rodrigues"));
                Assert.assertEquals(true, terms.get(3).getValue().equals("tatiane"));
                Assert.assertEquals(true, terms.get(4).getValue().equals("izabella"));
            }

            if (key.equals("cidade")) {
                Assert.assertEquals(5, terms.size());
                Assert.assertEquals(true, terms.get(0).getValue().equals("curitiba"));
                Assert.assertEquals(true, terms.get(1).getValue().equals("ji-panara"));
                Assert.assertEquals(true, terms.get(2).getValue().equals("mirante"));
                Assert.assertEquals(true, terms.get(3).getValue().equals("da"));
                Assert.assertEquals(true, terms.get(4).getValue().equals("serra"));
            }

            if (key.equals("cep")) {
                Assert.assertEquals(1, terms.size());
                Assert.assertEquals(true, terms.get(0).getValue().equals("80610-040"));
            }

        });

        var docs2 = Arrays.asList("{\n" +
                        "   \"nome\":\"Taliba Rodrigues\",\n" +
                        "   \"cidade\":\"São paulo\",\n" +
                        "   \"cep\":\"80610-000\"\n" +
                        "}");

        docs2.forEach(doc -> {
            attributesManager.indexAttributes(doc);
        });

        Thread.sleep(3000);

        attributesManager.getMAP_ATTIBUTES().forEach((key, terms) -> {

            if (key.equals("nome")) {
                Assert.assertEquals(6, terms.size());
                Assert.assertEquals(true, terms.get(0).getValue().equals("thiago"));
                Assert.assertEquals(true, terms.get(1).getValue().equals("luiz"));
                Assert.assertEquals(true, terms.get(2).getValue().equals("rodrigues"));
                Assert.assertEquals(true, terms.get(3).getValue().equals("tatiane"));
                Assert.assertEquals(true, terms.get(4).getValue().equals("izabella"));
                Assert.assertEquals(true, terms.get(5).getValue().equals("taliba"));
            }

            if (key.equals("cidade")) {
                Assert.assertEquals(7, terms.size());
                Assert.assertEquals(true, terms.get(0).getValue().equals("curitiba"));
                Assert.assertEquals(true, terms.get(1).getValue().equals("ji-panara"));
                Assert.assertEquals(true, terms.get(2).getValue().equals("mirante"));
                Assert.assertEquals(true, terms.get(3).getValue().equals("da"));
                Assert.assertEquals(true, terms.get(4).getValue().equals("serra"));
                Assert.assertEquals(true, terms.get(5).getValue().equals("são"));
                Assert.assertEquals(true, terms.get(6).getValue().equals("paulo"));
            }

            if (key.equals("cep")) {
                Assert.assertEquals(2, terms.size());
                Assert.assertEquals(true, terms.get(0).getValue().equals("80610-040"));
                Assert.assertEquals(true, terms.get(1).getValue().equals("80610-000"));
            }

        });

        Thread.sleep(3000);

        AttributesManager attributesManager2 = new AttributesManager(termManager, "CLIENTES", config, new StorageAttribute(config, "CLIENTES"));
        attributesManager2.getMAP_ATTIBUTES().forEach((key, terms) -> {
            System.out.println("Key: " + key + " VALUE: " + terms);
            if (key.equals("nome")) {
                Assert.assertEquals(6, terms.size());
                Assert.assertEquals(true, terms.get(0).getValue().equals("thiago"));
                Assert.assertEquals(true, terms.get(1).getValue().equals("luiz"));
                Assert.assertEquals(true, terms.get(2).getValue().equals("rodrigues"));
                Assert.assertEquals(true, terms.get(3).getValue().equals("tatiane"));
                Assert.assertEquals(true, terms.get(4).getValue().equals("izabella"));
                Assert.assertEquals(true, terms.get(5).getValue().equals("taliba"));
            }

            if (key.equals("cidade")) {
                Assert.assertEquals(7, terms.size());
                Assert.assertEquals(true, terms.get(0).getValue().equals("curitiba"));
                Assert.assertEquals(true, terms.get(1).getValue().equals("ji-panara"));
                Assert.assertEquals(true, terms.get(2).getValue().equals("mirante"));
                Assert.assertEquals(true, terms.get(3).getValue().equals("da"));
                Assert.assertEquals(true, terms.get(4).getValue().equals("serra"));
                Assert.assertEquals(true, terms.get(5).getValue().equals("são"));
                Assert.assertEquals(true, terms.get(6).getValue().equals("paulo"));
            }

            if (key.equals("cep")) {
                Assert.assertEquals(2, terms.size());
                Assert.assertEquals(true, terms.get(0).getValue().equals("80610-040"));
                Assert.assertEquals(true, terms.get(1).getValue().equals("80610-000"));
            }

        });

        collectionManager.dropColletion("CLIENTES");

    }
}